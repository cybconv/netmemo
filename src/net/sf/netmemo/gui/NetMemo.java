package net.sf.netmemo.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.Timer;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import net.sf.jnetparse.IPv4;
import net.sf.jnetparse.IPv6;
import net.sf.jnetparse.MAC;
import net.sf.jnetparse.QoS;
import net.sf.jnetparse.gui.AboutGUI;
import net.sf.jnetparse.gui.MetaData;
import net.sf.jnetparse.gui.TopicInfo;
import net.sf.jnetparse.security.PasswordGenerator;
import net.sf.jnetparse.util.Version;
import net.sf.netmemo.Launcher;
import net.sf.netmemo.gui.ConfigRegisterPanel;

import net.sf.jnetparse.gui.GUI;


import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.IllegalComponentStateException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.Vector;

@SuppressWarnings("serial")
public class NetMemo extends GUI implements ItemListener {

	//public static final String NETMEMO_VERSION = "0.0.1";
	public static final String NETMEMO_VERSION = "/config/GUI.xml";

	// minimum threshold for strong passwords' length
	public static final int SEC_PWD_LEN_THRESHOLD = 19;

	// passwords can not be longer than 1024 characters
	public static final int SEC_MAX_PWD_LENGTH = 1024;

	// can not generate more than 1000 passwords at a time
	public static final int SEC_MAX_PWD_COUNT = 1000;

	//public static final String IMAGES_BASE = "C:\\Users\\amine\\dev\\workspace\\netmemo\\img\\";
	public static final String IMAGES_BASE = "images/";
	//public static final String ICONS_BASE = IMAGES_BASE + "icons\\";
	public static final String ICONS_BASE = IMAGES_BASE + "icons/";

	private final String[] ICONS;

	protected final String[] NETMEMO_SPLASHES;

	//public static final String CONFIG_BASE = "C:\\Users\\amine\\dev\\workspace\\netmemo\\config\\";
	public static final String CONFIG_BASE = "config/";
	//public static final String QOS_RFC_MARKING = CONFIG_BASE + "ietf\\qos-marking.properties";
	public static final String QOS_RFC_MARKING = CONFIG_BASE + "ietf/qos-marking.properties";
	//public static final String QOS_AUTOQOS = CONFIG_BASE + "vendors\\cisco\\qos-autoqos.properties";
	public static final String QOS_AUTOQOS = CONFIG_BASE + "vendors/cisco/qos-autoqos.properties";

	//public static final String TROUBLESHOOTING_BASE = CONFIG_BASE + "vendors\\cisco\\troubleshooting\\";
	public static final String TROUBLESHOOTING_BASE = CONFIG_BASE + "vendors/cisco/troubleshooting/";
	public static final String TROUBLESHOOTING_MEMO = TROUBLESHOOTING_BASE + "troubleshooting.properties";

	public static final String NETMEMO_UPDATE_ADDRESS = "gui.update.url";
	public static final String CHECK_FOR_UPDATE_ACTION_ICON = "gui.icon.action.check_for_update";

	public static final String TAB_QOS = "gui.tab.qos";
	public static final String TAB_IPV4 = "gui.tab.ipv4";
	public static final String TAB_IPV6 = "gui.tab.ipv6";
	public static final String TAB_MC = "gui.tab.multicast";
	public static final String TAB_SEC = "gui.tab.security";
	public static final String TAB_TS = "gui.tab.ts";
	public static final String TAB_HOST = "gui.tab.host";
	public static final String TAB_DUH = "gui.tab.disk_usage_history";

//	public static final String TAB_HELP_ABOUT   = "netmemo.gui.tab.help.about";
//	public static final String TAB_HELP_DETAILS = "netmemo.gui.tab.help.about.details"; 
//	public static final String TAB_HELP_LICENSE = "netmemo.gui.tab.help.license";

	public static final String ICON_KEY_BASE = "gui.icon";

	public final String NETMEMO_SPLASH_IMAGE;

	public static final String ABOUT_ACTION_ICON = "gui.icon.action.about";
	public static final String EXIT_ACTION_ICON  = "gui.icon.action.exit";

	public static final String NETMEMO_BUTTON_COPY_CLIPBOARD = 
		"gui.icon.button.copy_clipboard";
	public static final String NETMEMO_BUTTON_BACK           = 
		"gui.icon.button.back";
	public static final String NETMEMO_BUTTON_SAVE           = 
		"gui.icon.button.save";
	public static final String NETMEMO_BUTTON_EDIT           = 
		"gui.icon.button.edit";
	public static final String NETMEMO_BUTTON_COMPUTE        =
		"gui.icon.button.compute";
	public static final String NETMEMO_BUTTON_CLEAR          =
		"gui.icon.button.clear";

	private Desktop desktop;

	private TrayIcon trayIcon;
	private MenuItem aboutItem = new MenuItem("About");
    private MenuItem restoreItem = new MenuItem("Restore");
    private MenuItem reduceItem = new MenuItem("Reduce");
    private MenuItem hideItem = new MenuItem("Hide");
    private MenuItem exitItem = new MenuItem("Exit");

    private JTextArea diskUsageHistory;

	////private static ResourceBundle resources = null;
	//private static Properties props = new Properties();
	private static final boolean JAR_MODE = true;

	////public static final Version version;

	////protected Version lastVersion;

	////static {
		////try {
			////resources = ResourceBundle.getBundle("config.GUI");
		////} catch (MissingResourceException mre) {
			////System.err.println("'config/Memo.properties' not found:");
			////mre.printStackTrace(System.err);
			////System.exit(1);
		////}
		////version = MetaData.loadVersion(NetMemo.class.getResourceAsStream(NETMEMO_VERSION));
		////ICONS = new String[6];
		////for (int i=0; i < ICONS.length; i++) {
			////ICONS[i] = ICONS_BASE + resources.getString(ICON_KEY_BASE+"."+i);
			////System.out.println("ICONS["+i+"]="+ICONS[i]);
		////}
		////NETMEMO_SPLASHES = new String[15];
		////for (int i=0; i < NETMEMO_SPLASHES.length; i++) {
			////NETMEMO_SPLASHES[i] = "/" +IMAGES_BASE + resources.getString(NETMEMO_SPLASH+"."+i);
			////System.out.println("NETMEMO_SPLASHES["+i+"]="+NETMEMO_SPLASHES[i]);
		////}
		////NETMEMO_SPLASH_IMAGE = NETMEMO_SPLASHES[14];
		////System.out.println(new java.io.File(".").getAbsolutePath());
	////};

//	protected static ImageIcon createImageIcon(String p, boolean isIcon) {
//		String path;
//		java.net.URL imageURL;
//		ImageIcon icon;
//
//		if (isIcon) {
//			path = "/" + ICONS_BASE + p;
//		} else {
//			path = p;
//		}
//		imageURL = NetMemo.class.getResource(path);
//		icon = (imageURL == null? null: new ImageIcon(imageURL));
//
//		System.out.println("NetMemo.createImageIcon -> path="+p);
//		System.out.println("NetMemo.createImageIcon -> url="+path);
//		System.out.println("NetMemo.createImageIcon -> imageURL="+imageURL);
//		System.out.println("NetMemo.createImageIcon -> icon="+icon);
//		return icon;
//	}
//
//	protected static String getStringResource(String name) {
//		return resources.getString(name);
//	}
//
//	protected static InputStream getResourceStream(String name) {
//		System.out.println("name="+name);
//		System.out.println("resource name="+resources.getString(name));
//		System.out.println("stream="+NetMemo.class.getResourceAsStream("/"+resources.getString(name)));
//		return NetMemo.class.getResourceAsStream("/"+resources.getString(name));
//	}
	
	private JMenuBar mainMenu;

	private JTabbedPane mainArea;

	private JPanel qosPanel;
	private JPanel ipv4Panel;
	private JPanel ipv6Panel;
	private JPanel mcPanel;
	private JPanel secPanel;
	private JPanel tsPanel;
	private JPanel duhPanel;
	private JPanel hostPanel;
	private JPanel cardsMC; 

	private JButton copy;
	private boolean macIPComplete;

	public NetMemo (String title) {
		super(title, true);
//		checkVersion(true);
		ImageIcon ii = super.createImageIcon("about.png", true);
		System.out.println("icon test="+ii);
//		try {
//			final String FS = System.getProperty("file.separator");
//			final String JAR = System.getProperty("sun.java.command",
//					System.getProperty("java.class.path", "netmemo.jar")).split(" ")[0];
//			URL res_url = new URL("jar:file:./"+JAR+"!/" +
//					getStringResource(GUI_VERSION));
//			version = MetaData.loadVersion(res_url.openStream());
//			version.seal();
//			System.out.println("resource url="+res_url);
//		} catch (MalformedURLException e) {
//			e.printStackTrace(System.err);
//			System.exit(1);
//		} catch (IOException e) {
//			e.printStackTrace(System.err);
//			System.exit(1);
//		}

		//version = MetaData.loadVersion(getClass().
		//	getResourceAsStream(getStringResource(GUI_VERSION)));
		//version.seal();

		ICONS = new String[6];
		NETMEMO_SPLASHES = new String[15];
		initGUI();
		NETMEMO_SPLASH_IMAGE = NETMEMO_SPLASHES[14];
		createGUI();
		setResizable(true);
		//pack();
		setVisible(true);
	}

	private void initGUI() {
		for (int i=0; i < ICONS.length; i++) {
			ICONS[i] = ICONS_BASE + resources.getString(ICON_KEY_BASE+"."+i);
			System.out.println("ICONS["+i+"]="+ICONS[i]);
		}
		
		for (int i=0; i < NETMEMO_SPLASHES.length; i++) {
			NETMEMO_SPLASHES[i] = "/" +IMAGES_BASE + resources.getString(GUI.GUI_SPLASH+"."+i);
			System.out.println("NETMEMO_SPLASHES["+i+"]="+NETMEMO_SPLASHES[i]);
		}
		System.out.println(new java.io.File(".").getAbsolutePath());
	}

//	protected void checkVersion(boolean newThread) {
//		if (newThread) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//				do_check_version();
//				}}).start();			
//		} else {
//			do_check_version();
//		}
//	}
//
//	private void do_check_version() {
//		try {
//			String adr = resources.getString(NETMEMO_UPDATE_ADDRESS);
//			URL nmu = new URL(adr);
//			InputStream input = nmu.openStream();
//
//			lastVersion = MetaData.loadVersion(input);
//			System.out.println("last version from '"+adr+"' is:"+
//					lastVersion.verbose());
//		} catch (MalformedURLException e) {
//			e.printStackTrace(System.err);
//		} catch (IOException e) {
//			e.printStackTrace(System.err);
//		}
//	}
//
//	protected Desktop getDesktop() {
//		return desktop;
//	}

