package org.moflon.tutorial.sokobangamegui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import org.eclipse.emf.ecore.EClass;
import org.moflon.tutorial.sokobangamegui.controller.Controller;
import org.moflon.tutorial.sokobangamegui.view.actions.ClearBoardAction;
import org.moflon.tutorial.sokobangamegui.view.actions.FieldSelectedAction;
import org.moflon.tutorial.sokobangamegui.view.actions.LoadSaveAction;
import org.moflon.tutorial.sokobangamegui.view.actions.NewBoardAction;
import org.moflon.tutorial.sokobangamegui.view.actions.PlayAction;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.Figure;
import SokobanLanguage.SokobanLanguageFactory;

/**
 * Custom view class (inherited from JFrame). This represents the whole window
 * containig all the buttons and menu entries.
 * 
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class View extends JFrame {
	private static final long serialVersionUID = 1L;

	/* Board and controller references */
	protected Board board;
	private Controller controller;

	/* JFrame main window and field buttons */
	protected FieldButton[][] buttons;
	protected PlayAction playAction;
	private JPopupMenu figureMenu;
	private JTextArea statusBar;
	private JScrollPane statusPanel;

	/* Icon list (implemented as hash-map to quick access via string) */
	private Map<String, ImageIcon> icons;

	/**
	 * The view constructor
	 * 
	 * @param controller Specifies the controller object. This must not be null!
	 * @param board      Specifies the board object. This must not be null!
	 */
	public View(Controller controller, Board board) {
		/* Setup references */
		this.controller = controller;
		this.board = board;
		this.icons = new HashMap<>();

		/* Initialize all components and update the view for the first time */
		initializeComponents();
		updateView();
	}

	/**
	 * Initializes all components: creates menu entries, action listener, field
	 * buttons etc.
	 */
	private void initializeComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		/* Create the menu bar */
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setName("File");
		JMenuItem newBoardItem = new JMenuItem("New Board...");
		JMenuItem clearBoardItem = new JMenuItem("Clear Board");
		JMenuItem loadItem = new JMenuItem("Load Board...");
		JMenuItem saveItem = new JMenuItem("Save Board...");

		JMenuItem playToggle = new JMenuItem();

		/* Create the action listeners */
		ActionListener loadSaveAction = new LoadSaveAction(this, saveItem, loadItem);

		newBoardItem.addActionListener(new NewBoardAction(this));
		clearBoardItem.addActionListener(new ClearBoardAction(this));
		loadItem.addActionListener(loadSaveAction);
		saveItem.addActionListener(loadSaveAction);

		playAction = new PlayAction(playToggle, this, controller);
		playToggle.addActionListener(playAction);

		fileMenu.add(newBoardItem);
		fileMenu.add(clearBoardItem);
		fileMenu.addSeparator();
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(playToggle);

		menuBar.add(fileMenu);

		/* Create popup menu */
		figureMenu = new JPopupMenu();

		JMenuItem endPos = new JMenuItem("Toggle End");
		endPos.addActionListener(e -> {
			FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
			createEndFigure(fieldButton);
		});
		figureMenu.add(endPos);

		figureMenu.addSeparator();

		JMenuItem noElementItem = new JMenuItem("None");
		noElementItem.addActionListener(e -> {
			FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
			nullFigure(fieldButton);
		});
		figureMenu.add(noElementItem);

		figureMenu.addSeparator();

		for (EClass elementClass : controller.getFigureClasses()) {
			JMenuItem elementClassItem = new JMenuItem(elementClass.getName());

			final EClass ec = elementClass;
			elementClassItem.addActionListener(e -> {
				FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
				createFigure(ec, fieldButton);
			});
			figureMenu.add(elementClassItem);
		}

		/* Compute button size depending on the count of fields and window size */
		Dimension maxBoardSize = new Dimension(1000, 500);

		int width = board.getWidth();
		int height = board.getHeight();

		int buttonSize = Math.min(maxBoardSize.width / width, maxBoardSize.height / height);

		/* Create field buttons */
		buttons = new FieldButton[height][width];
		JPanel panelBoard = new JPanel(new GridLayout(height, width));

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				/* Create new field button */
				FieldButton button = new FieldButton();

				/* Initialize button with size and popup menu connection */
				button.setPreferredSize(new Dimension(buttonSize, buttonSize));
				button.setMargin(new Insets(0, 0, 0, 0));
				button.setComponentPopupMenu(figureMenu);

				/* Insert button into array */
				buttons[row][col] = button;
				panelBoard.add(button);
			}
		}

		/* Connect all fields with an action listener */
		for (Field f : board.getFields()) {
			buttons[f.getRow()][f.getCol()].setField(f);
			buttons[f.getRow()][f.getCol()].addActionListener(new FieldSelectedAction(this, f));
		}

		/* Finalize main window */
		setTitle("BoardGameGUI");
		setJMenuBar(menuBar);
		add(panelBoard);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				icons.clear();
				updateView();
			}
		});

		/* Status bar */
		statusBar = new JTextArea(5, 30);
		statusPanel = new JScrollPane(statusBar);
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 22));

		updateStatus("Welcome to Sokoban!");

		pack();
		setVisible(true);
	}

	public void updateStatus(String status) {
		statusBar.append(status);
		statusBar.setCaretPosition(statusBar.getDocument().getLength());
	}

	public String getStatus() {
		return statusBar.getText();
	}

	/**
	 * Updates the view by setting up field text, icon, border, color etc.
	 */
	public void updateView() {
		Figure selectedFigure = board.getSelectedFigure();
		Field selectedField = null;

		if (selectedFigure != null) {
			selectedField = selectedFigure.getField();
		}

		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				/* Get field button at current row and column in array */
				FieldButton button = buttons[row][col];

				/* Get field from field-button */
				Field f = button.getField();

				/* Temporary memory */
				String figureName = "";
				ImageIcon icon = null;

				/* Get figure icon */
				if (f.getFigure() != null) {
					figureName = f.getFigure().eClass().getName();
					icon = loadIcon(figureName);
				}

				/* Setup icon and text */
				if (icon != null) {
					button.setText("");
					button.setIcon(icon);
				} else {
					button.setIcon(null);
					button.setText(figureName);
				}

				/* Setup border and background color */
				if (f.isEndPos()) {
					button.setBackground(Color.decode("#c1ffc1"));
					button.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else if (f.equals(selectedField)) {
					button.setBorder(BorderFactory.createLineBorder(Color.RED));
					button.setBackground(Color.PINK);
				} else {
					button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
					button.setBackground(null);
				}

				/* Check if popup menu should be shown */
				if (playAction.isPlayModus()) {
					button.setComponentPopupMenu(null);
				} else {
					button.setComponentPopupMenu(figureMenu);
				}
			}
		}

		this.repaint();
	}

	/**
	 * Loads the specified icon.
	 * 
	 * @param name Specifies the name of the icon which is to be loaded.
	 * @return The new image icon object.
	 * @see ImageIcon
	 */
	private ImageIcon loadIcon(String name) {
		if (icons.containsKey(name)) {
			return icons.get(name);
		}

		ImageIcon icon = null;

		try {
			/* Read icon from file */
			Image img = ImageIO.read(new File(imageFolder() + name + ".png"));

			/* Adjust icon size by scaling to button size */
			int buttonSize = Math.min(buttons[0][0].getWidth(), buttons[0][0].getHeight());
			Image scaled = img.getScaledInstance(buttonSize - 2, buttonSize - 2, Image.SCALE_SMOOTH);

			/* Allocate new image icon */
			icon = new ImageIcon(scaled);
		} catch (IOException e) {
			/* Ignore internal exceptions */
		}

		icons.put(name, icon);
		return icon;
	}

	/**
	 * @return The controller object.
	 */
	public Controller getController() {
		return controller;
	}

	public void selectField(Field field) {
		if (playAction.isPlayModus()) {
			controller.selectField(field);
		}
	}

	public void showMessage(String message) {
		updateStatus("\n" + message);
	}

	protected String imageFolder() {
		return "images/";
	}

	protected void createFigure(final EClass ec, FieldButton fieldButton) {
		/* Setup new connection between field and figure */
		Figure newFigure = (Figure) SokobanLanguageFactory.eINSTANCE.create(ec);
		controller.setFigure(fieldButton.getField(), newFigure);
	}

	protected void createEndFigure(FieldButton fieldButton) {
		fieldButton.getField().setEndPos(!fieldButton.getField().isEndPos());
		updateView();
	}

	protected void nullFigure(FieldButton fieldButton) {
		controller.setFigure(fieldButton.getField(), null);
	}

}
