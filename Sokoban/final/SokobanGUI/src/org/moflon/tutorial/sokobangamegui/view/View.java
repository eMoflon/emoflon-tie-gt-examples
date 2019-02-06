package org.moflon.tutorial.sokobangamegui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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

import SokobanRules.Board;
import SokobanRules.Field;
import SokobanRules.Figure;
import SokobanRules.SokobanRulesFactory;

/**
 * Custom view class (inherited from JFrame). This represents the whole window
 * containig all the buttons and menu entries.
 * 
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class View extends JFrame {
	private static final long serialVersionUID = 1L;

	/* Board and controller references */
	private final Board board;
	private final Controller controller;

	/* JFrame main window and field buttons */
	private FieldButton[][] buttons;
	private PlayAction playAction;
	private JPopupMenu figureMenu;
	private JTextArea statusBar;
	private JScrollPane statusPanel;

	/* Icon list (implemented as hash-map to quick access via string) */
	private final Map<String, ImageIcon> icons;

	/**
	 * The view constructor
	 * 
	 * @param controller Specifies the controller object. This must not be null!
	 * @param board      Specifies the board object. This must not be null!
	 */
	public View(final Controller controller, final Board board) {
		this.controller = controller;
		this.board = board;
		this.icons = new HashMap<>();

		initializeComponents();
		updateView();

		this.setTitle("Sokoban");
		this.setIconImage(this.loadIcon("Sokoban").getImage());
	}

	/**
	 * Initializes all components: creates menu entries, action listener, field
	 * buttons etc.
	 */
	private void initializeComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		/* Create the menu bar */
		final JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu("File");

		final JMenuItem newBoardItem = new JMenuItem("New Board...");
		final JMenuItem clearBoardItem = new JMenuItem("Clear Board");
		final JMenuItem loadItem = new JMenuItem("Load Model...");
		final JMenuItem saveItem = new JMenuItem("Save Model...");

		final JMenuItem playToggle = new JMenuItem();

		/* Create the action listeners */
		final ActionListener loadSaveAction = new LoadSaveAction(this, saveItem, loadItem);

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

		final JMenuItem endPos = new JMenuItem("Toggle End");
		endPos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
				fieldButton.getField().setEndPos(!fieldButton.getField().isEndPos());
				updateView();
			}
		});
		figureMenu.add(endPos);

		figureMenu.addSeparator();

		final JMenuItem noElementItem = new JMenuItem("None");
		noElementItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
				controller.setFigure(fieldButton.getField(), null);
			}
		});
		figureMenu.add(noElementItem);

		figureMenu.addSeparator();

		for (final EClass elementClass : controller.getFigureClasses()) {
			final JMenuItem elementClassItem = new JMenuItem(elementClass.getName());

			final EClass ec = elementClass;

			elementClassItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					/* Setup new connection between field and figure */
					final FieldButton fieldButton = (FieldButton) figureMenu.getInvoker();
					final Figure newFigure = (Figure) SokobanRulesFactory.eINSTANCE.create(ec);
					controller.setFigure(fieldButton.getField(), newFigure);
				}
			});

			figureMenu.add(elementClassItem);
		}

		/* Compute button size depending on the count of fields and window size */
		final Dimension maxBoardSize = new Dimension(1000, 500);

		final int width = board.getWidth();
		final int height = board.getHeight();

		final int buttonSize = Math.min(maxBoardSize.width / width, maxBoardSize.height / height);

		/* Create field buttons */
		buttons = new FieldButton[height][width];
		final JPanel panelBoard = new JPanel(new GridLayout(height, width));

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				/* Create new field button */
				final FieldButton button = new FieldButton();

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
		for (final Field f : board.getFields()) {
			buttons[f.getRow()][f.getCol()].setField(f);
			buttons[f.getRow()][f.getCol()].addActionListener(new FieldSelectedAction(this, f));
		}

		/* Finalize main window */
		setTitle("BoardGameGUI");
		setJMenuBar(menuBar);
		add(panelBoard);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent arg0) {
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

	public void updateStatus(final String status) {
		statusBar.append(status);
		statusBar.setCaretPosition(statusBar.getDocument().getLength());
	}

	/**
	 * Updates the view by setting up field text, icon, border, color etc.
	 */
	public void updateView() {
		final Figure selectedFigure = board.getSelectedFigure();
		Field selectedField = null;

		if (selectedFigure != null) {
			selectedField = selectedFigure.getField();
		}

		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				/* Get field button at current row and column in array */
				final FieldButton button = buttons[row][col];

				/* Get field from field-button */
				final Field f = button.getField();

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
	private ImageIcon loadIcon(final String name) {
		if (icons.containsKey(name)) {
			return icons.get(name);
		}

		ImageIcon icon = null;

		try {
			/* Read icon from file */
			final Image img = ImageIO.read(new File("images/" + name + ".png"));

			/* Adjust icon size by scaling to button size */
			final int buttonSize = Math.min(buttons[0][0].getWidth(), buttons[0][0].getHeight());
			final Image scaled = img.getScaledInstance(buttonSize - 2, buttonSize - 2, Image.SCALE_SMOOTH);

			/* Allocate new image icon */
			icon = new ImageIcon(scaled);
		} catch (final IOException e) {
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

	public void selectField(final Field field) {
		if (playAction.isPlayModus()) {
			controller.selectField(field);
		}
	}

	public void showMessage(final String message) {
		updateStatus("\n" + message);
	}
}
