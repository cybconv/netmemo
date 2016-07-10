package net.sf.netmemo.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.sf.jnetparse.gui.GUI;
import net.sf.jnetparse.gui.MetaData;
import net.sf.jnetparse.util.Version;

@SuppressWarnings("serial")
public class CheckForUpdateAction extends AbstractAction {

	private NetMemo netmemo;
	private String url;

	public CheckForUpdateAction(NetMemo netmemo, String name, String url, ImageIcon icon) {
		super(name, icon);
		putValue("action.command", name);
		this.netmemo = netmemo;
		this.url = url;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				CheckForUpdateAction.this.setEnabled(false);
				// TEMPORARY
				//check();
				JOptionPane.showMessageDialog(null, 
				"Not fully implemented yet...", null, JOptionPane.INFORMATION_MESSAGE);
				// END OF TEMPORARY
				CheckForUpdateAction.this.setEnabled(true);
			}});

		th.start();
	}

	private void check() {
		URL nmu;
		InputStream input;
		Version v = netmemo.getVersion();
		Version lastVersion;
		String message  = "";
		int messageType = -1;
		final JEditorPane desc;
		final String adr = netmemo.getStringResource(GUI.GUI_HOME_URL);
		JLabel status = new JLabel("Trying to connect to server...");
		JProgressBar pb = new JProgressBar(0, 100);
		//JPanel inner_cards = new JPanel(new CardLayout());
		//JButton detailsButton = new JButton("Details >>");
		//JPanel cards = new JPanel(new CardLayout());
		JDialog dialog;
		final StringBuffer do_run = new StringBuffer("y");
		Thread runner = new Thread(new Runnable() {
			@Override
			public void run() {
				netmemo.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
				while (do_run.charAt(0) == 'y') {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace(System.err);
					}
				}
			}});

		desc = new JEditorPane("text/html", "");
		desc.setBackground(netmemo.getBackground());
		desc.setEditable(false);
		runner.start();
		pb.setValue(0);
		pb.setStringPainted(true);
		dialog = make_check_panel(status, pb);//, inner_cards, detailsButton, desc, cards);
		try {
			nmu = new URL(url);
			pb.setValue(40);
			status.setText("Opening a channel to remote data...");
			input = nmu.openStream();
			pb.setValue(40 + 30);
			status.setText("Loading remote data...");
			lastVersion = MetaData.loadVersion(input);
			pb.setValue(100);
			status.setText("Remote data successfully loaded...");

			if (v.compareTo(lastVersion) < 0) {
				message = "Your version of '" + lastVersion.getName() + 
					"' needs to be updated...<br><br>" + 
					"current version: " + v.getId() + 
					"<br>" +
					"last version: " + lastVersion.getId() + 
					"<br>" +
					"Please visit: <a href=\"" + adr + "\">" + adr + "</a>";	
				messageType = JOptionPane.WARNING_MESSAGE;
			} else {
				message = "You are already using the newest version of " +
					lastVersion.getName() + ".<br>";
				//message = "Your version of '" + lastVersion.getName() + 
				//	"' is up to date.<br>"; 
				messageType = JOptionPane.INFORMATION_MESSAGE;
			}
		} catch (MalformedURLException e) {
			message = getExceptionTrace(e);
			messageType = JOptionPane.ERROR_MESSAGE;
		} catch (IOException e) {
			if (e instanceof java.net.UnknownHostException) {
				message = "Server unreachable! Please check your network settings...";
			} else {
				message = getExceptionTrace(e);
			}
			messageType = JOptionPane.ERROR_MESSAGE;
		}
		dialog.dispose();
		desc.addHyperlinkListener(new HyperlinkListener() {  
			public void hyperlinkUpdate(HyperlinkEvent hle) {
				Desktop desktop = netmemo.getDesktop();

				if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
					desc.setToolTipText(adr);
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

		do_run.setCharAt(0, 'n');
		netmemo.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		desc.setText(message);
		JOptionPane.showMessageDialog(netmemo, 
				new JScrollPane(desc),
				"check for updates...",
				messageType);
	}

	private JDialog make_check_panel(JLabel status, JProgressBar pb) {//, JPanel inner_cards, JButton button, JEditorPane txt, JPanel cards) {
		JPanel pane        = new JPanel();
		JDialog d          = new JDialog(netmemo, "Check for updates", false);
		Dimension screen   = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension r        = status.getPreferredSize();

		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(status);
		pane.add(pb);

		d.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		d.add(pane);
		d.setBounds(screen.width/3, screen.height/3, r.width*3/2, r.height*5);
		d.setResizable(false);
		d.setVisible(true);

		return d;
	}

	private String getExceptionTrace(Exception e) {
		e.printStackTrace(System.err);
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		PrintStream out = new PrintStream(output);
//		e.printStackTrace(out);
//		message = out.toString();
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		e.printStackTrace(out);

		return "<pre>" + sw.toString() + "</pre>";
	}
}
