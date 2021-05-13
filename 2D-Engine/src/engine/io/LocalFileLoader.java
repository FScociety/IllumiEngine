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
	
	public static Font loadFont(String localPath) {
		Font f = null;
		try {
			f = Font.createFont(0, w.getClass().getResourceAsStream(localPath));
			System.out.println("[FileLoader] : Loaded Font " + localPath);
		} catch (FontFormatException | IOException ex2) {
			System.err.println("Could NOT load " + localPath);
		}
		return f;
	}
	
	public static BufferedImage loadImage(String localPath) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(w.getClass().getResourceAsStream("/EngineLogo.png"));
			System.out.println("[FileLoader] : Loaded Image " + localPath);
		} catch (IOException e) {
			System.err.println("Could not LOAD the EngineLogo");
		}
		return bi;
	}
}