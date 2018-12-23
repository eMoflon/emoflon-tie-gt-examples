package org.moflon.tutorial.sokobangamegui.view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.moflon.tutorial.sokobangamegui.view.View;

/**
 * Custom action listener for load- and save-action.
 * 
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class LoadSaveAction implements ActionListener {
	private final JFileChooser fileChooser;
	private final View view;

	private final JComponent saveSource;
	private static File recentDirectory;

	public LoadSaveAction(final View view, final JComponent saveSource, final JComponent loadSource) {
		this.view = view;
		this.saveSource = saveSource;

		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(getCurrrentDirectoryForFileChooser());
		fileChooser.setAcceptAllFileFilterUsed(false);
		final FileFilter filter = new FileNameExtensionFilter("models", "xmi");
		fileChooser.addChoosableFileFilter(filter);
	}

	private File getCurrrentDirectoryForFileChooser() {

		final File instancesFolder = new File("instances/");
		final File currentFolder = new File(".");

		final File startDirectory;
		if (recentDirectory != null && recentDirectory.exists()) {
			startDirectory = recentDirectory;
		} else if (instancesFolder.exists()) {
			startDirectory = instancesFolder;
		} else {
			startDirectory = currentFolder;
		}

		return startDirectory;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		fileChooser.setCurrentDirectory(getCurrrentDirectoryForFileChooser());
		if (e.getSource().equals(saveSource)) {
			if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
				final File selectedFile = fileChooser.getSelectedFile();
				String filePath = selectedFile.getPath();

				/* Adjust filename for XMI file-extension */
				if (fileChooser.getFileFilter().getDescription().equals("models") && !filePath.endsWith(".xmi")) {
					filePath += ".xmi";
				}

				view.getController().saveModel(filePath);

				recentDirectory = selectedFile.getParentFile();
			}
		} else {
			if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				final File selectedFile = fileChooser.getSelectedFile();
				final String filePath = selectedFile.getPath();

				view.getController().loadModel(filePath);

				recentDirectory = selectedFile.getParentFile();
			}
		}
	}

}
