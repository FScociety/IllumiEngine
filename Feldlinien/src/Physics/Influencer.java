package Physics;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.Window;
import engine.gameobjects.gamebehaviour.builtin.debug.TransformController;
import engine.gameobjects.gamebehaviour.builtin.ui.Text2;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;

public class Influencer extends GameBehaviour {
	
	public static ArrayList<Influencer> influencers = new ArrayList<Influencer>();
	int charge;
	
	public Influencer(int charge) {
		this.charge = charge;
	}
	
	public void start() {
		influencers.add(this);
		TransformController tc = new TransformController(true, false, true);
		tc.getButton().setBaseColor(charge == 1 ? Color.RED : Color.BLUE);
		this.gameObject.addComponent(tc);
		Text2 t = new Text2(charge+"", 20, Color.WHITE, this);
		this.gameObject.addComponent(t);
		System.out.println(this.gameObject.getComponent(TransformController.class)+"");
	}
}