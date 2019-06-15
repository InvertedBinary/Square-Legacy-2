package com.IB.LE2.world.entity.mob.hostile;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.input.UI.GUI;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.emitter.WallParticleSpawner;
import com.IB.LE2.world.entity.mob.Mob;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Node;
import com.IB.LE2.world.level.TileCoord;

@Deprecated
public class WaterFamiliar extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.zombie_down,
			16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.zombie_up, 16,
			16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.zombie_left,
			16, 16, 2);
	transient private AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.zombie_right, 16, 16, 2);
	
	
	transient private AnimatedSprite animSprite = down;

	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient int maxlife = 100;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient private GUI gui;
	transient List<Entity> entities = null;
	transient List<Player> players = null;
	transient Vector2i start;
	transient Vector2i destination;

	public WaterFamiliar(int x, int y, int maxlife) {
		this.maxhealth = 10;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 0;
		this.setX(x << 4);
		this.setY(y << 4);
		this.name = "Water Familiar";
		this.speed = 0.8;
		this.hostility = "NEU";
		sprite = Sprite.playerback;

		this.maxlife = maxlife;

	}

	public void stab() {
		try {
			List<Entity> ent = level.getEntities(this, 20, entities);
			List<Player> p = level.getPlayers(this, 100);

			if (ent.get(0).hostility.equals("AGR")) {
				if (time % 30 == 0) {
					if (p.size() > 0) {
					}
					for (int i = 0; i < ent.size(); i++) {
						//Game.get().getLevel().damage((int) x, (int) y, (Mob) ent.get(0), 0,
								//1 + (Game.get().getPlayer().stat_base_MDF * 0.15), name, 0);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void move() {
		if (entities.size() > 0) {
			xa = 0;
			ya = 0;
			double px = entities.get(0).x();
			double py = (int) entities.get(0).y();
			start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int) y() >> VARS.TILE_BIT_SHIFT);
			destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			if (time % 1 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x() < vec.getX() << 4)
						xa++;
					if (x() > vec.getX() << 4)
						xa--;
					if (y() < vec.getY() << 4)
						ya++;
					if (y() > vec.getY() << 4)
						ya--;
				}
			}
		} else if (players.size() > 0) {
			xa = 0;
			ya = 0;
			double px = level.getPlayerAt(0).x();
			double py = (int) level.getPlayerAt(0).y();
			Vector2i start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int) y() >> VARS.TILE_BIT_SHIFT);
			Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			if (time % 1 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x() < vec.getX() << 4)
						xa++;
					if (x() > vec.getX() << 4)
						xa--;
					if (y() < vec.getY() << 4)
						ya++;
					if (y() > vec.getY() << 4)
						ya--;
				}
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		maxlife--;
		if (time % 8 == 0) {
			this.hurt = false;
		}

		if (maxlife <= 0) {
			level.add(new WallParticleSpawner((int) (x()), (int) (y()), 50, 20, level));
			remove();
		}
		players = level.getPlayers(this, 150);
		entities = level.getEntities(this, 135, "AGR");
		stab();
		if (fireRate > 0) {
			fireRate--;
		}
		time++;
		move();
		if (walking) {
			animSprite.update();
			level.add(new WallParticleSpawner((int) (x()), (int) (y()), 55, 1, level));
		} else
			animSprite.setFrame(0);
		if (ya < 0) {

			animSprite = up;
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
		}
		if (xa < 0) {
			// animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			// animSprite = right;
			dir = DIRECTION.RIGHT;
		}

	}

	public void render(Screen screen) {
		if (this.mobhealth < this.maxhealth)
			screen.renderSprite((int) x() - 16, (int) y() - 20, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset = -8;
		this.yOffset = -15;
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x() + xOffset), (int) (y() + yOffset), this);
		if (Boot.get().devModeOn) {
			screen.drawRect((int) x() + xOffset, (int) y() + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000,
					true);
		}
	}

}