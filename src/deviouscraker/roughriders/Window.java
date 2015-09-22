package deviouscraker.roughriders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import deviouscraker.api.util.images.SpriteSheet;
import deviouscraker.api.util.input.KeyInput;
import deviouscraker.roughriders.entity.Player;
import deviouscraker.roughriders.lib.Resource;
import deviouscraker.roughriders.lib.SpaniardTypes;

/**
 * @author Zachery Jun 25, 2015
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	/*
	 * Is whether or not the game is paused, if true then all game functions
	 * cease to halt the spaniard ai and any entity rendering
	 */
	public boolean isPaused = true;

	/*
	 * Size of the in-game screen, where everything is rendered.
	 */
	public static final Dimension gameDimension = new Dimension(1080, 720);

	/*
	 * Size of the main menu, contains all buttons.
	 */
	public static final Dimension mmDimension = new Dimension(100, 110);

	/*
	 * Panel used when the game is being played, contains all of the entities
	 */
	private GamePanel gamePanel = new GamePanel();

	/*
	 * Panel used when the game is either paused or at startup
	 */
	private MainMenuPanel mmPanel = new MainMenuPanel();

	/*
	 * The spritesheet containing all of the characters, both spaniards and the
	 * player
	 */
	public SpriteSheet charSheet = new SpriteSheet(18, 32, 6, 3, "CharSprites2.png");

	/*
	 * The player object
	 */
	public Player player = new Player(0, 100, 20, charSheet.getSpriteSheet().get("row2").get(0), charSheet.getSpriteSheet().get("row2").get(1), charSheet.getSpriteSheet().get("row2").get(2), charSheet.getSpriteSheet().get("row2").get(3));

	public SpaniardTypes st = new SpaniardTypes(charSheet);

	public Background b = new Background();

	private GameKeyListener gkl = new GameKeyListener();

	private static boolean firstTime = false;

	/**
	 * Sets up the basics of the window.
	 */
	public Window() {
		super(Resource.Game.NAME + " : " + Resource.Game.VERSION + " - " + Resource.Game.STATE);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		add(mmPanel);
		pack();

		this.addKeyListener(gkl);
	}

	public void run() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gkl.run();
		player.checkRifleStatus();
		repaint();
		pack();
	}

	/**
	 * @author Zachery Jun 25, 2015
	 */
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public GamePanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));
		}

		@Override
		public Dimension getPreferredSize() {
			return gameDimension;
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.drawImage(b.getBackground(), b.getAffineTransform(), null);
			g2d.drawImage(player.getCurrentStance(), player.getAffineTransform(), null);

			g2d.drawRect(1, 0, 200, 100);
			g2d.drawString("Health: " + player.getHealthPoints(), 5, 30);

			String wep = "Pistol";
			if (player.isHasRifle()) {
				wep = "Rifle";
			}

			g2d.drawString("Current Weapon: " + wep, 5, 45);

			if (player.isReloading()) {
				g2d.drawString("Reloading: " + player.getReloadingPercent() + "%", 5, 60);
			} else {
				g2d.drawString("Reloading: " + "Not Reloading", 5, 60);
			}

			g2d.drawString("Remaining Ammo: " + player.getCurrentAmmo() + "/" + player.getMaxAmmo(), 5, 15);
			g2d.drawString("Points: " + player.getPoints(), 5, 75);

			for (int i = 0; i < Main.sh.getSize(); i++) {
				g2d.drawImage(Main.sh.getSpaniard(i).getCurrentStance(), Main.sh.getSpaniard(i).getAffineTransform(), null);
				g2d.drawString(Main.sh.getSpaniard(i).getTrueName() + " : " + Main.sh.getSpaniard(i).getHealthPoints(), Main.sh.getSpaniard(i).getX(), Main.sh.getSpaniard(i).getY());
			}

			for (int i = 0; i < Main.bh.getBulletList().size(); i++) {
				g2d.drawImage(Main.bh.getBullet(i).getImage(), Main.bh.getBullet(i).getAffineTransform(), null);
			}
		}
	}

	/*
	 * Creates the Main Menu, including all buttons and triggers for
	 * loading/saving games. Includes it's own MouseListener handler
	 */
	private class MainMenuPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JLabel enterGame = new JLabel();
		private JLabel quitGame = new JLabel();
		private JLabel companyInfo = new JLabel();

		private HashMap<String, String> defaultNames = new HashMap<String, String>();
		private mMouseListener mListener = new mMouseListener();

		public MainMenuPanel() {
			defaultNames.put("new", "New Game");
			defaultNames.put("enter", "Enter Game");
			defaultNames.put("save", "Save Game");
			defaultNames.put("load", "Load Game");
			defaultNames.put("quit", "Quit Game");
			defaultNames.put("info", "T4OG � 2015");

			setBorder(BorderFactory.createLineBorder(Color.black));

			enterGame.setText(defaultNames.get("enter"));
			enterGame.addMouseListener(mListener);

			quitGame.setText(defaultNames.get("quit"));
			quitGame.addMouseListener(mListener);

			companyInfo.setText(defaultNames.get("info"));

			add(enterGame);
			add(quitGame);
			add(companyInfo);

		}

		@Override
		public Dimension getPreferredSize() {
			return mmDimension;
		}

		private class mMouseListener implements MouseListener {

			public void enterGame() {
				Main.w.remove(mmPanel);
				Main.w.add(gamePanel);
				pack();
				isPaused = false;
			}

			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getSource() == quitGame) {
					System.exit(0);
				} else if (event.getSource() == enterGame) {
					enterGame();
					mouseExited(event);
				}
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				if (event.getSource() == quitGame) {
					quitGame.setText(" > " + defaultNames.get("quit") + " < ");
				} else if (event.getSource() == enterGame) {
					enterGame.setText(" > " + defaultNames.get("enter") + " < ");
				}
			}

			@Override
			public void mouseExited(MouseEvent event) {
				if (event.getSource() == quitGame) {
					quitGame.setText(defaultNames.get("quit"));
				} else if (event.getSource() == enterGame) {
					enterGame.setText(defaultNames.get("enter"));
				}
			}

			@Override
			public void mousePressed(MouseEvent event) {}

			@Override
			public void mouseReleased(MouseEvent event) {}

		}
	}

	private class GameKeyListener extends KeyInput {

		public void run() {
			if (isKeyPressed(KeyInput.KEY_W) || isKeyPressed(KeyInput.KEY_ARROW_UP))
				player.moveUp();
			if (isKeyPressed(KeyInput.KEY_S) || isKeyPressed(KeyInput.KEY_ARROW_DOWN))
				player.moveDown();
			if (isKeyPressed(KeyInput.KEY_A) || isKeyPressed(KeyInput.KEY_ARROW_LEFT))
				player.moveLeft();
			if (isKeyPressed(KeyInput.KEY_D) || isKeyPressed(KeyInput.KEY_ARROW_RIGHT))
				player.moveRight();
			if (removeKeyIfExists(KeyInput.KEY_SPACE))
				player.triggerFire();
			if (removeKeyIfExists(KeyInput.KEY_C))
				player.triggerDuck();
			if (removeKeyIfExists(KeyInput.KEY_R))
				player.setHasRifle(true);
			if (removeKeyIfExists(KeyInput.KEY_P))
				player.setHasRifle(false);
			if (removeKeyIfExists(KeyInput.KEY_ESCAPE)) {
				Main.w.remove(gamePanel);
				Main.w.add(mmPanel);
				isPaused = true;
			}
			if (removeKeyIfExists(KeyInput.KEY_Q)) {
				player.triggerReload();
			}

			// testing keys
			if (removeKeyIfExists(KeyInput.KEY_V)) {
				Random rng = new Random();
				float x = rng.nextInt((int) Main.w.getSize().getWidth()) * 1f;
				float y = rng.nextInt((int) Main.w.getSize().getHeight()) * 1f;
				String type = "";
				int t = rng.nextInt(5);
				if (t == 0) {
					type = Resource.Spaniard.PRIVATE;
				} else if (t == 1) {
					type = Resource.Spaniard.CORPORAL;
				} else if (t == 2) {
					type = Resource.Spaniard.SERGEANT;
				} else if (t == 3) {
					type = Resource.Spaniard.LIEUTENANT;
				} else if (t == 4) {
					type = Resource.Spaniard.CAPTAIN;
				} else if (t == 5) {
					type = Resource.Spaniard.COMMANDER;
				}

				Main.sr.getNewSpaniard(x, y, type);
			}

			if (removeKeyIfExists(KeyInput.KEY_B))
				new Shop();
		}

	}

	private class Shop extends JFrame {
		private static final long serialVersionUID = 1L;

		private JLabel points;

		private JButton upgradeAmmo;
		private JButton upgradePower;
		private JButton upgradeReload;
		private JButton upgradeHealth;

		private Dimension dimension = new Dimension(275, 400);

		public Shop() {
			super("Swampy's Lagoon");

			setVisible(true);
			setLayout(new FlowLayout());
			setSize(dimension);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			points = new JLabel("Points: " + player.getPoints());

			add(points);

			upgradeAmmo = new UpgradeButtons(UpgradeButtons.TYPE_AMMO).name("Ammo").setCost();
			add(upgradeAmmo);

			upgradePower = new UpgradeButtons(UpgradeButtons.TYPE_POWER).name("Power").setCost();
			add(upgradePower);

			upgradeReload = new UpgradeButtons(UpgradeButtons.TYPE_RELOAD).name("Reload").setCost();
			add(upgradeReload);

			upgradeHealth = new UpgradeButtons(UpgradeButtons.TYPE_HEALTH).name("Health").setCost();
			add(upgradeHealth);

			if (player.getReloadUpgradesPurchased() == 10) {
				upgradeReload.setEnabled(false);
			}

			if (!firstTime) {
				firstTime = true;
				this.dispose();
				new Shop();
			}
		}

		public void remove() {
			this.dispose();
		}

		private class UpgradeButtons extends JButton {
			private static final long serialVersionUID = 1L;

			private static final int TYPE_AMMO = 0;
			private static final int TYPE_POWER = 1;
			private static final int TYPE_RELOAD = 2;
			private static final int TYPE_HEALTH = 3;

			/*
			 * What all upgrades start at.
			 */
			private static final int BASECOST = 5;

			/*
			 * How much the upgrade will actually cost.
			 */
			private int actualCost;

			/*
			 * Which particular type of upgrade it is, denoted by the constants
			 */
			private int type;

			public UpgradeButtons(int type) {
				addMouseListener(new UpgradeButtonMouseListener());
				this.type = type;
			}

			public UpgradeButtons name(String name) {
				if (this.type == TYPE_AMMO) {
					setText(name + " : Times Purchased : " + player.getAmmoUpgradesPurchased());
				} else if (this.type == TYPE_POWER) {
					setText(name + " : Times Purchased : " + player.getPowerUpgradesPurchased());
				} else if (this.type == TYPE_RELOAD) {
					setText(name + " : Times Purchased : " + player.getReloadUpgradesPurchased());
				} else if (this.type == TYPE_HEALTH) {
					setText(name + " : Times Purchased : " + player.getHealthUpgradesPurchased());
				}

				return this;
			}

			public UpgradeButtons setCost() {
				if (this.type == TYPE_AMMO) {
					this.actualCost = BASECOST * (int) Math.pow(2, player.getAmmoUpgradesPurchased());
				} else if (this.type == TYPE_POWER) {
					this.actualCost = BASECOST * (int) Math.pow(2, player.getPowerUpgradesPurchased());
				} else if (this.type == TYPE_RELOAD) {
					this.actualCost = BASECOST * (int) Math.pow(2, player.getReloadUpgradesPurchased());
				} else if (this.type == TYPE_HEALTH) {
					this.actualCost = BASECOST * (int) Math.pow(2, player.getHealthUpgradesPurchased());
				}

				return this;
			}

			/*
			 * Used in the MouseListener class
			 */
			public int getCost() {
				return this.actualCost;
			}

			private class UpgradeButtonMouseListener implements MouseListener {

				@Override
				public void mouseClicked(MouseEvent event) {
					UpgradeButtons ub = (UpgradeButtons) event.getSource();

					/*
					 * if the button isn't enabled (e.g, reload) stops the
					 * method.
					 */
					if (!ub.isEnabled()) {
						return;
					}

					int response = JOptionPane.showConfirmDialog(ub, "Are you sure you would like to purchase that for " + ub.getCost() + " points?", "", JOptionPane.YES_NO_OPTION);

					if (response == 0) { // yes
						if (ub.getCost() <= player.getPoints()) {
							player.removePoints(ub.getCost());

							if (event.getSource() == upgradeAmmo) {
								player.addAmmoUpgradesPurchased();
							} else if (event.getSource() == upgradePower) {
								player.addPowerUpgradesPurchased();
							} else if (event.getSource() == upgradeReload) {
								player.addReloadUpgradesPurchased();
							} else if (event.getSource() == upgradeHealth) {
								player.addHealthUpgradesPurchased();
							}

							player.checkUpgrades();
							Shop.this.remove();
							new Shop();
						} else {
							JOptionPane.showMessageDialog(ub, "You do not have enough points to purchase that!");
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}

			}
		}
	}
}
