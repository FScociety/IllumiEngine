package engine.game;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.input.listener.ViewListener;
import engine.io.LocalFileLoader;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class Window extends Canvas {
	public static JFrame frame;
	public static Font standartFont_regular;
	public static Font standartFont_bold;
	public static Font standartFont_italic;
	public static Font standartFont_bolditalic;
	public static String currentDir;
	public BufferStrategy bs;
	
	public static ArrayList<ViewListener> viewListener = new ArrayList<ViewListener>();

	public Graphics2D g;
	
	public static BufferedImage engineLogo = null;
	
	private float timeAfterUpdate;

	public Window(final GameContainer gc) {
		
		LocalFileLoader.w = this;
		
		Window.standartFont_regular = LocalFileLoader.loadFont("/defaultfont/regular.otf");
		Window.standartFont_bold = LocalFileLoader.loadFont("/defaultfont/bold.otf");
		Window.standartFont_italic = LocalFileLoader.loadFont("/defaultfont/italic.otf");
		Window.standartFont_bolditalic = LocalFileLoader.loadFont("/defaultfont/bolditalic.otf");
		
		Window.engineLogo = LocalFileLoader.loadImage("/EngineLogo.png");
		
        // activate opengl
        System.setProperty("sun.java2d.opengl", "true");

		final Dimension s = new Dimension((int) GameContainer.windowSize.x, (int) GameContainer.windowSize.y);
		(Window.frame = new JFrame("Engine")).setDefaultCloseOperation(3);
		Window.frame.setIconImage(engineLogo);
		JPanel panel = new JPanel(null);
		panel.setIgnoreRepaint(true);
		panel.add(this);
		Window.frame.add(panel);
		Window.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				timeAfterUpdate = 0.1f;
			}
		});
		Window.frame.setResizable(true);
		Window.frame.setVisible(true);
		Window.frame.setPreferredSize(s);
		Window.frame.pack();
		
		updateWindowGraphics();
	}
	
	private void updateWindowGraphics() {
		GameContainer.windowSize = new Vector2(Window.frame.getWidth(), Window.frame.getHeight());
		final Dimension s = new Dimension((int) GameContainer.windowSize.x, (int) GameContainer.windowSize.y);
		setSize(s);
		createBufferStrategy(2);
		bs = getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		if (GameContainer.d != null) {
			GameContainer.d.g = (Graphics2D) g;
		}
		if (SceneManager.activeScene != null) {
			SceneManager.activeScene.canvas.ScreenSizeChange();
		}
	}
	
	public void update() {
		if (this.timeAfterUpdate >= 0.1f) {
			this.timeAfterUpdate += GameContainer.dt;
		
			if (this.timeAfterUpdate >= 0.2f) {
				this.updateWindowGraphics();
				for (ViewListener vl : Window.viewListener) {
					vl.rezise();
				}
				this.timeAfterUpdate = 0;
			}
		}
	}
}