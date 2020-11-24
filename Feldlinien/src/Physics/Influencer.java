package Physics;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.Window;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.gameobjects.gamebehaviour.TransformController;

public class Influencer extends GameBehaviour {
	
	public static ArrayList<Influencer> influencers = new ArrayList<Influencer>();
	int charge;
	
	public Influencer(int charge) {
		this.charge = charge;
		influencers.add(this);
	}
	
	public void start() {
		TransformController tc = new TransformController(true, false, true);
		tc.getButton().setBaseColor(charge == 1 ? Color.RED : Color.BLUE);
		this.gameObject.addComponent(tc);
		Text t = new Text(charge+"", 20, Window.standartFont, Color.WHITE);
		this.gameObject.addComponent(t);
		System.out.println(this.gameObject.getComponent(TransformController.class)+"");
	}
}