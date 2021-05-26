package engine.io;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.game.GameContainer;
import engine.game.Window;

public class LocalFileLoader {
	
	public static Window w;
	
	private static final String prefix = "LocalFileLoader";
	
	public static Font loadFont(String localPath) {
		Font f = null;
		try {
			f = Font.createFont(0, w.getClass().getResourceAsStream(localPath));
			Logger.println(prefix, "Loaded Font " + localPath, 0);
		} catch (FontFormatException | IOException ex2) {
			Logger.println(prefix, "Failed loading Font " + localPath, 2);
		}
		return f;
	}
	
	public static BufferedImage loadImage(String localPath) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(w.getClass().getResourceAsStream("/EngineLogo.png"));
			Logger.println(prefix, "Loaded Image " + localPath, 0);
		} catch (IOException e) {
			Logger.println(prefix, "Failed loading Image " + localPath, 2);
		}
		return bi;
	}
}