package net.sf.netmemo.vendors.cisco;

import java.util.HashMap;
import java.util.Properties;

public class ConfigRegister {
	public static final int BOOT_FIELD_ROM = 0;
	public static final int BOOT_FIELD_FLASH = 1;
	public static final int BOOT_FIELD_SYSTEM = 0xF;
	public static final int BOOT_FIELD_MASK = 0xF;

	public static final int DEFAULT_REG_VALUE = 0x2102;

	private static HashMap<Integer, Integer> consoleSpeed = new HashMap<Integer, Integer>();

	private static Properties description = new Properties();

	static {
//		bit #15 - bit#0
//		xxx1 0xxx xx0x xxxx	1200
//		xxx1 1xxx xx0x xxxx	2400
//		xxx0 1xxx xx0x xxxx	4800
//		xxx0 0xxx xx0x xxxx	9600	[default]
//		xxx0 0xxx xx1x xxxx	19200
//		xxx0 1xxx xx1x xxxx	38400
//		xxx1 0xxx xx1x xxxx	57600
//		xxx1 1xxx xx1x xxxx	115200
		consoleSpeed.put(new Integer(0x1000), new Integer(1200));
		consoleSpeed.put(new Integer(0x1800), new Integer(2400));
		consoleSpeed.put(new Integer(0x0800), new Integer(4800));
		consoleSpeed.put(new Integer(0x0000), new Integer(9600));
		consoleSpeed.put(new Integer(0x0020), new Integer(19200));
		consoleSpeed.put(new Integer(0x0820), new Integer(38400));
		consoleSpeed.put(new Integer(0x1020), new Integer(57600));
		consoleSpeed.put(new Integer(0x1820), new Integer(115200));

		description.put("0.off", "Stays at the ROM monitor on a reload or power cycle.");
		description.put("0.on", "Boots the first image in flash memory as a system image.");
		description.put("1.off", "");
		description.put("1.on", "Enables default booting from flash memory.{0}" +
			"Enables boot system commands that override default booting from flash memory.");
		description.put("2.off", "");
		description.put("2.on", "Enables default booting from flash memory.{0}" +
			"Enables boot system commands that override default booting from flash memory.");
		description.put("3.off", "");
		description.put("3.on", "Enables default booting from flash memory.{0}" +
			"Enables boot system commands that override default booting from flash memory.");

		description.put("4.off", "");
		description.put("4.on", "");

		description.put("5.off", "Console Speed <= 9600 bps.");
		description.put("5.on", "Console Speed > 9600 bps.{0}"+
			"The system does not allow you to change the console speed bits directly with the config-register command.{0}"+ 
			"To change the console speed, complete this sequence:{0}"+
			"Router# configure terminal{0}"+
			"Router(config)# line console 0{0}"+
			"Router(config-line)# speed 9600{0}");

		description.put("6.off", "");
		description.put("6.on", "Causes the system software to ignore the contents of NVRAM.");

		description.put("7.off", "");
		description.put("7.on", "OEM bit enabled.");

		description.put("8.off", "Clearing bit 8 causes the processor to interpret Break as a command to force the router into the bootstrap monitor, halting normal operation. Break can always be sent in the first 60 seconds while the router is rebooting, regardless of the configuration settings.");
		description.put("8.on", "Setting bit 8 (the factory default) causes the processor to ignore the console Break key."); 
 
		description.put("9.off", "Bit 9 controls the system boot.{0}"+
			"Clearing bit 9 (the factory default) causes the system to boot from flash memory.");
		description.put("9.on", "Causes the system to use the secondary bootstrap.{0}"+
			"This is typically not used (set to 0)."); 

		description.put("10.off", 
			"Bit 10 controls the host portion of the IP broadcast address.{0}"+
			"Clearing bit 10 (the factory default) causes the processor to use all ones.{0}"+
			"Bit 10 interacts with bit 14, which controls the network and subnet portions of the broadcast address.{0}");
//			"+--------+--------+-----------------------+{0}" +
//			"| Bit 10 | Bit 14 | Address (<net> <host>)|{0}" +
//			"+--------+--------+-----------------------|{0}" +
//			"|   0    |    0   |   <ones>  <ones>      |{0}" +
//			"|   1    |    0   |   <zeros> <zeros>     |{0}" +
//			"|   1    |    1   |   <net> <zeros>       |{0}" +
//			"|   0    |    1   |   <net> <ones>        |{0}" +
//			"+--------+--------+-----------------------+{0}");
		description.put("10.on", "IP broadcast with all zeros.{0}{0}" +
			"Bit 10 controls the host portion of the IP broadcast address.{0}" +
			"Setting bit 10 causes the processor to use all zeros.{0}" +
			"Bit 10 interacts with bit 14, which controls the network and subnet portions of the broadcast address.{0}");
//			"+--------+--------+-----------------------+{0}" +
//			"| Bit 10 | Bit 14 | Address (<net> <host>)|{0}" +
//			"+--------+--------+-----------------------|{0}" +
//			"|   0    |    0   |   <ones>  <ones>      |{0}" +
//			"|   1    |    0   |   <zeros> <zeros>     |{0}" +
//			"|   1    |    1   |   <net> <zeros>       |{0}" +
//			"|   0    |    1   |   <net> <ones>        |{0}" +
//			"+--------+--------+-----------------------+{0}");

		description.put("11.off", "");
		description.put("11.on", "Console line speed.{0}"+
			"The system does not allow you to change the console speed bits directly with the config-register command.{0}"+ 
			"To change the console speed, complete this sequence:{0}"+
			"Router# configure terminal{0}"+
			"Router(config)# line console 0{0}"+
			"Router(config-line)# speed 9600{0}");

		description.put("12.off", "");
		description.put("12.on", "Console line speed."+
			"The system does not allow you to change the console speed bits directly with the config-register command.{0}"+ 
			"To change the console speed, complete this sequence:{0}"+
			"Router# configure terminal{0}"+
			"Router(config)# line console 0{0}"+
			"Router(config-line)# speed 9600{0}");

		description.put("13.off", "");
		description.put("13.on", "Boots default ROM software if the network boot fails.");

		description.put("14.off",
			"Controls the network and subnet portions of the broadcast address.{0}"+
			"Bit 14 interacts with bit 10.{0}");
//			"+--------+--------+-----------------------+{0}" +
//			"| Bit 10 | Bit 14 | Address (<net> <host>)|{0}" +
//			"+--------+--------+-----------------------|{0}" +
//			"|   0    |    0   |   <ones>  <ones>      |{0}" +
//			"|   1    |    0   |   <zeros> <zeros>     |{0}" +
//			"|   1    |    1   |   <net> <zeros>       |{0}" +
//			"|   0    |    1   |   <net> <ones>        |{0}" +
//			"+--------+--------+-----------------------+{0}");
		description.put("14.on", "IP broadcasts do not have net numbers.{0}" +
			"Controls the network and subnet portions of the broadcast address.{0}"+
			"Bit 14 interacts with bit 10.{0}{0}");
//			"+--------+--------+-----------------------+{0}" +
//			"| Bit 10 | Bit 14 | Address (<net> <host>)|{0}" +
//			"+--------+--------+-----------------------|{0}" +
//			"|   0    |    0   |   <ones>  <ones>      |{0}" +
//			"|   1    |    0   |   <zeros> <zeros>     |{0}" +
//			"|   1    |    1   |   <net> <zeros>       |{0}" +
//			"|   0    |    1   |   <net> <ones>        |{0}" +
//			"+--------+--------+-----------------------+{0}");

		description.put("15.off", "");
		description.put("15.on", "Enables diagnostic messages and ignores the contents of NVRAM.");
	};

