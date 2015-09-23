package deviouscraker.roughriders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import fourotherguys.lib.util.encryption.Encrypt;

public class Save {

	private File f;
	private Scanner scanner;
	private PrintWriter out;

	private Encrypt e = new Encrypt();

	public static final String SAVE_AMMO = "ammo";
	public static final String SAVE_HEALTH = "health";
	public static final String SAVE_RELOAD = "reload";
	public static final String SAVE_POWER = "power";
	public static final String SAVE_LEVEL = "level";
	public static final String SAVE_POINTS = "points";

	public static final String[] names = { SAVE_AMMO, SAVE_HEALTH, SAVE_RELOAD, SAVE_POWER, SAVE_LEVEL, SAVE_POINTS };

	public HashMap<String, String> values = new HashMap<String, String>();

	private static final String ERROR = "Save File Corrupted! If you believe this to be an error, please send save file to developer. Or simply delete it and restart.";

	public Save() {
		f = new File("Save.txt");

		try {
			if (f.createNewFile()) {
				Main.hasSave = false;
			} else {
				Main.hasSave = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			out = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		out.println(SAVE_AMMO + '=' + Main.w.player.getAmmoUpgradesPurchased());
		out.println(SAVE_RELOAD + '=' + Main.w.player.getReloadUpgradesPurchased());
		out.println(SAVE_POWER + '=' + Main.w.player.getPowerUpgradesPurchased());
		out.println(SAVE_POINTS + '=' + Main.w.player.getPoints());
		out.println(SAVE_LEVEL + '=' + Main.w.player.getCurrentFort());
		out.println(SAVE_HEALTH + '=' + Main.w.player.getHealthUpgradesPurchased());
		out.flush();
		out.close();
		e.encryptFile(f);
	}

	public void load() {
		e.decryptFile(f);

		resetScanner();

		for (int i = 0; i < names.length; i++) {
			if (!putValue(names[i])) {
				e.encryptFile(f);
				JOptionPane.showMessageDialog(Main.w, ERROR, "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}

		scanner.close();
		e.encryptFile(f);
	}

	public boolean putValue(String s) {
		while (scanner.hasNextLine()) {
			String scannerLine = scanner.nextLine();
			if (scannerLine.startsWith(s)) {
				values.put(s, getValue(scannerLine));
				resetScanner();
				return true;
			}
		}

		return false;
	}

	public void resetScanner() {
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getValue(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '=') {
				return s.substring(i + 1);
			}
		}

		return null;
	}

}
