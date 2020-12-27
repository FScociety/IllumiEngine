// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.builtin.ui.graphs.curves;

public class ArchimedischSpiral extends BaseGraph {
	float gRotation;
	float frequency;

	public ArchimedischSpiral(final float frequency, final float gRotation) {
		this.gRotation = 0.0f;
		this.frequency = 1.0f;
		this.gRotation = gRotation;
		this.frequency = frequency;
		this.justPositive = true;
	}

	@Override
	public float calcPoint(final float x) {
		return x * this.frequency + this.gRotation;
	}
}
