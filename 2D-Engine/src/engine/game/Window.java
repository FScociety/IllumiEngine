package engine.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.math.Vector2;

public class Window extends Canvas {
	public static JFrame frame;
	public static Font standartFont;
	public static String currentDir;
	public BufferStrategy bs;

	public Graphics g;

	public Window(final GameContainer gc) {

		System.out.println(getClass().getResourceAsStream(""));
		try {
			Window.standartFont = Font.createFont(0, getClass().getResourceAsStream("/DefaultFont.ttf")).deriveFont(0,
					10.0f);
		} catch (FontFormatException | IOException ex2) {
			ex2.printStackTrace();
		}
		BufferedImage engineLogo = null;
		try {
			engineLogo = ImageIO.read(getClass().getResourceAsStream("/EngineLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		final Dimension s = new Dimension((int) gc.getSize().x, (int) gc.getSize().y);
		setSize(s);
		(Window.frame = new JFrame("Engine")).setDefaultCloseOperation(3);
		Window.frame.setLayout(new GridLayout());
		Window.frame.setIconImage(engineLogo);
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(Color.BLACK);
		panel.add(this);
		Window.frame.add(panel);

		Window.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				GameContainer.windowSize = new Vector2(Window.frame.getWidth(), Window.frame.getHeight());
				final Dimension s = new Dimension((int) gc.getSize().x, (int) gc.getSize().y);
				setSize(s);
				createBufferStrategy(2);
				bs = getBufferStrategy();
				g = bs.getDrawGraphics();
				if (GameContainer.d != null) {
					GameContainer.d.g = (Graphics2D) g;
				}
			}
		});
		Window.frame.pack();
		Window.frame.setResizable(true);

		Window.frame.setVisible(true);
		createBufferStrategy(2);
		bs = getBufferStrategy();
		g = bs.getDrawGraphics();
	}
}