	private void createGUI () {
		JToolBar toolbar = new JToolBar();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JMenu systemMenu = new JMenu("System");
		JMenu helpMenu   = new JMenu("Help");
		CheckForUpdateAction checkForUpdateAction = new CheckForUpdateAction(this, 
				"Check for updates",
				resources.getString(NETMEMO_UPDATE_ADDRESS),
				createImageIcon(resources.getString(CHECK_FOR_UPDATE_ACTION_ICON), true));
		final ExitAction exitAction = new ExitAction(
				"Exit",
				createImageIcon(resources.getString(EXIT_ACTION_ICON), true)
				);
		AboutGUI aboutAction = new AboutGUI(this, 
				"About",
				resources.getString(GUI.GUI_HOME_URL),
				createImageIcon(resources.getString(ABOUT_ACTION_ICON), true),
				createImageIcon(NETMEMO_SPLASH_IMAGE, false));
		int x = screenSize.width / 10;
		int y = screenSize.height / 10;
		int width = 2 * screenSize.width / 3;
		int height = 2 * screenSize.height / 3;

		qosPanel  = makeQoSPanel();
		ipv4Panel = makeIPv4Panel();
		ipv6Panel = makeIPv6Panel();
		mcPanel   = makeMCPanel();
		secPanel  = makeSecurityPanel();
		tsPanel   = makeTSPanel();
		hostPanel = makeHostPanel();
		duhPanel  = makeDUHPanel();

		toolbar.add(exitAction);
		toolbar.add(checkForUpdateAction);
		toolbar.add(aboutAction);
		toolbar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		toolbar.setFloatable(false);
		mainMenu = new JMenuBar();
		systemMenu.setMnemonic('S');
		helpMenu.setMnemonic('H');
		systemMenu.add(exitAction).setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, 
						ActionEvent.CTRL_MASK));
		helpMenu.add(checkForUpdateAction).setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_K, 
						ActionEvent.CTRL_MASK));
		helpMenu.add(aboutAction).setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, 
						ActionEvent.CTRL_MASK));
		mainMenu.add(systemMenu);
		mainMenu.add(helpMenu);
		mainArea = new JTabbedPane();
		mainArea.addTab(resources.getString(TAB_QOS), qosPanel);
		mainArea.addTab(resources.getString(TAB_IPV4), ipv4Panel);
		mainArea.addTab(resources.getString(TAB_IPV6), ipv6Panel);
		mainArea.addTab(resources.getString(TAB_MC), mcPanel);
		mainArea.addTab(resources.getString(TAB_SEC), secPanel);
		mainArea.addTab(resources.getString(TAB_TS), tsPanel);
		//mainArea.addTab(resources.getString(TAB_DUH), duhPanel);
		mainArea.addTab(resources.getString(TAB_HOST), hostPanel);

		mainArea.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				String os = System.getProperty("os.name").toLowerCase();
				String message = "Please note that under Windows Vista " +
						"hardware addresses are not properly retrieved...";

				if (mainArea.getSelectedComponent() == hostPanel) {
					if (os.indexOf("vista") != -1) {
						JOptionPane.showMessageDialog(NetMemo.this,
								message,
								"Bugged functionnality...",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}});
		mainArea.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	
