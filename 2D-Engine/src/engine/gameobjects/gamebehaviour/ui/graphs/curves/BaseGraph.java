// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui.graphs.curves;

import engine.gameobjects.gamebehaviour.ui.graphs.CoordinateSystem;

public abstract class BaseGraph {
	public CoordinateSystem cs;
	public boolean justPositive;

	public BaseGraph() {
		this.cs = null;
		this.justPositive = true;
	}

	public abstract float calcPoint(final float p0);
}
