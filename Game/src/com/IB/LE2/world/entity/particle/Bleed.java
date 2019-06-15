package com.IB.LE2.world.entity.particle;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;

public class Bleed extends Particle {

	transient private Sprite sprite;
	//private Particle particle;
	
	public Bleed(int x, int y, int life, int amount) {
		super(x,y,life,amount);
		this.setX(x);
		this.setY(y);
		this.xx = x;
		this.yy = y;
		sprite = Sprite.bleed;
		this.invulnerable = true;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
		public void update() {
			time++;
			if (time >= 7400) time = 0;
			if (time > life) remove();
			za -= 0.1;
			
			if (zz < 0) {
				zz = 0;
				za *= -0.55;
				xa *= 0.4;
				ya *= 0.4;
			}
			
			move(xx + xa, (yy + ya) + (zz + za));
		}
		
		private void move(double x, double y) {
				if (collision(x, y)) {
					this.xa *= -0.5;
					this.ya *= -0.5;
					this.za *= -0.5;
				}
				this.xx += xa;
				this.yy += ya;
				this.zz += za;
	
			}
			
			

		
		public boolean collision(double x, double y) {
			boolean solidtwo = false;
			for(int c = 0; c < 4; c++) {
				double xt = (x - c % 2 * 16) / 16;
				double yt = (y - c / 2 * 16) / 16 + 10;
				int ix = (int) Math.ceil(xt);
				int iy = (int) Math.ceil(yt);
				if (c % 2 == 0) ix = (int)Math.floor(xt);
				if (c / 2 == 0) iy = (int)Math.floor(yt);
				if (level.getTile(ix, iy).solidtwo()) solidtwo = true;
			}
			return solidtwo;
		}
		

		public void render(Screen screen) {
			screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
		}

}
