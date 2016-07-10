package net.sf.netmemo.gui;//.vendors.cisco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.netmemo.vendors.cisco.ConfigRegister;

@SuppressWarnings("serial")
public class ConfigRegisterPanel extends JPanel {
	private ConfigRegister reg;
	private JPanel pane;

	private NetMemo netmemo;

	public ConfigRegisterPanel(NetMemo netmemo) {
		this(netmemo, null);
	}

	public ConfigRegisterPanel(NetMemo netmemo, String title) {
		super(new FlowLayout());
		//super(new GridBagLayout());
		this.netmemo = netmemo;
		reg = new ConfigRegister();
		buildComponenets(title);
	}

	private void updateNibbles(JLabel[] nibbles) {
		String cr = reg.toString().substring(2);

		for (int i=0; i<4; i++) {
			nibbles[i].setText(cr.charAt(i)+"");
		}
	}

	private void updateBits(JRadioButton[] bits, JLabel[] nibbles) {
		int idx;
		boolean focus;
		String cr;

		for (int i=0; i < bits.length; i++) {
			idx = bits.length-i-1;
			focus = bits[idx].hasFocus();
			bits[idx].setEnabled(false);
			bits[idx].setSelected(reg.isBitSet(i));
			bits[idx].setEnabled(true);
			if (focus) {
				bits[idx].requestFocus();
			}
		}
		updateNibbles(nibbles);
	}

	private void buildComponenets(String title) {
		final JTextField crf = new JTextField(6);
		JButton computeButton = new JButton("Compute", netmemo.
				createImageIcon(netmemo.getStringResource(NetMemo.NETMEMO_BUTTON_COMPUTE), true));
		String t = (title == null? "Configuration Register": title.trim());
		final JComboBox choices = new JComboBox(ConfigRegister.getConsoleSpeedsInteger());
		GridBagConstraints c = new GridBagConstraints();
		// ctrl panel
		JPanel ctrlp  = new JPanel(new GridBagLayout());
		// speed panel
		JPanel sp = new JPanel(new BorderLayout());
		// config-register panel
		JPanel crp    = new JPanel();
		// description panel
		JPanel dp     = new JPanel(new BorderLayout());
		final JRadioButton[] bits = new JRadioButton[16];
		final JLabel[] nibbles = new JLabel[4];
		final JTextArea desc = new JTextArea(20, 20);
		Font font = crf.getFont();

		desc.setBackground(Color.BLACK);
		desc.setForeground(Color.ORANGE);
		pane = new JPanel(new GridBagLayout());
		//desc.setEnabled(true);
		desc.setEditable(false);
		updateDescription(desc);
		choices.setSelectedItem(new Integer(reg.getDefaultConsoleSpeed()));
		crf.setText(reg.toString());
		crf.setFont(font.deriveFont(Font.BOLD|Font.ITALIC, font.getSize2D()*5));
		computeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				int r = -1;
				boolean changed = false;
				String v = crf.getText().trim().toLowerCase(); 

				crf.setForeground(Color.BLACK);
				try {
					if (v.startsWith("0x")) {
						r = Integer.parseInt(v.substring(2), 16);
					} else {
						r = Integer.parseInt(v);
					}
				} catch (NumberFormatException nfe) {}
				changed = reg.setReg(r);
				if (changed) {
					crf.setText(reg.toString());
					updateDescription(desc);
					choices.setSelectedItem(new Integer(ConfigRegister.getConsoleSpeed(reg.getReg())));
					updateBits(bits, nibbles);
				} else {
					crf.setForeground(Color.RED);
				}
			}});
		crp.setLayout(new BoxLayout(crp, BoxLayout.PAGE_AXIS));
		crp.add(crf);
		crp.add(computeButton);
		computeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		desc.setEditable(false);
		dp.add(new JScrollPane(desc), BorderLayout.CENTER);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = -1;
		for (int i=0; i < bits.length; i++) {
			if (i%4 == 0) {
				c.gridx = 0;
				c.gridy++;
				nibbles[i/4] = new JLabel(reg.toString().charAt(2+i/4)+"");
				nibbles[i/4].setFont(font.deriveFont(Font.BOLD, font.getSize2D()*5));
				ctrlp.add(nibbles[i/4], c);
				c.gridx++;
				ctrlp.add(Box.createHorizontalStrut(font.getSize()), c);
				c.gridx++;
			}
			bits[i] = new JRadioButton("Bit #"+(bits.length-i-1));
			bits[i].setActionCommand((bits.length-i-1)+"");
			bits[i].setSelected(reg.isBitSet(bits.length-i-1));
			bits[i].addItemListener(new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent ie) {
					JRadioButton rb = (JRadioButton)ie.getItem();
					Integer v = Integer.parseInt(rb.getActionCommand());
					int n = v.intValue();
					int speed;

					if (rb.isEnabled() == false) {
						return;
					}
					if (rb.isSelected()) {
						reg.setBit(n);
					} else {
						reg.unsetBit(n);
					}
					if ((n == 5) || (n == 11) || (n == 12)) {
						rb.setEnabled(false);
						speed = ConfigRegister.getConsoleSpeed(reg.getReg());
						choices.setEnabled(false);
						choices.setSelectedItem(new Integer(speed));
						choices.setEnabled(true);
						rb.setEnabled(true);
					}
					updateNibbles(nibbles);
					crf.setText(reg.toString());
					updateDescription(desc);
					rb.requestFocus();
				}});
			ctrlp.add(bits[i], c);
			c.gridx++;
		}
		choices.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent ie) {
				Integer v = (Integer)ie.getItem();

				if (choices.isEnabled() == false) {
					return;
				}
				reg.setSpeed(v.intValue());
				updateNibbles(nibbles);
				crf.setText(reg.toString());
				updateDescription(desc);
				updateBits(bits, nibbles);
				System.out.println("reg-value="+reg);
			}});

		setBorder(new TitledBorder(t));
		ctrlp.setBorder(new TitledBorder("Control Panel"));
		sp.setBorder(new TitledBorder("Console (bps)"));
		crp.setBorder(new TitledBorder("Config-Register"));
		dp.setBorder(new TitledBorder("Description"));

		sp.add(choices, BorderLayout.CENTER);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;

		crf.setMinimumSize(crf.getPreferredSize());
		crp.setMinimumSize(crp.getPreferredSize());
		pane.add(crp, c);
		c.gridy = 1;
		pane.add(sp, c);
		c.gridy = 0;
		c.gridheight = 3;
		c.gridx = 1;
		pane.add(ctrlp, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		dp.setMinimumSize(dp.getPreferredSize());
		//add(dp, c);
		add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane, dp));
	}

	private void updateDescription(JTextArea desc) {
		String[] d = reg.getDescription();
		MessageFormat form;
		StringBuffer buf = new StringBuffer();
		String line;
		final String nl = String.format("%n");

		for (int i=0; i<d.length; i++) {
			form = new MessageFormat(d[i]);
			line = form.format(new String[] {String.format("%n")});
			if (line.trim().equals("") == false) {
				buf.append("* ");
			}
			buf.append(line).append(nl);
		}
		desc.setText(buf.toString());
		desc.setCaretPosition(0);
	}
}
