package engine.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.io.LocalFileLoader;
import engine.math.Vector2;

public class Window extends Canvas {
	public static JFrame frame;
	public static Font standartFont_regular;
	public static Font standartFont_bold;
	public static Font standartFont_italic;
	public static Font standartFont_bolditalic;
	public static String currentDir;
	public BufferStrategy bs;

	public Graphics2D g;
	
	public static BufferedImage engineLogo = null;

	public Window(final GameContainer gc) {
		
		LocalFileLoader.w = this;
		
		Window.standartFont_regular = LocalFileLoader.loadFont("/defaultfont/regular.otf");
		Window.standartFont_bold = LocalFileLoader.loadFont("/defaultfont/bold.otf");
		Window.standartFont_italic = LocalFileLoader.loadFont("/defaultfont/italic.otf");
		Window.standartFont_bolditalic = LocalFileLoader.loadFont("/defaultfont/bolditalic.otf");
		
		Window.engineLogo = LocalFileLoader.loadImage("/EngineLogo.png");

		final Dimension s = new Dimension((int) gc.getSize().x, (int) gc.getSize().y);
		setSize(s);
		(Window.frame = new JFrame("Engine")).setDefaultCloseOperation(3);
		//Window.frame.setLayout(new BorderLayout());
		Window.frame.setIconImage(engineLogo);
		JPanel panel = new JPanel(new BorderLayout());
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
				g = (Graphics2D) bs.getDrawGraphics();
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
		g = (Graphics2D) bs.getDrawGraphics();
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
	}
}
