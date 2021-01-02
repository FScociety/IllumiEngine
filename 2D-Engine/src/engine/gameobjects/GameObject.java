package engine.gameobjects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.Scene;

public class GameObject implements Serializable {

	private Transform localTransform = new Transform();
	private Transform globalTransform = new Transform();
	private ArrayList<GameBehaviour> components = new ArrayList<>();
	private ArrayList<GameObject> children = new ArrayList<>(); // Vllt doch erst bei runTime intitialisieren?
	private GameObject parent;
	public boolean started = false;
	public boolean updatesOutOfView = false;

	private final boolean inWorld;
	private boolean active = true;
	private Scene loadingScene;

	public int viewRange = 100; // Change if needed

	public GameObject(Vector2 pos, boolean inWorld) {
		this.setTransform(new Transform(pos, 0, new Vector2(1)));
		this.inWorld = inWorld;
	}

	public GameObject(Vector2 pos, final GameObject parent) {
		this.inWorld = parent.getInWorld();
		this.setParent(parent);
		this.setTransform(new Transform(pos, 0, new Vector2(1)));
	}

	public void addChildren(final GameObject child) {
		this.children.add(child);
	}

	public void addComponent(final GameBehaviour gb) {
		gb.gameObject = this;
		this.components.add(gb);

		if (this.started == true) {
			gb.d = GameContainer.d;
			gb.start();
			gb.started = true;
		}
	}

	public void addPosition(final Vector2 position) {
		this.setTransform(new Transform(Vector2.add(position, this.localTransform.position),
				this.localTransform.rotation, this.localTransform.scale));
	}

