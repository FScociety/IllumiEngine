// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui.graphs;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.ui.graphs.curves.BaseGraph;
import engine.math.Vector2;

public class CoordinateSystem extends GameBehaviour {
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int pixelPerUnit;
	private ArrayList<Coordinate> cords;
	private Font f;
	private boolean polar;

	public CoordinateSystem() {
		this.minX = 10;
		this.maxX = 10;
		this.minY = 10;
		this.maxY = 10;
		this.cords = new ArrayList<Coordinate>();
	}

	public CoordinateSystem(final int minX, final int maxX, final int minY, final int maxY, final int pixelPerUnit,
			final Font f) {
		this.minX = 10;
		this.maxX = 10;
		this.minY = 10;
		this.maxY = 10;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.setPixelPerUnit(pixelPerUnit);
		this.cords = new ArrayList<Coordinate>();
		this.f = f;
	}

	public void addPoint(final boolean connected, final float x, final float y) {
		if (x <= this.maxX && x >= -this.minX && y <= this.maxY && y >= -this.minY) {
			this.cords.add(new Coordinate(this, connected, x, y));
		}
	}

	public float getMaxX() {
		return this.maxX;
	}

	public float getMaxY() {
		return this.maxY;
	}

	public float getMinX() {
		return this.minX;
	}

	public float getMinY() {
		return this.minY;
	}

	public int getPixelPerUnit() {
		return this.pixelPerUnit;
	}

	public boolean getPolar() {
		return this.polar;
	}

	@Override
	public void render() {
		this.d.setColor(Color.GRAY);
		for (int x = 0; x < this.maxX + this.minX; ++x) {
			for (int y = 0; y < this.maxY + this.minY; ++y) {
				this.g.drawRect((x - this.minX) * this.pixelPerUnit + (int) this.gameObject.getPosX(),
						(y - this.maxY) * this.pixelPerUnit + (int) this.gameObject.getPosY(), this.pixelPerUnit,
						this.pixelPerUnit);
			}
		}
		this.d.setColor(Color.GREEN);
		this.d.fillRect(new Vector2(-5), new Vector2(10));
		this.d.drawLine(0, 0, this.maxX * this.pixelPerUnit, 0);
		this.d.drawLine(0, 0,
				(int) (this.gameObject.getPosX() - this.minX * this.pixelPerUnit), (int) this.gameObject.getPosY());
		this.d.drawLine((int) this.gameObject.getPosX(), (int) this.gameObject.getPosY(),
				(int) this.gameObject.getPosX(), (int) (this.gameObject.getPosY() - this.maxX * this.pixelPerUnit));
		this.d.drawLine((int) this.gameObject.getPosX(), (int) this.gameObject.getPosY(),
				(int) this.gameObject.getPosX(), (int) (this.gameObject.getPosY() + this.minX * this.pixelPerUnit));
		this.d.setFont(this.f.deriveFont(25.0f));
		this.d.drawString("X", (int) this.gameObject.getPosX() - 10,
				(int) this.gameObject.getPosY() - this.maxX * this.pixelPerUnit);
		this.d.drawString("Y", (int) this.gameObject.getPosX() + this.maxX * this.pixelPerUnit,
				(int) this.gameObject.getPosY() + 10);
		this.d.setColor(Color.ORANGE);
		Coordinate lastCord = null;
		for (final Coordinate cord : this.cords) {
			if (lastCord != null && lastCord.isConnected()) {
				this.g.drawLine((int) (lastCord.getX(this.polar) * this.pixelPerUnit + this.gameObject.getPosX()),
						(int) (lastCord.getY(this.polar) * -this.pixelPerUnit + this.gameObject.getPosY()),
						(int) (cord.getX(this.polar) * this.pixelPerUnit + this.gameObject.getPosX()),
						(int) (cord.getY(this.polar) * -this.pixelPerUnit + this.gameObject.getPosY()));
			}
			lastCord = cord;
		}
	}

	public void runFunction(final BaseGraph bg, final float res) {
		bg.cs = this;
		final float i2 = (this.minX * (bg.justPositive ? 0 : 1) + this.maxX) / res;
		for (int j = 0; j < i2; ++j) {
			final float x = (j - this.minX / res) * res;
			if (bg.justPositive) {
				this.addPoint(j + 1 != i2, x + this.minX, bg.calcPoint(x + this.minX + 1.0f));
			} else {
				this.addPoint(j + 1 != i2, x, bg.calcPoint(x + 1.0f));
			}
		}
	}

	public void setMaxX(final int maxX) {
		this.maxX = maxX;
		this.SizeUpdatet();
	}

	public void setMaxY(final int maxY) {
		this.maxY = maxY;
		this.SizeUpdatet();
	}

	public void setMinX(final int minX) {
		this.minX = minX;
		this.SizeUpdatet();
	}

	public void setMinY(final int minY) {
		this.minY = minY;
		this.SizeUpdatet();
	}

	public void setPixelPerUnit(final int pixelPerUnit) {
		this.pixelPerUnit = pixelPerUnit;
	}

	public void setPolar(final boolean polar) {
		this.polar = polar;
	}

	public void SizeUpdatet() {
		for (final Coordinate cord : this.cords) {
			if (cord.getX(this.polar) > this.maxX || cord.getX(this.polar) < this.minX
					|| cord.getY(this.polar) > this.maxY || cord.getY(this.polar) < this.minX) {
				this.cords.remove(cord);
			}
		}
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
}