	private int reg;

	public static int[] getConsoleSpeeds() {
		return new int[] {
				1200,
				2400,
				4800,
				9600,
				19200,
				38400,
				57600,
				115200
			};
	}

	public static Integer[] getConsoleSpeedsInteger() {
		return new Integer[] {
				new Integer(1200),
				new Integer(2400),
				new Integer(4800),
				new Integer(9600),
				new Integer(19200),
				new Integer(38400),
				new Integer(57600),
				new Integer(115200)
			};
	}

	public static int getConsoleSpeed(int fifth, int eleventh, int twelveth) {
		int mask = 
			(fifth & 0x20) |
			(eleventh & 0x800) |
			(twelveth & 0x1000);
		Integer v = (Integer)consoleSpeed.get(new Integer(mask));

		return (v == null? -1: v.intValue());
	}

	public static int getConsoleSpeed(int cr) {
		int mask = cr & 0x1820;
		Integer v = (Integer)consoleSpeed.get(new Integer(mask));

		return (v == null? -1: v.intValue());
	}

	public ConfigRegister() {
		this(DEFAULT_REG_VALUE);
	}

	public ConfigRegister(int reg) {
		if ((reg < 0x0) || (reg > 0xFFFF)) {
			throw new IllegalArgumentException("configuration register out bounds:"+reg);
		}
		this.reg = reg;
	}

