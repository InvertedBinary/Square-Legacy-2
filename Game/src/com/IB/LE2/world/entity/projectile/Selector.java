package com.IB.LE2.world.entity.projectile;

import java.util.Random;

import com.IB.LE2.world.entity.Entity;

public class Selector extends Projectile {
	private static final long serialVersionUID = 1L;

	public int xa, ya;

	protected static Random Random = new Random();
	public static Entity selected;

	public Selector(double x, double y) {
		super(x, y, 0);
	}

	public void update() {
		Entity to_select = Collide(this, level.entitiesList);
		if (to_select != null) {
			selected = to_select;
		}

		remove();
	}
}