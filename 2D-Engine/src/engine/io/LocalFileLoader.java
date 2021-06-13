package engine.io;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
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
	
	public static BufferedImage toCompatibleImage(BufferedImage image) {
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfxConfig = GraphicsEnvironment.
	        getLocalGraphicsEnvironment().getDefaultScreenDevice().
	        getDefaultConfiguration();

	    /*
	     * if image is already compatible and optimized for current system 
	     * settings, simply return it
	     */
	    if (image.getColorModel().equals(gfxConfig.getColorModel()))
	        return image;

	    // image is not optimized, so create a new image that is
	    BufferedImage newImage = gfxConfig.createCompatibleImage(
	            image.getWidth(), image.getHeight(), Transparency.BITMASK);

	    // get the graphics context of the new image to draw the old image on
	    Graphics2D g2d = newImage.createGraphics();

	    // actually draw the image and dispose of context no longer needed
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    // return the new optimized image
	    return newImage; 
	}
}