	public void addRotation(float r) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation + r,
				this.localTransform.scale));
	}

	public GameObject getChild() {
		return this.children.get(0);
	}

	public ArrayList<GameObject> getChildren() {
		return this.children;
	}

	/*
	 * public void setInWorld(boolean state) { this.inWorld = state; for (final
	 * GameObject obj : this.children) { obj.setInWorld(state); } }
	 */

	public int getChildrenCount() {
		int children = 0;
		for (int i = 0; i < this.children.size(); i++) {
			children += this.children.get(i).getChildrenCount() + 1;
		}
		return children;
	}

	public GameBehaviour getComponent(Class<? extends GameBehaviour> e) {
		for (final GameBehaviour component2 : this.components) {
			if (component2.getClass() == e || e.isInstance(component2)) {
				return component2;
			}
		}
		return null;
	}

	public int getComponentCount() {
		return this.components.size();
	}
	
	public ArrayList<GameBehaviour> getComponents() { //Think: Maybe wäre Array besser aber ich glaub nicht weil er dann umwandeln müsste
		return this.components;
	}

	public GameObject getCopy() {
		// MAGIC FUNKTION FROM STACK OVERFLOW FOR COPIING OBJECTS AND I HAVE NO IDEA
		// HOW IT WORKS BUT IT WORKKKKKKKKKKKKKKSSSSSSSS!!!!!!!!!!
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] byteData = bos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
		GameObject object = null;
		try {
			object = (GameObject) new ObjectInputStream(bais).readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return object;
	}

	public boolean getInWorld() {
		return this.inWorld;
	}

	public Transform getLocalTransform() {
		return this.localTransform.getCopy();
	}

	public GameObject getParent() {
		return this.parent;
	}

	public Transform getTransform() {
		return this.globalTransform.getCopy();
	}

	public Transform getTransformWithCaution() {
		return this.globalTransform;
	}
	
	public boolean getActive() {
		return this.active;
	}

	public void removeComponent(final GameBehaviour gb) {
		this.components.remove(gb);
		// Maybe noch remove Compand in GameBehaviour
	}

	public void render() {
		if (!active) { return; }
		
		GameContainer.d.applyTransforms(this);
		
		for (final GameBehaviour component : this.components) {
			if (component.active) {
				component.render();
			}
		}
		
		GameContainer.d.resetTransform();

		for (final GameObject child : this.children) {
			child.render();
		}
	}

	public void setChildren(final ArrayList<GameObject> children) {
		this.children = children;
	}

	public void setParent(final GameObject parent) {
		if (parent.getInWorld() == this.getInWorld()) {
			(this.parent = parent).addChildren(this);
		} else {
			System.err.println("You cant parent an Object with inWorld = " + parent.getInWorld()
					+ " and an Object with InWorld = " + this.getInWorld());
		}
	}

	public void setPosition(final Vector2 position) {
		this.setTransform(new Transform(position, this.localTransform.rotation, this.localTransform.scale));
	}

	public void setRotation(final float rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation, this.localTransform.scale));
	}

	public void setScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation, scale));
	}

	public void setTransform(final Transform transform) {
		// Bei DiffTransform verbesserung: Ist dumm immer 'this.getTransform()' zu
		// machen ist besser mit varibale
		Transform diffTransform = new Transform(Vector2.substract(transform.position, this.getTransform().position),
				transform.rotation - this.getTransform().rotation,
				Vector2.substract(transform.scale, this.getTransform().scale));
		this.localTransform = transform;
		if (this.parent != null) {
			this.globalTransform = new Transform(
					Vector2.add(this.parent.getTransform().position, this.localTransform.position), // Doesnt berehcnen
																									// the rotationg mit
																									// ein
					this.parent.getTransform().rotation + this.localTransform.rotation,
					Vector2.multiply(this.parent.getTransform().scale, this.localTransform.scale));
		} else {
			this.globalTransform = this.localTransform;
		}

		if (this.children.size() > 0) {
			this.updateTransform(diffTransform);
		}
	}
	
	public void activade() {
		this.active = true;
		
		if (!started) {
			this.startSub(this.loadingScene);
		}
	}
	
	public void deactivade() {
		this.active = false;
	}

	public void start(Scene loadingScene) {
		if (active) {
			startSub(loadingScene);
		} else {
			this.loadingScene = loadingScene;
		}
	}
	
	private void startSub(Scene loadingScene) {
		this.started = true;
		loadingScene.addObjectCount();

		Object[] bufferGb = this.components.toArray();

		for (int i = 0; i < bufferGb.length; ++i) {
			startComponent(this.components.get(i));
		}
		if (this.children.size() > 0) {
			for (int i = 0; i < this.children.size(); ++i) {
				this.children.get(i).start(loadingScene);
			}
		}
		System.out.println("Started GameObject[" + this + "]");
	}

	@Override
	public String toString() {
		return ("[Transform: " + this.globalTransform + ", Children: " + this.children.size() + ", Parent: "
				+ (this.parent != null ? true : false));
	}

	public String toStringShort() {
		return this.globalTransform.position + "";
	}

	public void update() {
		if (!active) { return; }
		
		for (final GameBehaviour component : this.components) {
			if (component.active) {
				component.update();
			}
		}

		for (final GameObject child : this.children) {
			child.update();
		}
	}

	public void updateGlobalTransform(final GameObject parent, Transform diffTransform) {
		this.localTransform.position.rotate(diffTransform.rotation);

		this.globalTransform = new Transform(
				Vector2.add(this.parent.getTransform().position, this.localTransform.position),
				this.parent.getTransform().rotation + this.localTransform.rotation,
				Vector2.multiply(this.parent.getTransform().scale, this.localTransform.scale));

		if (this.children.size() > 0) {
			this.updateTransform(diffTransform);
		}
	}

	public void updateTransform(Transform diffTransform) {
		for (int i = 0; i < this.children.size(); i++) {
			this.children.get(i).updateGlobalTransform(this, diffTransform);
		}
	}

	private void startComponent(GameBehaviour gb) {
		gb.gameObject = this;
		if (gb.testInWorldStateSame()) {
			gb.d = GameContainer.d;
			gb.start();
			gb.started = true;
		}
	}
}