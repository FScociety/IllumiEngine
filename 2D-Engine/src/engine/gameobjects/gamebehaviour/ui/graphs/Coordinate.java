// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui.graphs;

public class Coordinate {
	private CoordinateSystem cs;
	private boolean connected;
	private float x;
	private float y;

	public Coordinate(final CoordinateSystem cs) {
		this.x = 0.0f;
		this.y = 0.0f;
		this.setCs(cs);
	}

	public Coordinate(final CoordinateSystem cs, final boolean connected, final float x, final float y) {
		this.x = 0.0f;
		this.y = 0.0f;
		this.setX(x);
		this.setY(y);
		this.setCs(cs);
		this.setConnected(connected);
	}

	public CoordinateSystem getCs() {
		return this.cs;
	}

	public float getX(final boolean polar) {
		if (polar) {
			return (float) Math.cos(this.y) * this.x;
		}
		return this.x;
	}

	public float getY(final boolean polar) {
		if (polar) {
			return (float) Math.sin(this.y) * this.x;
		}
		return this.y;
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(final boolean connected) {
		this.connected = connected;
	}

	public void setCs(final CoordinateSystem cs) {
		this.cs = cs;
	}

	public void setX(final float x) {
		this.x = x;
	}

	public void setY(final float y) {
		this.y = y;
	}
}
