package net.sf.netmemo.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ExitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExitAction(String actionCommand, ImageIcon icon) {
		super(actionCommand, icon);
		putValue("action.command", actionCommand);
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		int choice = JOptionPane.showConfirmDialog(null, 
				"Exit NetMemo?", null, JOptionPane.YES_NO_OPTION);

		if (choice == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
