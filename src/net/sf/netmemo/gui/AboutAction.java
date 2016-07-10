/**
 * 
 */
package net.sf.netmemo.gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.sf.jnetparse.gui.GUI;

/**
 * @author Mohammed El-Amine MAHBOUBI
 *
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {

	private static final String txtDescription = 
		"<b><i><u>NetMemo</u></i></b> " +
		"is provided in the hope to be useful and instructive.<br><br>" +
		"This is free software; see the License for copying conditions.<br>" +
		"There is NO warranty; not even for MERCHANTABILITY or <br>" +
		"FITNESS FOR A PARTICULAR PURPOSE.<br><br>";

	private static final String arrayDescription;

	static {
		GregorianCalendar cal = NetMemo.version.getCal();
		String s = (NetMemo.version.isAlpha()? "alpha":
			(NetMemo.version.isBeta()? "beta":
				(NetMemo.version.isRelease()? "release":
					(NetMemo.version.isTerminal()? "terminal": "N/A"))));
		arrayDescription = 
			"<table border=\"1\">" +
			"<tr>" +
				"<td align=\"left\">Version</td>" +
				"<td align=\"center\">" + NetMemo.version.getId() + "</td>" + 
			"</tr>" +
			"<tr>" +
				"<td align=\"left\">Status</td>" +
				"<td align=\"center\">" +
				NetMemo.version.getName() + " - " + s + 
				"</td>" +
			"</tr>" +
			"<tr>" +
				"<td align=\"left\">Release date</td>" +
				"<td align=\"center\">" + 
					cal.get(Calendar.DAY_OF_MONTH) + "." +
					cal.get(Calendar.MONTH) + "." +
					cal.get(Calendar.YEAR) + 
				"</td>" +
			"</tr>" +
			"<tr>" +
				"<td align=\"left\">Author</td>" +
				"<td align=\"center\">Mohammed El-Amine MAHBOUBI</td>" +
			"</tr>" +
			"<tr>" +
				"<td align=\"left\">Home Page</td>" + 
				"<td align=\"center\">" +
					"<a href=\"" + 
					NetMemo.getStringResource(NetMemo.NETMEMO_HOME_URL) + "\">" + 
					NetMemo.getStringResource(NetMemo.NETMEMO_HOME_URL) + "</a>" +
				"</td>" +
			"</tr>" +
			"</table>";
	};

	private NetMemo netmemo;

	public static final String DEFAULT_ACTION_COMMAND = "netmemo.action.command.about";
	private String url;
	private JDialog intFrame;

	/**
	 * @param actionCommand
	 * @param icon
	 */
	public AboutAction(NetMemo netmemo, String actionCommand, String url, Icon icon) {
		super(actionCommand, icon);
		putValue("action.command", actionCommand);
		this.netmemo = netmemo;
		this.url = url;
		buildGUI();
	}

	private void buildGUI() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screen.width / 4;
		int y = screen.height / 4;
		int width = 3 * screen.width / 3;
		int height = 5 * screen.height / 5;

		intFrame = new JDialog(netmemo, "About NetMemo", true);
	 	intFrame.setBounds(x, y, width, height);  
	 	addComponents();
	}

	private void addComponents() {
		JEditorPane txt_desc   = new JEditorPane("text/html", txtDescription);
		JEditorPane array_desc = new JEditorPane("text/html", arrayDescription);
		JTextArea license = loadLicense();
		JButton closeButton = new JButton("Close");
		ImageIcon icon = NetMemo.createImageIcon(NetMemo.NETMEMO_SPLASH_IMAGE, false);
		int icon_width = icon.getIconWidth() * 1 / 2;
		int icon_height = icon.getIconHeight() * 1 / 2;
		ImageIcon scaledIcon = new ImageIcon(icon.getImage().
				getScaledInstance(icon_width, icon_height, 
						java.awt.Image.SCALE_SMOOTH));
		JLabel iconLabel = new JLabel(scaledIcon);
		JScrollPane spl = new JScrollPane(license);
		JPanel aboutPanel = new JPanel(new BorderLayout());
		JPanel detailsPanel = new JPanel(new BorderLayout());
		JPanel licensePanel = new JPanel(new BorderLayout());
		JPanel ctrlPanel = new JPanel();
		JTabbedPane tabs = new JTabbedPane();
		JTextPane textPane = new JTextPane();
		JTextPane detailsPane = new JTextPane();

		txt_desc.setEditable(false);
		txt_desc.setOpaque(false);
		array_desc.setEditable(false);
		array_desc.setOpaque(false);
		array_desc.addHyperlinkListener(new HyperlinkListener() {  
			public void hyperlinkUpdate(HyperlinkEvent hle) {
				Desktop desktop = netmemo.getDesktop();

				if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {  
					if ((desktop != null) && desktop.isSupported(Desktop.Action.BROWSE)) {
						try {
							desktop.browse(hle.getURL().toURI());
						} catch (IOException e) {
							e.printStackTrace(System.err);
						} catch (URISyntaxException e) {
							e.printStackTrace(System.err);
						}
					}
				}
			}});

		textPane.setEditable(false);
		textPane.setOpaque(false);
		detailsPane.setEditable(false);
		detailsPane.setOpaque(false);

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				intFrame.dispose();
			}});

		license.setEditable(false);
		license.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		textPane.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
		textPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textPane.setCaretPosition(0);
		textPane.insertComponent(txt_desc);
		textPane.insertComponent(new JLabel(scaledIcon));

		detailsPane.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
		detailsPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		detailsPane.setCaretPosition(0);
		detailsPane.insertComponent(array_desc);
		detailsPane.insertComponent(new JLabel(scaledIcon));

		aboutPanel.add(new JScrollPane(textPane), BorderLayout.CENTER);
		detailsPanel.add(new JScrollPane(detailsPane), BorderLayout.CENTER);
		licensePanel.add(spl, BorderLayout.CENTER);

		ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.LINE_AXIS));
		ctrlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		ctrlPanel.add(Box.createHorizontalGlue());
		ctrlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		ctrlPanel.add(closeButton);

		tabs.addTab(NetMemo.getStringResource(NetMemo.TAB_HELP_ABOUT), aboutPanel);
		tabs.addTab(NetMemo.getStringResource(NetMemo.TAB_HELP_DETAILS), detailsPanel);
		tabs.addTab(NetMemo.getStringResource(NetMemo.TAB_HELP_LICENSE), licensePanel);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		intFrame.add(tabs, BorderLayout.CENTER);
		intFrame.add(ctrlPanel, BorderLayout.PAGE_END);
		intFrame.setSize(3 * icon_width, 2 * icon_height);
		intFrame.setMinimumSize(intFrame.getSize());
	}

	private JTextArea loadLicense() {
		JTextArea area = new JTextArea(30, 70);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				NetMemo.getResourceStream(NetMemo.NETMEMO_HELP_LICENSE)));
		String line;

		try {
			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				area.append(line);
				area.append(NetMemo.NEW_LINE);
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
		}
		area.setMinimumSize(area.getPreferredSize());
		return area;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		//System.out.println("Internal Frame should be visible now...");
		intFrame.setVisible(true);
	}
}