//		if (Desktop.isDesktopSupported()) {
//            desktop = Desktop.getDesktop();
//            // now enable buttons for actions that are supported.
//            //enableSupportedActions();
//        } else {
//        	desktop = null;
//        }

		setIconImage(createImageIcon(NETMEMO_SPLASH_IMAGE, false).getImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeiconified(WindowEvent we) {
				trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(ICONS[0]));
				restoreItem.setEnabled(false);
				reduceItem.setEnabled(true);
				hideItem.setEnabled(true);
			}

			@Override
			public void windowIconified(WindowEvent we) {
				trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(ICONS[1]));
				restoreItem.setEnabled(true);
				reduceItem.setEnabled(false);
			}

			@Override
			public void windowClosing(WindowEvent we) {
				exitAction.actionPerformed(new ActionEvent(we.getSource(), we.getID(), "Exit"));
			}});
		//add(mb, BorderLayout.NORTH);
		setJMenuBar(mainMenu);
		add(toolbar, BorderLayout.NORTH);
		add(mainArea, BorderLayout.CENTER);
		//add(statusPanel, BorderLayout.SOUTH);
		setBounds(x, y, width, height);
		setLocationRelativeTo(null);
		//setIconImage(createImageIcon(resources.getString(WINDOW_ICON)).
			//getImage());
		createSystemTray(aboutAction);
		validate();
	}

	private void createSystemTray(final AboutGUI aboutAction) {
		PopupMenu popup = new PopupMenu();
		Menu help = new Menu("Help");
		Image image = Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource(ICONS[0]));
		SystemTray tray = SystemTray.getSystemTray();

		trayIcon = new TrayIcon(image, "NetMemo", popup);
		if (SystemTray.isSupported()) {
			restoreItem.setEnabled(false);
			ActionListener exitListener = new ActionListener() {
				private ExitAction ea = new ExitAction(null, null);

				public void actionPerformed(ActionEvent e) {
					ea.actionPerformed(e);
				}
			};

			ActionListener aboutListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					trayIcon.displayMessage("NetMemo", 
							"visit: http://www.sourceforge.net/projects/netmemo", 
							TrayIcon.MessageType.INFO);
					aboutAction.actionPerformed(ae);
				}   	
		    };

		    ActionListener restoreListener = new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		int state = NetMemo.this.getExtendedState(); // get the current state  

		            state = state & ~JFrame.ICONIFIED;  
		            // ~Frame.ICONIFIED is the exact inverse of this field. The & will now keep all bits except those that makeup Frame.ICONIFIED  
		            NetMemo.this.setExtendedState(state);  
		            //NetMemo.this.setVisible(true);
		            reduceItem.setEnabled(true);
		            hideItem.setEnabled(true);
		            restoreItem.setEnabled(false);
		            NetMemo.this.toFront();
		            NetMemo.this.setVisible(true);
		            //trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(ICONS[0]));
		    		trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(
		    				getClass().getClassLoader().
		    				getResource(ICONS[0])));
		            trayIcon.setToolTip("NetMemo");
		        }
		    };

		    ActionListener reduceListener = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {  
		            NetMemo.this.setExtendedState(JFrame.ICONIFIED);  
		            reduceItem.setEnabled(false);
		            restoreItem.setEnabled(true);
		            //trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(ICONS[1]));
		    		trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(
		    				getClass().getClassLoader().
		    				getResource(ICONS[1])));
		            //trayIcon.setToolTip("NetMemo [reduced]");
		        }
		    };

		    ActionListener hideListener = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            NetMemo.this.dispose();
		            reduceItem.setEnabled(false);
		            hideItem.setEnabled(false);
		            restoreItem.setEnabled(true);
		            //trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(ICONS[5]));
		    		trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(
		    				getClass().getClassLoader().
		    				getResource(ICONS[5])));
		            trayIcon.setToolTip("Right-click to restore NetMemo");
		        }
		    };

		    aboutItem.addActionListener(aboutListener);
		    restoreItem.addActionListener(restoreListener);
		    reduceItem.addActionListener(reduceListener);
		    hideItem.addActionListener(hideListener);
		    exitItem.addActionListener(exitListener);
		    help.add(aboutItem);
		    popup.add(help);
		    popup.addSeparator();
		    popup.add(restoreItem);
		    popup.add(reduceItem);
		    popup.add(hideItem);
		    popup.addSeparator();
		    popup.add(exitItem);

		    //ActionListener actionListener = new ActionListener() {
		    //    public void actionPerformed(ActionEvent e) {
		    //        trayIcon.displayMessage("Action Event", 
		    //            "An Action Event Has Been Performed!",
		    //            TrayIcon.MessageType.INFO);
		    //    }
		    //};

		    trayIcon.setImageAutoSize(true);
		    //trayIcon.addActionListener(actionListener);
		    //trayIcon.addMouseListener(mouseListener);

		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println("TrayIcon could not be added.");
		    }
		} else {
		    //  System Tray is not supported
		}
	}

	private JPanel makeQoSPanel() {
		JPanel pane = new JPanel(new BorderLayout());
		String[] choicesStr = new String[] {
			"Layer2/3 Conversions",
			"Summary of RFC-recommanded values for marking",
			"Summary of AutoQos for the Entreprise classes and DSCP values"
			};
		JComboBox<String> choices = new JComboBox<String>(choicesStr);
		JPanel cmd = new JPanel();
		final JPanel body = new JPanel(new CardLayout());
		JPanel convPane = makeQoS_conv();
		JPanel markPane = makeQoS_mark();
		JPanel autoqosPane = makeQoS_autoqos();

		choices.setEditable(false);
		choices.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
		        CardLayout cl = (CardLayout)(body.getLayout());
		        cl.show(body, (String)evt.getItem());
		    }});
		convPane.setBorder(new TitledBorder("Conversions"));
		markPane.setBorder(new TitledBorder("RFC-recommanded marking settings"));
		autoqosPane.setBorder(new TitledBorder("AutoQoS marking settings"));

		cmd.add(new JLabel("Selection:"));
		cmd.add(choices);
		body.add(convPane, choicesStr[0]);
		body.add(markPane, choicesStr[1]);
		body.add(autoqosPane, choicesStr[2]);

		pane.add(cmd, BorderLayout.PAGE_START);
		pane.add(body, BorderLayout.CENTER);

		return pane;
	}

	private JPanel makeQoS_conv() {
		JPanel p = new JPanel(new BorderLayout());
		JTextArea info = new JTextArea(5,40);
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel fl = new JLabel("From: ");
		JTextField fromField = new JTextField(10);
		JLabel tl = new JLabel("To: ");
		JTextArea toField = new JTextArea(9,10);
		JRadioButton tos_f = new JRadioButton("ToS");
		JRadioButton dscp_f = new JRadioButton("DSCP");
		JRadioButton ipp_f = new JRadioButton("IP Precedence");
		JRadioButton cos_f = new JRadioButton("CoS");
		JRadioButton tos_t = new JRadioButton("ToS");
		JRadioButton dscp_t = new JRadioButton("DSCP");
		JRadioButton ipp_t = new JRadioButton("IP Precedence");
		JRadioButton cos_t = new JRadioButton("CoS");
		// "From" group
		ButtonGroup fg = new ButtonGroup();
		// "To" group
		ButtonGroup tg = new ButtonGroup();
		QoSConverionAdapter ql = new QoSConverionAdapter(fromField, toField, tos_f, dscp_f, ipp_f, cos_f, tos_t, dscp_t, ipp_t, cos_t);

		fg.add(tos_f);
		fg.add(dscp_f);
		fg.add(ipp_f);
		fg.add(cos_f);
		tg.add(tos_t);
		tg.add(dscp_t);
		tg.add(ipp_t);
		tg.add(cos_t);
		toField.setEditable(false);
		fromField.getDocument().addDocumentListener(ql);
		tos_f.addActionListener(ql);
		dscp_f.addActionListener(ql);
		ipp_f.addActionListener(ql);
		cos_f.addActionListener(ql);
		tos_t.addActionListener(ql);
		dscp_t.addActionListener(ql);
		ipp_t.addActionListener(ql);
		cos_t.addActionListener(ql);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(fl, c);
		c.gridx = 1;
		pane.add(fromField, c);
		c.gridx = 2;
		pane.add(tos_f, c);
		c.gridx = 3;
		pane.add(dscp_f, c);
		c.gridx = 4;
		pane.add(ipp_f, c);
		c.gridx = 5;
		pane.add(cos_f, c);
		c.gridx = 0;
		c.gridy = 1;
		pane.add(tl, c);
		c.gridx = 1;
		pane.add(new JScrollPane(toField), c);
		c.gridx = 2;
		pane.add(tos_t, c);
		c.gridx = 3;
		pane.add(dscp_t, c);
		c.gridx = 4;
		pane.add(ipp_t, c);
		c.gridx = 5;
		pane.add(cos_t, c);

		info.setEditable(false);
		info.setText("Possibles inputs for DSCP might be either:" +
			NetMemo.NEW_LINE +
			"-- Keywords: none, default, cs[0-7], ef, af[1-4][1-3]" + 
			NetMemo.NEW_LINE +
			"-- Hexdecimal value, e.g: 0x27 (range: 0..0x39)" + 
			NetMemo.NEW_LINE +
			"-- Decimal value, e.g: 45 (range: 0..63)" + 
			NetMemo.NEW_LINE);
		p.add(pane, BorderLayout.CENTER);
		p.add(new JScrollPane(info), BorderLayout.SOUTH);
		return p;
		//return pane;
	}

	private JPanel makeQoS_mark() {
		JPanel pane = new JPanel(new BorderLayout());
		Properties props = new Properties();
		JTable table;
		String notes[];
		JTextArea na;
		JPanel np = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton copy = new JButton("Copy table to clipboard", 
				createImageIcon(resources.
						getString(NETMEMO_BUTTON_COPY_CLIPBOARD), true));

		try {
			props.load(getClass().getClassLoader().
					getResourceAsStream(NetMemo.QOS_RFC_MARKING));
		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
			props = null;
		}

		if (props == null) {
			return pane;
		}
		table = MetaData.makeTable(props);
		notes = MetaData.getNotes(props);
		copy.addActionListener(new CopyTable(this, table));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		np.add(copy, c);
		pane.add(np, BorderLayout.NORTH);
		pane.add(new JScrollPane(table), BorderLayout.CENTER);
		if (notes != null) {
			na = new JTextArea(notes[0]);
			na.setEditable(false);
			pane.add(new JScrollPane(na), BorderLayout.SOUTH);
		}

		return pane;
	}

	private JPanel makeQoS_autoqos() {
		JPanel pane = new JPanel(new BorderLayout());
		Properties props = new Properties();
		JTable table;
		String notes[] = null;
		JPanel np = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton copy = new JButton("Copy table to clipboard", 
				createImageIcon(resources.
						getString(NETMEMO_BUTTON_COPY_CLIPBOARD), true));

		try {
			props.load(getClass().getClassLoader().
					getResourceAsStream(NetMemo.QOS_AUTOQOS));
			table = MetaData.makeTable(props);
			notes = MetaData.getNotes(props);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
			table = new JTable();
		}
		copy.addActionListener(new CopyTable(this, table));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		np.add(copy, c);
		pane.add(np, BorderLayout.NORTH);
		pane.add(new JScrollPane(table), BorderLayout.CENTER);
		if (notes != null) {
			pane.add(new JScrollPane(new JLabel(notes[0])), BorderLayout.SOUTH);
		}
		
		return pane;		
	}

	private JPanel makeIPv4Panel() {
		JPanel pane = new JPanel(new BorderLayout());
		JPanel cmd_pane = new JPanel(new FlowLayout());
		JLabel cmd_l = new JLabel("Selection: ");
		final JPanel body = new JPanel(new CardLayout());
		JPanel dec_pane = makeIPv4_dec_conv();
		JPanel adr_pane = makeIPv4_adress();
		String[] choicesStr = new String[] {
				"Dotted Decimal <-> Decimal Conversions",
				"Address Calculator"
				};
		JComboBox<String> choices = new JComboBox<String>(choicesStr);

		choices.setEditable(false);
		choices.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
		        CardLayout cl = (CardLayout)(body.getLayout());
		        cl.show(body, (String)evt.getItem());
		    }});
		cmd_pane.add(cmd_l, BorderLayout.CENTER);
		cmd_pane.add(choices, BorderLayout.CENTER);
		dec_pane.setBorder(new TitledBorder("Dotted Decimal IP Address / Decimal Address Conversion"));
		adr_pane.setBorder(new TitledBorder("Network Address Calculator"));
		body.add(dec_pane, choicesStr[0]);
		body.add(adr_pane, choicesStr[1]);

		pane.add(cmd_pane, BorderLayout.NORTH);
		pane.add(body, BorderLayout.CENTER);

		return pane;
	}

	private JPanel makeIPv4_dec_conv() {
		JPanel pane = new JPanel(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel dot2dec = new JPanel(new GridBagLayout());
		JPanel dec2dot = new JPanel(new GridBagLayout());
		JLabel ipal1 = new JLabel("IP Address: ");
		JLabel ipal2 = new JLabel("IP Address: ");
		JLabel dal1 = new JLabel("Decimal Address: ");
		JLabel dal2 = new JLabel("Decimal Address: ");
		final JTextField ipa1 = new JTextField(20);
		final JTextField ipa2 = new JTextField(20);
		final JTextField da1 = new JTextField(20);
		final JTextField da2 = new JTextField(20);

		dec2dot.setBorder(new TitledBorder("Decimal value to Dotted address"));
		dot2dec.setBorder(new TitledBorder("Dotted address to Decimal value"));
		da1.setEditable(false);
		ipa2.setEditable(false);
		ipa1.getDocument().addDocumentListener(new DocumentListener(){
			private void dot2dec(DocumentEvent ect) {
				String ip = ipa1.getText();
				String dec = IPv4.dotted2decimal(ip);

				da1.setText((dec == null? "": dec));
			}

			@Override
			public void changedUpdate(DocumentEvent evt) {
				//Plain text components don't fire these events.				
			}

			@Override
			public void insertUpdate(DocumentEvent evt) {
				dot2dec(evt);
			}

			@Override
			public void removeUpdate(DocumentEvent evt) {
				dot2dec(evt);
			}});
		da2.getDocument().addDocumentListener(new DocumentListener(){
			private void dec2dot(DocumentEvent ect) {
				String dec = da2.getText();
				String ip = IPv4.decimal2dotted(dec);

				ipa2.setText((ip == null? "": ip));
			}

			@Override
			public void changedUpdate(DocumentEvent evt) {
				//Plain text components don't fire these events.				
			}

			@Override
			public void insertUpdate(DocumentEvent evt) {
				dec2dot(evt);
			}

			@Override
			public void removeUpdate(DocumentEvent evt) {
				dec2dot(evt);
			}});
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		dot2dec.add(ipal1, c);
		c.gridx = 1;
		dot2dec.add(ipa1, c);
		c.gridx = 0;
		c.gridy = 1;
		dot2dec.add(dal1, c);
		c.gridx = 1;
		dot2dec.add(da1, c);

		c.gridx = 0;
		c.gridy = 0;
		dec2dot.add(dal2, c);
		c.gridx = 1;
		dec2dot.add(da2, c);
		c.gridx = 0;
		c.gridy = 1;
		dec2dot.add(ipal2, c);
		c.gridx = 1;
		dec2dot.add(ipa2, c);

		pane.add(dot2dec);
		pane.add(dec2dot);

		return pane;
	}

	private JPanel makeIPv4_adress() {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel ipl = new JLabel("IP Address: ");
		final JTextField ipa = new JTextField(20);
		JLabel nml1 = new JLabel("Netmask: ");
		final JSpinner nm = new JSpinner();
		final JTextField nm2 = new JTextField("0.0.0.0");
		JLabel nal = new JLabel("Network Address: ");
		final JTextField na = new JTextField(20);
		JLabel bal = new JLabel("Broadcast Address: ");
		final JTextField ba = new JTextField(20);
		JLabel rl = new JLabel("Range: ");
		final JTextField ra = new JTextField(40);
		SpinnerListModel nm_model;
		JSpinner.DefaultEditor nm_editor;
		//JFormattedTextField field;
		//DefaultFormatter formatter;
		//JComponent comp;
		String masks[] = new String[33];

		for (int i=0; i<=32; i++) {
			masks[i] = "/" + i;
		}
		//nm_model = new SpinnerListModel(masks);
		nm_model = new CyclingSpinnerListModel(masks);
		nm.setModel(nm_model);
		nm_editor = new JSpinner.DefaultEditor(nm);
		nm.setEditor(nm_editor);

		//comp = nm.getEditor();
		//field = (JFormattedTextField) comp.getComponent(0);

		//formatter = (DefaultFormatter) field.getFormatter();
		//formatter.setCommitsOnValidEdit(true);

		nm2.setEditable(false);
		na.setEditable(false);
		ba.setEditable(false);
		ra.setEditable(false);
		ipa.getDocument().addDocumentListener(new DocumentListener(){
			@Override
	        public void insertUpdate(DocumentEvent e) {
	        	address_updated(e);
	        }

			@Override
	        public void removeUpdate(DocumentEvent e) {
	        	address_updated(e);
	        }

			@Override
	        public void changedUpdate(DocumentEvent e) {
	        	//Plain text components don't fire these events.
	        }

			private void address_updated(DocumentEvent e) {
				String ip = ipa.getText();
				String netmask = nm2.getText();
				String net = IPv4.getNetworkAddress(ip, netmask);
				String broadcast = IPv4.getBroadcastAddress(ip, netmask);
				String range[] = IPv4.getAddressesRange(ip, netmask);

				System.out.println("ip="+ip+" - netmask="+netmask+" - net="+net+" - broadcast="+broadcast);
				na.setText((net == null? "": net));
				ba.setText((broadcast == null? "": broadcast));
				if (range == null) {
					ra.setText("");
				} else {
					ra.setText(range[0] + " - " + range[1]);
				}
			}});
		nm.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				Object m = nm.getValue(); 
				int v;
				String adr = ipa.getText();
				String netmask;
				String net;
				String broadcast;
				String[] range;
	
				if (m == null) {
					return;
				}
				try {
					v = Integer.parseInt(m.toString().substring(1));
					netmask = IPv4.getNetMask(v);
					net = IPv4.getNetworkAddress(adr, netmask);
					broadcast = IPv4.getBroadcastAddress(ipa.getText(), netmask);
					range = IPv4.getAddressesRange(adr, netmask);
					System.out.println("ip="+adr+" - netmask="+netmask+" - net="+net+" - broadcast="+broadcast);
					na.setText((net == null? "": net));
					ba.setText((broadcast == null? "": broadcast));
					if (range == null) {
						ra.setText("");
					} else {
						ra.setText(range[0] + " - "+ range[1]);
					}
					nm.revalidate();
					nm2.setText(netmask);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}});

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(ipl, c);
		c.gridx = 1;
		pane.add(ipa, c);
		c.gridx = 2;
		pane.add(nm, c);
		c.gridx = 0;
		c.gridy = 1;
		pane.add(nml1, c);
		c.gridx = 1;
		pane.add(nm2, c);
		c.gridx = 0;
		c.gridy = 2;
		pane.add(nal, c);
		c.gridx = 1;
		pane.add(na, c);
		c.gridx = 0;
		c.gridy = 3;
		pane.add(bal, c);
		c.gridx = 1;
		pane.add(ba, c);
		c.gridx = 0;
		c.gridy = 4;
		pane.add(rl, c);
		c.gridx = 1;
		pane.add(ra, c);

		return pane;
	}

	private JPanel makeIPv6Panel () {
		JPanel pane = new JPanel(new BorderLayout());
		// ISATAP
		// [64-bit link-local or global unicast prefix]:0000:5EFE:[IPv4 address of the ISATAP link]

		// automatic 6to4
		// 2002:border-router-IPv4-address::/48

		// automatic ipv4-compatible addresses
		// ::A.B.C.D or ::[A-in-hex][B-in-hex]:[C-in-hex][D-in-hex]

		String[] choicesStr = new String[] {
				"EUI-64",
				"Tunneling",
				"Miscellaneous"
			};
		JComboBox<String> choices = new JComboBox<String>(choicesStr);
		JPanel cmd = new JPanel();
		final JPanel body = new JPanel(new CardLayout());
		JPanel eui64Pane = makeIPv6_eui64();
		JPanel miscPane = makeIPv6_misc();
		JPanel tunnelsPane = makeIPv6_tunnels();

		choices.setEditable(false);
		choices.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
		        CardLayout cl = (CardLayout)(body.getLayout());
		        cl.show(body, (String)evt.getItem());
		    }});
		miscPane.setBorder(new javax.swing.border.TitledBorder("Miscellaneous settings"));
		eui64Pane.setBorder(new javax.swing.border.TitledBorder("EUI-64 address settings"));
		tunnelsPane.setBorder(new javax.swing.border.TitledBorder("Tunnels settings"));

		cmd.add(new JLabel("Selection:"));
		cmd.add(choices);
		body.add(eui64Pane, choicesStr[0]);
		body.add(tunnelsPane, choicesStr[1]);
		body.add(miscPane, choicesStr[2]);

		pane.add(cmd, BorderLayout.PAGE_START);
		pane.add(body, BorderLayout.CENTER);

		return pane;
	}

	private JPanel makeIPv6_eui64() {
		JPanel pane = new JPanel(new GridBagLayout());
		JPanel prefixPane = new JPanel(new GridBagLayout());
		JPanel prefixCards = new JPanel(new CardLayout());
		GridBagConstraints c = new GridBagConstraints();
		JCheckBox hasPrefix = new JCheckBox("IPv6 prefix");
		JLabel macLabel = new JLabel("MAC:");
		JLabel eui64Label = new JLabel("EUI-64:");
		JLabel llLabel = new JLabel("Link-Local:");
		JLabel ipv6PrefixLabel = new JLabel("IPv6 prefix:");
		JLabel ipv6AddressLabel = new JLabel("IPv6 address:");
		JTextField macField = new JTextField(30);
		JTextField eui64 = new JTextField(30);
		JTextField linkLocal = new JTextField(30);
		JTextField ipv6Prefix = new JTextField(30);
		JTextField ipv6Address = new JTextField(30);

		hasPrefix.addItemListener(new EUI64ItemAdapter(prefixCards));
		eui64.setEditable(false);
		linkLocal.setEditable(false);
		ipv6Address.setEditable(false);
		macField.getDocument().addDocumentListener(new EUI64MACListener(eui64, linkLocal, ipv6Prefix, ipv6Address));
		ipv6Prefix.getDocument().addDocumentListener(new IPv6AddressListener(eui64, ipv6Address));

		c.gridx = 0;
		c.gridy = 0;
		pane.add(hasPrefix, c);

		c.gridy = 1;
		pane.add(macLabel, c);
		c.gridx = 1;
		pane.add(macField, c);

		c.gridx = 0;
		c.gridy = 2;
		pane.add(eui64Label, c);
		c.gridx = 1;
		pane.add(eui64, c);

		c.gridx = 0;
		c.gridy = 3;
		pane.add(llLabel, c);
		c.gridx = 1;
		pane.add(linkLocal, c);

		c.gridx = 0;
		c.gridy = 0;
		prefixPane.add(ipv6PrefixLabel, c);
		c.gridx = 1;
		prefixPane.add(ipv6Prefix);

		c.gridx = 0;
		c.gridy = 1;
		prefixPane.add(ipv6AddressLabel, c);
		c.gridx = 1;
		prefixPane.add(ipv6Address, c);

		prefixCards.add(prefixPane, "prefix");
		prefixCards.add(new JPanel(), "");
		((CardLayout)prefixCards.getLayout()).show(prefixCards, "");
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		pane.add(new JLabel(" "), c);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		pane.add(prefixCards, c);

		return pane;
	}

	private JPanel makeIPv6_misc() {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel ipv6Label = new JLabel("IPv6 Address: ");
		JTextField ipv6Address = new JTextField(20);
		JLabel solnodLabel = new JLabel("Solicited Node Address: ");
		JTextField solnod = new JTextField(20);

		ipv6Address.getDocument().addDocumentListener(new SolNodAdapter(solnod));
		solnod.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		pane.add(ipv6Label, c);
		c.gridx = 1;
		pane.add(ipv6Address, c);

		c.gridx = 0;
		c.gridy = 1;
		pane.add(solnodLabel, c);
		c.gridx = 1;
		pane.add(solnod, c);

		return pane;		
	}
	
	private JPanel makeIPv6_tunnels() {
		// ISATAP
		// [64-bit link-local or global unicast prefix]:0000:5EFE:[IPv4 address of the ISATAP link]
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel ipv6Label = new JLabel("IPv6 Prefix: ");
		JTextField ipv6Prefix = new JTextField(20);
		JLabel ipv4Label = new JLabel("IPv4 Tunnel Address: ");
		JTextField ipv4Address = new JTextField(20);
		JLabel isatapLabel = new JLabel("ISATAP Tunnel Address: ");
		JTextField isatapAddress = new JTextField(20);
		JLabel auto6to4Label = new JLabel("Automatic 6to4 Address: ");
		JTextField auto6to4 = new JTextField(20);
		JLabel ipv4CompLabel = new JLabel("Automatic IPv4-Compatible Address: ");
		JTextField ipv4Compatible = new JTextField(20);
		JLabel ipv4MappedLabel = new JLabel("Automatic IPv4-Mapped Address: ");
		JTextField ipv4Mapped = new JTextField(20);

		ipv6Prefix.getDocument().addDocumentListener(new TunnelingAdapater(ipv6Prefix, ipv4Address, isatapAddress, auto6to4, ipv4Compatible, ipv4Mapped));
		ipv4Address.getDocument().addDocumentListener(new TunnelingAdapater(ipv6Prefix, ipv4Address, isatapAddress, auto6to4, ipv4Compatible, ipv4Mapped));
		isatapAddress.setEditable(false);
		auto6to4.setEditable(false);
		ipv4Compatible.setEditable(false);
		ipv4Mapped.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		pane.add(ipv6Label, c);
		c.gridx = 1;
		pane.add(ipv6Prefix, c);
		c.gridx = 0;
		c.gridy = 1;
		pane.add(ipv4Label, c);
		c.gridx = 1;
		pane.add(ipv4Address, c);
		c.gridx = 0;
		c.gridy = 2;
		pane.add(isatapLabel, c);
		c.gridx = 1;
		pane.add(isatapAddress, c);
		c.gridx = 0;
		c.gridy = 3;
		pane.add(auto6to4Label, c);
		c.gridx = 1;
		pane.add(auto6to4, c);
		c.gridx = 0;
		c.gridy = 4;
		pane.add(ipv4CompLabel, c);
		c.gridx = 1;
		pane.add(ipv4Compatible, c);
		c.gridx = 0;
		c.gridy = 5;
		pane.add(ipv4MappedLabel, c);
		c.gridx = 1;
		pane.add(ipv4Mapped, c);

		return pane;
	}

	private JPanel makeMCPanel () {
		//JPanel pane = new JPanel(new GridBagLayout());
		JPanel pane = new JPanel(new GridBagLayout());//FlowLayout());
		JPanel ipv4macPane = new JPanel(new GridBagLayout());
		JPanel ipv6macPane = new JPanel(new GridBagLayout());
		JPanel macipPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		String[] choices = new String[] {"IPv4 to MAC:", "IPv6 to MAC:", "MAC to IP:"};
		JTextField ipv4Field = new JTextField();
		JTextField ipv6Field = new JTextField();
		JTextField mf1 = new JTextField(20);
		JTextField mf2 = new JTextField(20);
		JTextField mf6 = new JTextField(20);
		JList<String> ipList = new JList<String>();
		copy = new JButton("Copy to clipboard", 
				createImageIcon(resources.
						getString(NETMEMO_BUTTON_COPY_CLIPBOARD), true));

		ipv4Field.getDocument().addDocumentListener(new IPv4ActionAdapter(mf1));
		ipv6Field.getDocument().addDocumentListener(new IPv6ActionAdapter(mf6));
		mf2.getDocument().addDocumentListener(new MACActionAdapter(this, ipList));
		mf1.setEditable(false);
		mf6.setEditable(false);
		ipList.setModel(new DefaultListModel<String>());
		//choice.addItemListener(this);
		copy.setEnabled(false);
		copy.addActionListener(new CopyAdapter(this, ipList));

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		ipv4macPane.add(new JLabel("IP:"), c);
		ipv6macPane.add(new JLabel("IP:"), c);
		c.gridx = 1;
		ipv4macPane.add(ipv4Field, c);
		ipv6macPane.add(ipv6Field, c);
		c.gridx = 0;
		c.gridy = 1;
		ipv4macPane.add(new JLabel("MAC:"), c);
		ipv6macPane.add(new JLabel("MAC:"), c);
		c.gridx = 1;
		ipv4macPane.add(mf1, c);
		ipv6macPane.add(mf6, c);

		c.gridx = 0;
		c.gridy = 0;
		macipPane.add(new JLabel("MAC:"), c);
		c.gridx = 1;
		macipPane.add(mf2, c);
		c.gridx = 0;
		c.gridy = 1;
		macipPane.add(new JLabel("Matching IPv4 adresses:"), c);
		c.gridx = 1;
		macipPane.add(new JScrollPane(ipList), c);
		c.gridx = 0;
		c.gridy = 2;
		macipPane.add(copy, c);

		ipv4macPane.setBorder(new javax.swing.border.TitledBorder(choices[0]));
		ipv6macPane.setBorder(new javax.swing.border.TitledBorder(choices[1]));
		macipPane.setBorder(new javax.swing.border.TitledBorder(choices[2]));
		c.gridx = 0;
		c.gridy = 0;
		//c.gridheight = 20;
		pane.add(ipv4macPane, c);
		c.gridy = 1;
		pane.add(ipv6macPane, c);
		c.gridy = 2;
		pane.add(new JLabel(" "), c);
		c.gridy = 3;
		pane.add(new JLabel(" "), c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 4;
		pane.add(macipPane, c);
		return pane;
	}

	private JPanel makeSecurityPanel() {
		final JTextArea list = new JTextArea(10, 20);
		final JPanel pane = new JPanel(new CardLayout());
		JPanel pwd_gen_pane = makeSecurity_pwd_outset(pane, list);
		JPanel pwd_conf_pane = makeSecurity_pwd_settings(pane, list);

		list.setEditable(false);
		pane.add(pwd_gen_pane, "outset");
		pane.add(pwd_conf_pane, "settings");
		((CardLayout)pane.getLayout()).show(pane, "settings");
		return pane;
	}

	private JPanel makeSecurity_pwd_outset(final JPanel card, final JTextArea list) {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel lbl = new JLabel("Passwords: ");
		JPanel cmd_pane = new JPanel(new GridBagLayout());
		JButton back = new JButton("Back", createImageIcon(resources.
				getString(NETMEMO_BUTTON_BACK), true));
		JButton copy = new JButton("Copy to clipboard", createImageIcon(resources.
				getString(NETMEMO_BUTTON_COPY_CLIPBOARD), true));
		JButton save = new JButton("Export to file", createImageIcon(resources.
				getString(NETMEMO_BUTTON_SAVE), true));

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				((CardLayout)card.getLayout()).show(card, "settings");
			}});
		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection stringSelection;

				stringSelection = new StringSelection(list.getText());
			    clipboard.setContents(stringSelection, new ClipboardOwner() {
					/**
					 * Empty implementation of the ClipboardOwner interface.
					 */
					public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
						//do nothing
					}});
			    trayIcon.displayMessage("Passwords copy", 
			    		"Passwords list copied to clipboard", 
			    		TrayIcon.MessageType.INFO);
			    JOptionPane.showMessageDialog(NetMemo.this, 
			    		"Passwords list copied to clipboard");
			}});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser();
				File f;
				FileOutputStream fos;
				PrintStream ps;
				String filename;
				File dir;
				
				int r = fc.showOpenDialog(NetMemo.this);

				if (r != JFileChooser.APPROVE_OPTION) {
					return;
				}
				f = fc.getSelectedFile();
				if (f.exists()) {
					r = JOptionPane.showConfirmDialog(NetMemo.this,
							"Overwrite already existing file '"+f.getAbsolutePath()+"' ?",
                            "Confirm file Overwrite...",
                            JOptionPane.YES_NO_OPTION);
					if (r != JOptionPane.YES_OPTION) {
						return;
					}
				}
				try {
					filename = f.getAbsolutePath();
					dir = f.getParentFile();
					addToDiskUsageHistory("creating file '"+filename+"'...");
					fos = new FileOutputStream(f.getAbsolutePath());
					ps = new PrintStream(fos);
					ps.print(list.getText());
					addToDiskUsageHistory("passwords sucessfuly exported to file:'"+filename+"'");
				    trayIcon.displayMessage("Passwords export", 
				    		"Passwords list copied to file '"+filename+"'", 
				    		TrayIcon.MessageType.INFO);
				    if (desktop.isSupported(Desktop.Action.OPEN)) {
			            desktop.open(f);
			            desktop.open(dir);
			        }
					JOptionPane.showMessageDialog(NetMemo.this, 
							"Data sucessfully exported to file '"+
							filename+"'");
					fos.close();
				} catch (FileNotFoundException e) {
					addToDiskUsageHistory("I/O exception occured during the export process... [" +
							e.getMessage() +"]");
					e.printStackTrace();
				} catch (IOException ioe) {
					addToDiskUsageHistory("I/O exception occured during the export process... [" +
							ioe.getMessage() +"]");
					ioe.printStackTrace();
				}
			}});

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		cmd_pane.add(back, c);
		c.gridx = 1;
		cmd_pane.add(copy, c);
		c.gridx = 2;
		cmd_pane.add(save, c);
		c.gridx = 0;
		pane.add(lbl, c);
		c.gridy = 1;
		pane.add(new JScrollPane(list), c);
		c.gridy = 2;
		pane.add(cmd_pane, c);

		return pane;
	}

	private JPanel makeSecurity_pwd_settings(final JPanel card, final JTextArea list) {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		final JCheckBox incSP = new JCheckBox("only strong passwords");
		final JCheckBox incLC = new JCheckBox("include lower cases letters [a-z]");
		final JCheckBox incUC = new JCheckBox("include upper cases letters [A-Z]");
		final JCheckBox incAD = new JCheckBox("include arabic digits [0-9]");
		final JCheckBox incHD = new JCheckBox("include hexadecimal digits [0-9a-f]");
		final JCheckBox incPC = new JCheckBox("include punctions characters");
		JLabel pll = new JLabel("Password length: ");
		JLabel post_p = new JLabel("   [1-"+NetMemo.SEC_MAX_PWD_LENGTH+"]");
		final JSpinner pwd;
		SpinnerListModel pwd_model;
		JSpinner.DefaultEditor pwd_editor;
		JLabel ql = new JLabel("Quantity: ");
		JLabel post_q = new JLabel("   [1-"+NetMemo.SEC_MAX_PWD_COUNT+"]");
		final JSpinner qt;
		SpinnerListModel qt_model;
		JSpinner.DefaultEditor qt_editor;
		Integer a_p[] = new Integer[NetMemo.SEC_MAX_PWD_LENGTH];
		Integer a_q[] = new Integer[NetMemo.SEC_MAX_PWD_COUNT];
		JComponent comp_p, comp_q;
		JFormattedTextField field_p, field_q;
		JButton generate = new JButton("Generate", createImageIcon(resources.
				getString(NETMEMO_BUTTON_EDIT), true));
		DefaultFormatter formatter_p, formatter_q;
		//net.sf.jnetparse.security.PasswordGenerator pg = new net.sf.jnetparse.security.PasswordGenerator();

		for (int i=0; i<a_p.length; i++) {
			a_p[i] = new Integer(i+1);
		}
		for (int i=0; i<a_q.length; i++) {
			a_q[i] = new Integer(i+1);
		}
		pwd_model = new CyclingSpinnerListModel(a_p); //new SpinnerListModel(a_p);
		qt_model  = new CyclingSpinnerListModel(a_q); //new SpinnerListModel(a_q);
		pwd = new JSpinner();
		qt  = new JSpinner();
		pwd.setModel(pwd_model);
		qt.setModel(qt_model);
		pwd_editor = new JSpinner.DefaultEditor(pwd);
		qt_editor  = new JSpinner.DefaultEditor(qt);
		pwd.setEditor(pwd_editor);
		qt.setEditor(qt_editor);
		comp_p = pwd.getEditor();
	    field_p = (JFormattedTextField) comp_p.getComponent(0);
		comp_q = qt.getEditor();
	    field_q = (JFormattedTextField) comp_q.getComponent(0);
	    formatter_p = (DefaultFormatter) field_p.getFormatter();
	    formatter_q = (DefaultFormatter) field_q.getFormatter();
	    formatter_p.setCommitsOnValidEdit(true);
	    formatter_q.setCommitsOnValidEdit(true);
	    field_p.setEditable(true);
	    field_q.setEditable(true);

	    pwd.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				int v = Integer.valueOf(pwd.getValue().toString());
				int p = ((Integer)pwd.getPreviousValue()).intValue();
				int n = ((Integer)pwd.getNextValue()).intValue();

				if (incSP.isSelected() == false) {
					return;
				}
				if (v < NetMemo.SEC_PWD_LEN_THRESHOLD) {
					if (p < NetMemo.SEC_PWD_LEN_THRESHOLD) {
						pwd.setValue(new Integer(NetMemo.SEC_MAX_PWD_LENGTH));
					} else {
						pwd.setValue(new Integer(NetMemo.SEC_PWD_LEN_THRESHOLD));
					}
					try {
						pwd.commitEdit();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}});
	    incSP.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				boolean toggle = !incSP.isSelected();
				boolean letter = incLC.isSelected() || incUC.isSelected();
				int len = Integer.parseInt(pwd.getValue().toString());

				incLC.setEnabled(toggle);
				incUC.setEnabled(toggle);
				incPC.setEnabled(toggle);
				if (toggle == false) {
					incAD.setEnabled(toggle);
					incHD.setEnabled(toggle);
					if (len < NetMemo.SEC_PWD_LEN_THRESHOLD) {
						pwd.setValue(new Integer(NetMemo.SEC_PWD_LEN_THRESHOLD));
						try {
							pwd.commitEdit();
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} else {
					incAD.setEnabled(!incHD.isSelected());
					incHD.setEnabled(!(letter && incAD.isSelected()));
				}
			}});
	    incLC.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				boolean arab = incAD.isSelected();
				boolean letter = incLC.isSelected() || incUC.isSelected();
				boolean toggle = letter && arab;

				incHD.setEnabled(!toggle);
				if (incHD.isEnabled() && incHD.isSelected()) {
					incAD.setEnabled(false);
				}
			}});
	    incUC.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				boolean arab = incAD.isSelected();
				boolean letter = incLC.isSelected() || incUC.isSelected();
				boolean toggle = letter && arab;

				incHD.setEnabled(!toggle);
				if (incHD.isEnabled() && incHD.isSelected()) {
					incAD.setEnabled(false);
				}
			}});
	    incAD.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				boolean arab = incAD.isSelected();
				boolean letter = incLC.isSelected() || incUC.isSelected();
				boolean toggle = letter && arab;

				if (incSP.isSelected()) {
					incAD.setEnabled(false);
					return;
				}
				incHD.setEnabled(!toggle);
			}});
	    incHD.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (incSP.isSelected()) {
					incHD.setEnabled(false);
					return;
				}
				incAD.setEnabled(!incHD.isSelected());
			}});
	    generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				boolean next = incSP.isSelected() ||
					incLC.isSelected() ||
					incUC.isSelected() ||
					incAD.isSelected() ||
					incHD.isSelected() ||
					incPC.isSelected();
				PasswordGenerator pg;
				int len = Integer.parseInt(pwd.getValue().toString());
				int count = Integer.parseInt(qt.getValue().toString());
				ArrayList<String> pwd_l;

				if (next == false) {
					return;
				}
				if (incSP.isSelected()) {
					pg = new PasswordGenerator(len);
				} else {
					pg = new PasswordGenerator(incLC.isSelected(),
							incUC.isSelected(),
							incAD.isSelected(),
							incHD.isSelected(),
							incPC.isSelected(),
							len);
				}
				NetMemo.this.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
				pwd_l = pg.next(count);
				if (pwd_l == null) {
					return;
				}
				list.setText("");
				assert (count == pwd_l.size());
				for (int i=0; i < count; i++) {
					list.append(pwd_l.get(i));
					list.append(NetMemo.NEW_LINE);
				}
				((CardLayout)card.getLayout()).show(card, "outset");
				NetMemo.this.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}});
	    

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(incSP, c);
		c.gridy = 1;
		pane.add(incLC, c);
		c.gridy = 2;
		pane.add(incUC, c);
		c.gridy = 3;
		pane.add(incAD, c);
		c.gridy = 4;
		pane.add(incHD, c);
		c.gridy = 5;
		pane.add(incPC, c);
		c.gridy = 6;
		pane.add(pll, c);
		c.gridx = 1;
		pane.add(pwd, c);
		c.gridx = 2;
		pane.add(post_p, c);
		c.gridx = 0;
		c.gridy = 7;
		pane.add(ql, c);
		c.gridx = 1;
		pane.add(qt, c);
		c.gridx = 2;
		pane.add(post_q, c);
		c.gridx = 1;
		c.gridy = 8;
		pane.add(new JLabel(" "), c);
		c.gridy = 9;
		pane.add(generate, c);

		return pane;
	}

	private Popup popup = null;

	private JPanel makeTSPanel() {
		JPanel pane = new JPanel(new BorderLayout());
		GridBagConstraints c = new GridBagConstraints();
		String[] choicesStr = new String[] {
				"Cisco technology", 
				"      >> Configuration Register settings",
				"      >> Summary of useful troubleshooting commands"
				};
		JComboBox<String> choices = new JComboBox<String>(choicesStr);
		JPanel cmd = new JPanel(new FlowLayout());
		JPanel confReg = new ConfigRegisterPanel(this);
		final JPanel body = new JPanel(new CardLayout());
		final Properties pt;
		final JTree tree;
		final JTable table;

		pt = MetaData.loadData(getClass().getClassLoader().
				getResourceAsStream(NetMemo.TROUBLESHOOTING_MEMO));
		tree = MetaData.makeTree(pt);
		table = new JTable();
		table.addMouseMotionListener(new MouseMotionListener() {
			private PopupFactory factory = PopupFactory.getSharedInstance();

			@Override
			public void mouseDragged(MouseEvent me) {}

			@Override
			public void mouseMoved(MouseEvent me) {
				final JTextArea desc = new JTextArea(10, 30);
				final JScrollPane sp;
				Font font = desc.getFont();
				FontMetrics fm;
				int x = me.getXOnScreen();
				int y = me.getYOnScreen();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				int col = table.columnAtPoint(p);

				if ((row == -1) || (col == -1)) {
					if (popup != null) {
						popup.hide();
					}
					return;
				}
				desc.setEditable(false);
				desc.setFont(font.deriveFont(Font.BOLD));
				desc.setBackground(new Color(255, 249, 189));
				desc.setForeground(Color.BLACK);
				desc.setText(table.getValueAt(row, col).toString());
				desc.setLineWrap(true);
				desc.setWrapStyleWord(true);
				fm = desc.getFontMetrics(font);
				desc.setPreferredSize(new Dimension(fm.getHeight()*10, fm.getHeight()*30));
				desc.setCaretPosition(0);
				desc.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent ke) {
						if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
							if (popup != null) {
								popup.hide();
							}
						}
					}});
				if (popup != null) {
					popup.hide();
				}
				sp = new JScrollPane(desc,
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				popup = factory.getPopup(table, sp, x, y);
				popup.show();
				Thread runner = new Thread(new Runnable() {
					private boolean must_run  = true;
					@Override
					public void run() {
						Point mouseLocation;
						Point tableLocation;
						Point popupLocation;
						Rectangle tableBounds;
						Rectangle popupBounds;	

						while (must_run) {
							mouseLocation = MouseInfo.getPointerInfo().getLocation();
							tableLocation = table.getLocationOnScreen();
							try {
								popupLocation = sp.getLocationOnScreen();
							} catch (IllegalComponentStateException icse) {
								continue;
							}
							tableBounds = table.getBounds();
							popupBounds = sp.getBounds();
							tableBounds.translate(tableLocation.x, tableLocation.y);
							popupBounds.translate(popupLocation.x, popupLocation.y);
							must_run = tableBounds.contains(mouseLocation) || 
								popupBounds.contains(mouseLocation) ||
								sp.hasFocus();
							try {
								Thread.currentThread().sleep(100L);
							} catch (InterruptedException ie) {}
						}
						popup.hide();
					}});
				runner.start();
			}});

		JSplitPane sp_cmd = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(tree),
				new JScrollPane(table)
				);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (popup != null) {
						popup.hide();
					}
				}	
			}});
		tree.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (popup != null) {
						popup.hide();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent ke) {}

			@Override
			public void keyTyped(KeyEvent ke) {}
			});
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent tse) {
				TreePath tp = tse.getPath();
				Point p = MouseInfo.getPointerInfo().getLocation();

				if (popup != null) {
					popup.hide();
				}
			}});

		choices.setEditable(false);
		choices.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
		        CardLayout cl = (CardLayout)(body.getLayout());
		        cl.show(body, (String)evt.getItem());
		    }});
		cmd.add(new JLabel("Selection: "));
		cmd.add(choices);


		//Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
					tree.getLastSelectedPathComponent();
				Object nodeInfo;

				if (node == null) return;
				nodeInfo = node.getUserObject();
				if (node.isLeaf()) {
					TopicInfo ti = (TopicInfo)nodeInfo;
					display_table(pt, table, ti);
				} else if (node.isRoot()) {
					table.setModel(new DefaultTableModel());
				}
			}});
		tree.expandRow(0);

		sp_cmd.setOneTouchExpandable(true);
		sp_cmd.setDividerLocation(150);

		body.add(makeCiscoPanel(choicesStr, body, choices), choicesStr[0]);
		body.add(new JScrollPane(confReg), choicesStr[1]);
		body.add(sp_cmd, choicesStr[2]);
		pane.add(cmd, BorderLayout.NORTH);
		pane.add(body, BorderLayout.CENTER);
		
		return pane;
	}

	private JPanel makeCiscoPanel(final String[] list, final JPanel body, final JComboBox choices) {
		JPanel cp = new JPanel();
		final JList<String> cmd = new JList<String>(Arrays.copyOfRange(list, 1, list.length));
		JScrollPane sp = new JScrollPane(cmd); 

		cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
		cmd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cmd.setToolTipText("Double-click the item you wish to use");
		cmd.setAlignmentX(LEFT_ALIGNMENT);
		cmd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int idx = 1 + cmd.locationToIndex(evt.getPoint());

					do_update_cisco_list_display(choices, body, list, idx);
				}
			}});
		cmd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = 1 + cmd.getSelectedIndex();

					do_update_cisco_list_display(choices, body, list, idx);
				}
			}});
		sp.setAlignmentX(LEFT_ALIGNMENT);
		cp.add(sp);
		return cp;
	}

	private JPanel makeDUHPanel() {
		JPanel pane = new JPanel();
		Box ctrl = Box.createHorizontalBox();
		JButton copy = new JButton("Copy logs to clipboard", createImageIcon(resources.
				getString(NETMEMO_BUTTON_COPY_CLIPBOARD), true));
		JButton clear = new JButton("Clear logs", createImageIcon(resources.
				getString(NETMEMO_BUTTON_CLEAR), true));

		diskUsageHistory = new JTextArea(25, 80);
		copy.addActionListener(new ActionListener() {
			private ClipboardOwner clip = new ClipboardOwner() {
				/**
				 * Empty implementation of the ClipboardOwner interface.
				 */
				@Override
				public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
					//do nothing
				}};
			@Override
			public void actionPerformed(ActionEvent ae) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection stringSelection = new StringSelection(diskUsageHistory.getText());

			    clipboard.setContents(stringSelection, clip);
			}});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int response = JOptionPane.showConfirmDialog(NetMemo.this, 
						"Would you like to reset disk usage logging?", 
						"Logs clearing...", 
						JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					clearDiskUsageHistory();
				}
			}});
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		diskUsageHistory.setEditable(false);
		diskUsageHistory.setBackground(Color.BLACK);
		diskUsageHistory.setForeground(Color.ORANGE);
		ctrl.add(copy);
		ctrl.add(Box.createHorizontalGlue());
		ctrl.add(clear);
		pane.add(new JScrollPane(diskUsageHistory));
		pane.add(ctrl);
		return pane;
	}

	protected void addToDiskUsageHistory(String log) {
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();

		diskUsageHistory.append(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) +
				", " +
				cal.get(Calendar.DAY_OF_MONTH) +
				" " +
				cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + 
				" " +
				cal.get(Calendar.YEAR) + 
				" at " +
				cal.get(Calendar.HOUR_OF_DAY) +
				":" +
				cal.get(Calendar.MINUTE) +
				":" +
				cal.get(Calendar.SECOND)
				);
		diskUsageHistory.append(" ::[");
		diskUsageHistory.append(log);
		diskUsageHistory.append("]");
		diskUsageHistory.append(NetMemo.NEW_LINE);
		diskUsageHistory.append("-----");
		diskUsageHistory.append(NetMemo.NEW_LINE);
	}

	protected void clearDiskUsageHistory() {
		diskUsageHistory.setText("");
	}

	private JPanel makeHostPanel() {
		JPanel pane = new JPanel(new BorderLayout());
		Enumeration<NetworkInterface> enum_ifs;
		Vector<NetworkInterface> net_ifs = new Vector<NetworkInterface>();
		final Vector<String> header  = new Vector<String>();
		final Vector<Vector> rowData = new Vector<Vector>();
		final TableModel model;
		JTable table = null;

		try {
			header.addElement("Interface");
			header.addElement("Description");
			header.addElement("Parent");
			header.addElement("IP Address(es)");
			header.addElement("MAC Address");
			header.addElement("MTU");
			header.addElement("Loopback");
			header.addElement("Virtual");
			header.addElement("Point-to-Point");
			header.addElement("Multicast");
			header.addElement("UP");

			enum_ifs = NetworkInterface.getNetworkInterfaces();
			while (enum_ifs.hasMoreElements()) {
				Vector<Object> row = new Vector<Object>();
				Vector<String> addresses = new Vector<String>();
				Enumeration<InetAddress> e;
				NetworkInterface ni   = enum_ifs.nextElement();
				NetworkInterface p_ni = ni.getParent(); 
				net_ifs.addElement(ni);
				row.addElement(ni.getName());
				row.addElement(ni.getDisplayName());
				row.addElement((p_ni == null? "": p_ni.getName()));
				e = ni.getInetAddresses();
				while (e.hasMoreElements()) {
					addresses.addElement(e.nextElement().getHostAddress());
				}
				row.addElement(addresses);
				row.addElement(MAC.toString(ni.getHardwareAddress()));
				row.addElement(new Integer(ni.getMTU()));
				row.addElement(new Boolean(ni.isLoopback()));
				row.addElement(new Boolean(ni.isVirtual()));
				row.addElement(new Boolean(ni.isPointToPoint()));
				row.addElement(new Boolean(ni.supportsMulticast()));
				row.addElement(new Boolean(ni.isUp()));
				rowData.addElement(row);
			}
			model = new AbstractTableModel() {
				public int getColumnCount() { return header.size(); }
				public int getRowCount() { return rowData.size();}
				public Object getValueAt(int row, int col) { 
					return rowData.elementAt(row).elementAt(col);
				}
				public boolean isCellEditable(int row, int col) {
					return (col == 3);
				}
				public String getColumnName(int col) {
					return header.elementAt(col);
				}
				public Class<?> getColumnClass(int col) {
					Class<?> cl;

					switch (col) {
						case 0:
						case 1:
						case 2:
						case 4:
							cl = String.class;
							break;
						case 3:
							cl = Vector.class;
							break;
						case 5:
							cl = Integer.class;
							break;
						case 6:
						case 7:
						case 8:
						case 9:
						case 10:
							cl = Boolean.class;
							break;
						default:
							cl = null;
					}
					return cl;
				}
		      };

			table = new JTable(model) {
				public TableCellEditor getCellEditor(int row, int col) {
					final Object o = model.getValueAt(row, col);
					final boolean b = model.isCellEditable(row, col);
					JTextField tf; 

					if (col == 3) {
						final Vector<String> v = (Vector<String>)o;
						int len = v.size();
						if (len == 0) {
							tf = new JTextField(20);
							tf.setEditable(false);
							return new DefaultCellEditor(tf);
						} else if (len == 1) {
							tf = new JTextField(v.elementAt(0).toString());
							tf.setEditable(false);
							return new DefaultCellEditor(tf);
						} else {
							JComboBox<String> combo = new JComboBox<String>(v);
							combo.setEditable(false);
							return new DefaultCellEditor(combo);
						}
					}
					return super.getCellEditor(row, col);
				}

//				public TableCellRenderer getCellRenderer(int row, int col) {
//					final Object o = model.getValueAt(row, col);
//					final boolean b = model.isCellEditable(row, col);
//
//					if (col == 3) {
//						final Vector v = (Vector)o;
//						int len = v.size();
//						if (len == 0) {
//							return new TableCellRenderer() {
//								@Override
//								public Component getTableCellRendererComponent(
//										JTable table, Object value,
//										boolean isSelected, boolean hasFocus,
//										int row, int col) {
//									return new JTextField(20);
//								}};
//						} else if (len == 1) {
//							return new TableCellRenderer() {
//								@Override
//								public Component getTableCellRendererComponent(
//										JTable table, Object value,
//										boolean isSelected, boolean hasFocus,
//										int row, int col) {
//									return new JTextField(v.elementAt(0).toString());
//								}};							
//						} else {
//							return new TableCellRenderer() {
//								@Override
//								public Component getTableCellRendererComponent(
//										JTable table, Object value, boolean isSelected,
//										boolean hasFocus, int row, int col) {
//									JComboBox combo = new JComboBox(v);
//									combo.setEditable(true);
//									System.out.println("cell editable="+b);
//									System.out.println(value.getClass().getName());
//									return combo;
//								}};
//						}
//					}
//					return super.getCellRenderer(row, col);
//				}
			};

//			table.setDefaultRenderer(Vector.class, new TableCellRenderer() {
//				@Override
//				public Component getTableCellRendererComponent(JTable table,
//						Object value, boolean isSelected, boolean hasFocus, 
//						int row, int col) {
//					Vector<String> v = (Vector<String>)rowData.elementAt(row).elementAt(col);
//					int len = v.size();
//					Component comp = null;
//					TableColumn tc = table.getColumnModel().getColumn(col); // col == 3
//
//					if (len == 0) {
//						comp = new JTextField(20);
//						((JTextField)comp).setEditable(false);
//					} else if (len == 1) {
//						comp = new JTextField(v.elementAt(0));
//						((JTextField)comp).setEditable(false);
//					} else {
//						comp = new JComboBox(v);
//						((JComboBox)comp).setEditable(false);
//						//tc.setCellEditor(new DefaultCellEditor((JComboBox)comp));
//						tc.setCellRenderer(new AddressesRenderer(v));
//					}
//					return comp;
//				}});
			pane.add(new JScrollPane(table), BorderLayout.CENTER);
		} catch (SocketException e) {
			e.printStackTrace(System.err);
		}

		return pane;
	}

	public class AddressesRenderer extends JComboBox implements TableCellRenderer {
	    public AddressesRenderer(Vector<String> items) {
	        super(items);
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int col) {
	        if (isSelected) {
	            setForeground(table.getSelectionForeground());
	            super.setBackground(table.getSelectionBackground());
	        } else {
	            setForeground(table.getForeground());
	            setBackground(table.getBackground());
	        }

	        // Select the current value
	        setSelectedItem(value);
	        return this;
	    }
	}

	private void do_update_cisco_list_display(JComboBox choices, JPanel body, String[] list, int index) {
		choices.setEnabled(false);
		choices.setSelectedIndex(index);
		CardLayout cl = (CardLayout)(body.getLayout());
		cl.show(body, list[index]);
		choices.setEnabled(true);
	}

	private void display_table(Properties p, JTable table, TopicInfo topic) {
		final Vector<String> columnNames = retrieve_leaf_col_names(p, topic);
		final Vector<Vector> rowData = retrieve_leaf_data(p, topic, columnNames.size());
		final int rows = rowData.size(), 
			cols = columnNames.size();
		AbstractTableModel model; 

		model = new AbstractTableModel() {
			public String getColumnName(int col) {
				return (String)columnNames.elementAt(col);
			}
			public int getRowCount() { return rows; }
			public int getColumnCount() { return cols; }
			public Object getValueAt(int row, int col) {
				return ((rowData.elementAt(row))).elementAt(col);
			}
			public boolean isCellEditable(int row, int col) {
				return false;
			}};

		table.setModel(model);
	}

	private Vector<String> retrieve_leaf_col_names(Properties p, TopicInfo topic) {
		Vector<String> names = new Vector<String>();
		String n;
		int i = 1;

		while (true) {
			n = p.getProperty("node."+topic.id+".title."+i);
			if (n == null) {
				break;
			}
			names.addElement(n);
			i++;
		}
		return names;
	}

	private Vector<Vector> retrieve_leaf_data(Properties p, TopicInfo topic, int cols) {
		Vector<Vector> data = new Vector<Vector>();
		String k;
		String v = null;
		int i = 1;

		while (true) {
			Vector<String> line = new Vector<String>(); 
			for (int j=1; j <= cols; j++) {
				k = "node." + topic.id + ".line." + i + ".col." + j;
				v = p.getProperty(k);
				if (v == null) {
					break;
				}
				line.addElement(v);
			}
			if (v == null) {
				break;
			}
			data.addElement(line);
			i++;
		}
		return data;
	}

	protected void setMacIPStatus(boolean status) {
		this.macIPComplete = status;
		copy.setEnabled(status);
	}

	protected boolean getMacIPStatus() {
		return this.macIPComplete;
	}

	

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(cardsMC.getLayout());
		cl.show(cardsMC, (String)evt.getItem());
	}

	protected class CyclingSpinnerListModel extends SpinnerListModel {
	    Object firstValue, lastValue;
	    SpinnerModel linkedModel = null;

	    public CyclingSpinnerListModel(Object[] values) {
	        super(values);
	        firstValue = values[0];
	        lastValue = values[values.length - 1];
	    }

	    public void setLinkedModel(SpinnerModel linkedModel) {
	        this.linkedModel = linkedModel;
	    }

	    public Object getNextValue() {
	        Object value = super.getNextValue();
	        if (value == null) {
	            value = firstValue;
	            if (linkedModel != null) {
	                linkedModel.setValue(linkedModel.getNextValue());
	            }
	        }
	        return value;
	    }

	    public Object getPreviousValue() {
	        Object value = super.getPreviousValue();
	        if (value == null) {
	            value = lastValue;
	            if (linkedModel != null) {
	                linkedModel.setValue(linkedModel.getPreviousValue());
	            }
	        }
	        return value;
	    }
	}

	protected class IPv4ActionAdapter implements DocumentListener {
		private JTextField mac;

		public IPv4ActionAdapter(JTextField mac) {
			this.mac = mac;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateMac(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateMac(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateMac(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	String ip = null;
        	String m = null;

			try {
				ip = d.getText(0, d.getLength());
				m = IPv4.getMacMC(ip);
				//m = net.sf.jnetparse.MAC.toVendorForm(m);
				mac.setText((m==null? "":m));
				System.out.println("ipv4=["+ip+"] - mac=["+m+"] - MC:"+IPv4.isMC(m));
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

	protected class IPv6ActionAdapter implements DocumentListener {
		private JTextField mac;

		public IPv6ActionAdapter(JTextField mac) {
			this.mac = mac;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateMac(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateMac(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateMac(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	String ip = null;
        	String m = null;

			try {
				ip = d.getText(0, d.getLength());
				System.out.print("NetMemo::IPv6.getOctets("+ip+")=[");

				m = IPv6.getMacMC(ip);
				//m = net.sf.jnetparse.MAC.toVendorForm(m);
				mac.setText((m==null? "":m));
				System.out.println("ipv6=["+ip+"] - mac=["+m+"]");
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

	protected class MACActionAdapter implements DocumentListener {
		private NetMemo memo;
		private JList ipList;

		public MACActionAdapter(NetMemo memo, JList list) {
			this.memo = memo;
			this.ipList = list;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateList(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateList(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateList(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	DefaultListModel<String> model;
        	String text;
        	String[] ips;
        	boolean status = false;

			try {
				memo.setMacIPStatus(false);
				text = d.getText(0, d.getLength());
				ips = IPv4.listMCIPv4(text);
				model = (DefaultListModel<String>) ipList.getModel();
				model.clear();
				if (ips == null) {
					return;
				}
				for (String ip: ips) {
					model.addElement(ip);
				}
				status = true;
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			memo.setMacIPStatus(status);
        }
	}

	protected class CopyAdapter implements ActionListener, ClipboardOwner {
		private NetMemo memo;
		private JList ipList;

		public CopyAdapter(NetMemo memo, JList ipList) {
			this.memo = memo;
			this.ipList = ipList;
		}

		public void actionPerformed(ActionEvent ae) {
			StringBuffer buf = new StringBuffer(); 
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection;
			ListModel model = ipList.getModel();
			int len = model.getSize();

			if ((memo.getMacIPStatus() == false) && (len > 0)) {
				return;
			}
			// otherwise, the MAC field is complete and 
			// the MAC address is valid (because len > 0)
			for (int i=0; i<len; i++) {
				buf.append(model.getElementAt(i).toString()).append(NetMemo.NEW_LINE);
			}
			stringSelection = new StringSelection(buf.toString());
		    clipboard.setContents(stringSelection, this);
		    trayIcon.displayMessage("IP Addresses copy", 
		    		"List of the matching IP addresses list copied to clipboard...", 
		    		TrayIcon.MessageType.INFO);
		    javax.swing.JOptionPane.showMessageDialog(memo, 
		    		(len+" entrie(s) have been copied to clipboard"));
		}

		/**
		 * Empty implementation of the ClipboardOwner interface.
		 */
		public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
			//do nothing
		}
	}

	protected class CopyTable implements ActionListener, ClipboardOwner {
		private NetMemo memo;
		private JTable table;

		public CopyTable(NetMemo memo, JTable table) {
			this.memo = memo;
			this.table = table;
		}

		public void actionPerformed(ActionEvent ae) {
			StringBuffer buf = new StringBuffer(); 
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection;
			TableModel model = table.getModel();
			int rows = model.getRowCount();
			int cols = model.getColumnCount();

			for (int i=0; i<cols; i++) {
				buf.append(model.getColumnName(i)).append('\t');
			}
			buf.append(NetMemo.NEW_LINE);
			for (int i=0; i<rows; i++) {
				for (int j=0; j < cols; j++) {
					buf.append(model.getValueAt(i, j)).append('\t');
				}
				buf.append(NetMemo.NEW_LINE);
			}
			stringSelection = new StringSelection(buf.toString());
		    clipboard.setContents(stringSelection, this);
		    trayIcon.displayMessage("Table copy", 
		    		"Table content copied to clipboard...", 
		    		TrayIcon.MessageType.INFO);
		    javax.swing.JOptionPane.showMessageDialog(memo, 
		    		("Table content copied to clipboard"));
		}

		/**
		 * Empty implementation of the ClipboardOwner interface.
		 */
		public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
			//do nothing
		}
	}


	protected class EUI64MACListener implements DocumentListener {
		private JTextField eui64;
		private JTextField linkLocal;
		private JTextField ipv6Prefix;
		private JTextField ipv6Address;
		
		public EUI64MACListener(JTextField eui64, JTextField linkLocal, JTextField ipv6Prefix, JTextField ipv6Address) {
			this.eui64 = eui64;
			this.linkLocal = linkLocal;
			this.ipv6Prefix = ipv6Prefix;
			this.ipv6Address = ipv6Address;
		}

		public void insertUpdate(DocumentEvent e) {
			updateMac(e);
		}

		public void removeUpdate(DocumentEvent e) {
			updateMac(e);
		}

		public void changedUpdate(DocumentEvent e) {
			//Plain text components don't fire these events.
		}

		private void updateMac(DocumentEvent e) {
			PlainDocument d = (PlainDocument)e.getDocument();
			String mac = null;
			String e64 = null;
			String pfx = null;
			String adr = null;

			try {
				mac = d.getText(0, d.getLength());
				e64 = IPv6.getEUI64(mac);
				pfx = ipv6Prefix.getText();
				//adr = net.sf.jnetparse.IPv6.getPrefix(adr, 64);
				eui64.setText(e64);
				linkLocal.setText(IPv6.getLinkLocal(mac));
				adr = IPv6.buildIPv6PrefixFromEUI64(pfx, e64);
				System.out.println("adr="+adr+" - eui64="+e64);
				ipv6Address.setText(adr);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	protected class IPv6AddressListener implements DocumentListener {
		private JTextField eui64;
		private JTextField ipv6Address;

		public IPv6AddressListener(JTextField eui64, JTextField ipv6Address) {
			this.eui64 = eui64;
			this.ipv6Address = ipv6Address;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateIPv6(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateIPv6(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateIPv6(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	String pfx = null;
        	String e64 = null;
        	String ipv6 = null;

			try {
				pfx = d.getText(0, d.getLength());
				e64 = eui64.getText();
				if (pfx.isEmpty() || e64.isEmpty()) {
					ipv6Address.setText("");
				} else {
					ipv6 = IPv6.buildIPv6PrefixFromEUI64(pfx, e64);
					ipv6Address.setText(ipv6);
					System.out.println("ipv6=["+ipv6+"] - eui64=["+e64+"]");
				}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

	protected class SolNodAdapter implements DocumentListener {
		private JTextField solnod;

		public SolNodAdapter(JTextField solnod) {
			this.solnod = solnod;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateSolNod(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateSolNod(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateSolNod(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	String ipv6 = null;
        	String sn = null;
        	String m = null;

			try {
				ipv6 = d.getText(0, d.getLength());
				sn = IPv6.getSolicitedNodeAddress(ipv6);
				solnod.setText((sn == null? "": sn));
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

	protected class TunnelingAdapater implements DocumentListener {
		private JTextField ipv6Prefix;
		private JTextField ipv4Address;
		private JTextField isatapAddress;
		private JTextField auto6to4;
		private JTextField ipv4Compatible;
		private JTextField ipv4Mapped;

		public TunnelingAdapater(JTextField ipv6Prefix, JTextField ipv4Address, JTextField isatapAddress, JTextField auto6to4, JTextField ipv4Compatible, JTextField ipv4Mapped) {
			this.ipv6Prefix = ipv6Prefix;
			this.ipv4Address = ipv4Address;
			this.isatapAddress = isatapAddress;
			this.auto6to4 = auto6to4;
			this.ipv4Compatible = ipv4Compatible;
			this.ipv4Mapped = ipv4Mapped;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateTunnels(e);
        }

        public void removeUpdate(DocumentEvent e) {
        	updateTunnels(e);
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        private void updateTunnels(DocumentEvent e) {
        	PlainDocument d = (PlainDocument)e.getDocument();
        	String ipv6 = ipv6Prefix.getText();;
        	String ipv4 = ipv4Address.getText();
        	String isatap = IPv6.buildISATAPAddress(ipv6, ipv4);
        	String a6to4 = IPv6.buildAuto6to4(ipv4);
        	String v4comp = IPv6.buildIPv4Compatible(ipv4);
        	String mapped = IPv6.buildIPv4Mapped(ipv4);

			isatapAddress.setText((isatap == null? "": isatap));
			auto6to4.setText((a6to4 == null? "": a6to4));
			ipv4Compatible.setText((v4comp == null? "": v4comp));
			ipv4Mapped.setText((mapped == null? "": mapped));
        }
	}

	protected class QoSConverionAdapter implements DocumentListener, ActionListener {
		private JTextField fromField;
		private JTextArea toField;
		private JRadioButton tos_f;
		private JRadioButton dscp_f;
		private JRadioButton ipp_f;
		private JRadioButton cos_f;
		private JRadioButton tos_t;
		private JRadioButton dscp_t;
		private JRadioButton ipp_t;
		private JRadioButton cos_t;

		public QoSConverionAdapter(JTextField fromField, JTextArea toField, JRadioButton tos_f, JRadioButton dscp_f, JRadioButton ipp_f, JRadioButton cos_f, JRadioButton tos_t, JRadioButton dscp_t, JRadioButton ipp_t, JRadioButton cos_t) {
			this.fromField = fromField;
			this.toField = toField;
			this.tos_f = tos_f;
			this.dscp_f = dscp_f;
			this.ipp_f = ipp_f;
			this.cos_f = cos_f;
			this.tos_t = tos_t;
			this.dscp_t = dscp_t;
			this.ipp_t = ipp_t;
			this.cos_t = cos_t;
		}

        public void insertUpdate(DocumentEvent e) {
        	updateQoS();
        }

        public void removeUpdate(DocumentEvent e) {
        	updateQoS();
        }

        public void changedUpdate(DocumentEvent e) {
        	//Plain text components don't fire these events.
        }

        public void actionPerformed(ActionEvent ae) {
        	updateQoS();
        }

        private void updateQoS() {
        	String fs = fromField.getText().trim().toLowerCase();
        	int from = -1, to = -1;
        	StringBuffer ts = new StringBuffer();
        	int v[] = null;

        	try {
       			if (fs.matches("0x[0-9a-f]{1,2}")) {
       				from = Integer.parseInt(fs.substring(2), 16);
       			} else if (fs.matches("[0-9]{1,3}")) {
       				from = Integer.parseInt(fs);
       			}
        		if (tos_f.isSelected()) {
        			if (tos_t.isSelected()) {
        				if (QoS.isToS(from)) {
        					to = from;
        				} else {
        					to = -1;
        				}
        			} else if (dscp_t.isSelected()) {
        				to = QoS.tos2dscp(from);
        			} else if (ipp_t.isSelected()) {
        				to = QoS.tos2ipp(from);
        			} else if (cos_t.isSelected()) {
        				to = QoS.tos2cos(from);
        			}
        		} else if (dscp_f.isSelected()) {
        			if (from == -1) {
        				from = QoS.getDSCPValue(fs);
        			}
        			if (tos_t.isSelected()) {
        				to = QoS.dscp2tos(from);
        			} else if (dscp_t.isSelected()) {
        				if (QoS.isDSCP(from)) {
        					to = from;
        				} else {
        					to = -1;
        				}
        			} else if (ipp_t.isSelected()) {
        				to = QoS.dscp2ipp(from);
        			} else if (cos_t.isSelected()) {
        				to = QoS.dscp2cos(from);
        			}
        		} else if (ipp_f.isSelected()) {
        			if (tos_t.isSelected()) {
        				to = QoS.ipp2tos(from);
        			} else if (dscp_t.isSelected()) {
        				to = QoS.ipp2dscp(from);
        			} else if (ipp_t.isSelected()) {
        				if (QoS.isIPP(from)) {
        					to = from;
        				} else {
        					to = -1;
        				}
        			} else if (cos_t.isSelected()) {
        				to = QoS.ipp2cos(from);
        			}
        		} else if (cos_f.isSelected()) {
        			if (tos_t.isSelected()) {
        				v = QoS.cos2tos(from);
        			} else if (dscp_t.isSelected()) {
        				v = QoS.cos2dscp(from);
        			} else if (ipp_t.isSelected()) {
        				to = QoS.cos2ipp(from);
        			} else if (cos_t.isSelected()) {
        				if (QoS.isCoS(from)) {
        					to = from;
        				} else {
        					to = -1;
        				}
        			}        			
        		}
        		if (to != -1) {
        			toField.setText(to + "");
        		} else if (v != null) {
        			for (int b: v) {
        				ts.append(b).append(NetMemo.NEW_LINE);
        			}
        			toField.setText(ts.toString());
        		} else {
        			toField.setText("");
        		}
        	} catch (NumberFormatException nfe) {
        		toField.setText("");
        	}
        }
	}


	// THIS WAY HAS BEEN ABANDONNED BY THE APPLICATION DEVELOPER
	protected class AddressInputVerifier extends InputVerifier {
		private Class ac;
		private JList<String> list;

		public AddressInputVerifier(Class cl) {
			this(cl, null);
		}
		public AddressInputVerifier(Class cl, JList<String> l) {
			this.ac = cl;
			this.list = l;
		}

		public boolean verify(JComponent input) {
			boolean check = false;
			DefaultListModel<String> model = new DefaultListModel<String>();

			if (input instanceof JFormattedTextField) {
				JFormattedTextField ftf = (JFormattedTextField)input;
				AbstractFormatter formatter = ftf.getFormatter();
				if (formatter != null) {
					String text = ftf.getText();
					String nt = MAC.normalize(text);
					if (MAC.class.equals(ac)) {
						System.out.println("NORMALIZE("+text+")=["+nt+"]");
						check = (nt != null);
						if (check) {
							for (String ip: IPv4.listMCIPv4(text)) {
								System.out.println("ip:"+ip);
								model.addElement(ip);
							}
							list.setModel(model);
						}
					} else if (IPv4.class.equals(ac)) {
						check = (nt != null);
						if (check) {
							
						}
					} else if (IPv6.class.equals(ac)) {
						check = (nt != null);
					}
				}
			} else {
				check = true;
			}
			return check;
		}
	}

	protected class EUI64ItemAdapter implements ItemListener {
		private JPanel cards;

		public EUI64ItemAdapter(JPanel pane) {
			this.cards = pane;
		}

	    public void itemStateChanged(ItemEvent ie) {
	    	CardLayout cl = (CardLayout)(cards.getLayout());
	    	cl.next(cards);
	        //cl.show(cards, (String)ie.getItem());
	    }
	}
}