	public boolean reset() {
		boolean changed = ((reg ^ DEFAULT_REG_VALUE) != 0);

		reg = DEFAULT_REG_VALUE;
		return changed;
	}

	public boolean setBit(int bit) {
		boolean changed = false;
		int mask;

		if ((bit < 0) || (bit > 15)) {
			return false;
		}
		mask = 1 << bit;
		changed = ((reg & mask) == 0);
		reg |= mask;
		return changed;
	}

	public boolean setReg(int r) {
		if ((r < 0) || (r > 0xFFFF)) {
			return false;
		}
		reg = r;
		return true;
	}

	public int getReg() {
		return reg;
	}

	public boolean unsetBit(int bit) {
		boolean changed = false;
		int mask;

		if ((bit < 0) || (bit > 15)) {
			return false;
		}
		mask = (1 << bit);
		changed = ((reg & mask) != 0);
		mask = ~mask & 0xFFFF;
		reg = reg & mask;
		return changed;
	}

	public boolean setSpeed(int sp) {
		boolean changed;

		switch (sp) {
			case 1200:
				//reg |= 0x1000;
				changed = setBit(12);
				changed = unsetBit(5) || changed;
				changed = unsetBit(11) || changed;
				break;
			case 2400:
				//reg |= 0x1800;
				changed = setBit(12);
				changed = setBit(11) || changed;
				changed = unsetBit(5) || changed;
				break;
			case 4800:
				//reg |= 0x0800;
				changed = setBit(11);
				changed = unsetBit(5) || changed;
				changed = unsetBit(12) || changed;
				break;
			case 9600:
				changed = unsetBit(5);
				changed = unsetBit(11) || changed;
				changed = unsetBit(12) || changed;
				break;
			case 19200:
				//reg |= 0x0020;
				changed = setBit(5);
				changed = unsetBit(11) || changed;
				changed = unsetBit(12) || changed;
				break;
			case 38400:
				//reg |= 0x0820;
				changed = setBit(11);
				changed = setBit(5) || changed;
				changed = unsetBit(12) || changed;
				break;
			case 57600:
				//reg |= 0x1020;
				changed = setBit(12);
				changed = setBit(5) || changed;
				changed = unsetBit(11) || changed;
				break;
			case 115200:
				//reg |= 0x1820;
				changed = setBit(12);
				changed = setBit(11) || changed; 
				changed = setBit(5)  || changed;
				break;
			default:
				changed = false;
		}
		return changed;
	}

	public boolean isBitSet(int bit) {
		if ((bit < 0) || (bit > 15)) {
			return false;
		}
		return ((reg & ((1 << bit) & 0xFFFF)) != 0);
	}

	public int getDefaultConsoleSpeed() {
		return 9600;
	}

	public String toString() {
		String t = Integer.toHexString(reg);
		StringBuffer s = new StringBuffer(t);

		for (int i=t.length(); i<4; i++) {
			s.insert(0, '0');
		}
		return s.insert(0, "0x").toString();
	}

	public String[] getDescription() {
		String[] desc = new String[16];

		for (int i=0; i<desc.length; i++) {
			if (isBitSet(i)) {
				desc[i] = description.getProperty(i+".on");
			} else {
				desc[i] = description.getProperty(i+".off");
			}
		}
		return desc;
	}
}
