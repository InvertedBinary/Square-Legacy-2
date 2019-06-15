package com.IB.LE2.input.UI;

import java.util.ArrayList;

import com.IB.LE2.input.UI.components.basic.UI_Menu;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Mob;
import com.IB.LE2.world.entity.mob.Player;

public class GUI extends CheckBounds {
	private static final long serialVersionUID = 1L;

	transient public Font16x font;
	transient public Font8x font8x8;

	public transient int displayTime = 0;
	transient public int displayTimeM = 0;
	transient public int displayTimeS = 0;

	private transient Sprite MobHealthSprite = Sprite.MobHealthBar20;

	private transient Sprite HealthSprite = Sprite.HealthBar20;
	private transient Sprite ManaSprite = Sprite.manabar20;
	private transient Sprite StaminaSprite = Sprite.manabar20;

	Player tempLoadInfo = null;

	transient public AnimatedSprite healthbar = new AnimatedSprite(SpriteSheet.anim_hb, 72, 32, 61);
	transient public AnimatedSprite manabar = new AnimatedSprite(SpriteSheet.anim_mb, 72, 32, 61);
	transient public AnimatedSprite staminabar = new AnimatedSprite(SpriteSheet.anim_sb, 72, 32, 61);
	transient public AnimatedSprite expbar = new AnimatedSprite(SpriteSheet.anim_eb, 156, 32, 151);

	public boolean displayH = true, displayM = true, displayS = true;
	public int yOffM = 130, yOffH = 143, yOffS = 156;
	
	public transient UI_Menu menu = new UI_Menu();

	public GUI() {
		font = new Font16x();
		font8x8 = new Font8x();
	}

	public void update() {
		// expBar.update();
		for (int i = 0; i < UI_Menu.menus.size(); i++) {
			UI_Menu.menus.get(i).updateUnloaded();
		}
		
		menu.UpdateMacros();
		menu.update();
	}

	public void render(Screen screen) {
		menu.render(screen);
	}


	public void renderString(Screen screen, String PersonNameGetter, int x, int y, int spacing, int color,
			boolean fixed, boolean background, boolean font8) {
		if (!font8) {
			font.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		} else {
			font8x8.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		}
	}

	public void renderBuild(Screen screen, Player player) {
		
	}
	

	// 263, 42, 55[mana]

	public Sprite renderBar(int size, AnimatedSprite sprite, double max, double current) {
		double currentHealth = current;
		double maxHealth = max;
		double special = maxHealth - 1;
		double barSpace = size;
		double slope = maxHealth / barSpace;
		double hb = barSpace;
		double s1 = 0;

		ArrayList<Double> variables = new ArrayList<Double>();

		for (int i = 0; i < barSpace - 2; i++) {
			if (i == 0) {
				if (maxHealth > barSpace) {
					s1 = special - slope;// 38 OR 4.75
				} else if (maxHealth <= barSpace) {
					s1 = maxHealth - slope;// 38 OR 4.75
				}
				variables.add(s1);
			} else {
				variables.add(variables.get(i - 1) - slope);
			}
		}

		if (currentHealth < maxHealth) {
			if (currentHealth <= 0) {
				hb = 0;
			} else {
				hb = 1;
				for (int i = 0; i < barSpace - 2; i++) {
					if (currentHealth >= variables.get(i)) {
						hb = barSpace - i - 1;
						break;
					}
				}
			}
		}
		sprite.setFrame((int) size - (int) hb);
		return sprite.getSprite();
	}

	public Sprite renderHealthExperiment(Screen screen, Mob mob, int size) {
		double CurrentHealth = mob.mobhealth;
		double MaxHealth = mob.maxhealth;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25
		
		if (mob.incombat || mob.mobhealth < mob.maxhealth) {
			displayTime = 0;
		} else if (displayTime < 151) {
			displayTime++;
			displayH = false;
		}
		if (displayTime <= 150) {
			displayH = true;
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				HealthSprite = Sprite.HealthBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				HealthSprite = Sprite.HealthBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				HealthSprite = Sprite.HealthBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				HealthSprite = Sprite.HealthBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				HealthSprite = Sprite.HealthBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				HealthSprite = Sprite.HealthBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				HealthSprite = Sprite.HealthBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				HealthSprite = Sprite.HealthBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				HealthSprite = Sprite.HealthBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				HealthSprite = Sprite.HealthBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				HealthSprite = Sprite.HealthBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				HealthSprite = Sprite.HealthBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				HealthSprite = Sprite.HealthBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				HealthSprite = Sprite.HealthBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				HealthSprite = Sprite.HealthBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				HealthSprite = Sprite.HealthBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				HealthSprite = Sprite.HealthBar4;
				screen.fade(20, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				HealthSprite = Sprite.HealthBar3;
				screen.fade(40, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				HealthSprite = Sprite.HealthBar2;
				screen.fade(60, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				HealthSprite = Sprite.HealthBar1;
				screen.fade(80, 0, 0);
			}
			if (CurrentHealth <= 0) {
				HealthSprite = Sprite.HealthBar0;
			}
		} else {
			HealthSprite = new Sprite(0, 0xff000000);
		}
		return HealthSprite;
	}

	public Sprite renderMobHealthExperiment(Entity mob, int size) {
		double CurrentHealth = mob.mobhealth;
		double MaxHealth = mob.maxhealth;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		if (mob.mobhealth < mob.maxhealth || mob.incombat) {
			displayTime = 0;
		} else if (displayTime < 151) {
			displayTime++;
		}
		if (displayTime < 150) {
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				MobHealthSprite = Sprite.MobHealthBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				MobHealthSprite = Sprite.MobHealthBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				MobHealthSprite = Sprite.MobHealthBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				MobHealthSprite = Sprite.MobHealthBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				MobHealthSprite = Sprite.MobHealthBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				MobHealthSprite = Sprite.MobHealthBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				MobHealthSprite = Sprite.MobHealthBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				MobHealthSprite = Sprite.MobHealthBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				MobHealthSprite = Sprite.MobHealthBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				MobHealthSprite = Sprite.MobHealthBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				MobHealthSprite = Sprite.MobHealthBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				MobHealthSprite = Sprite.MobHealthBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				MobHealthSprite = Sprite.MobHealthBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				MobHealthSprite = Sprite.MobHealthBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				MobHealthSprite = Sprite.MobHealthBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				MobHealthSprite = Sprite.MobHealthBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				MobHealthSprite = Sprite.MobHealthBar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				MobHealthSprite = Sprite.MobHealthBar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				MobHealthSprite = Sprite.MobHealthBar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				MobHealthSprite = Sprite.MobHealthBar1;
			}
			if (CurrentHealth <= 0) {
				MobHealthSprite = Sprite.MobHealthBar0;
			}
		}
		return MobHealthSprite;
	}

	public Sprite renderManaExperiment(Mob mob, int size) {
		double CurrentHealth = mob.mana;
		double MaxHealth = mob.maxmana;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		if (mob.incombat || mob.mana < mob.maxmana) {
			displayTimeM = 0;
		} else if (displayTimeM < 151) {
			displayTimeM++;
			displayM = false;
		}
		if (displayTimeM <= 150) {
			displayM = true;
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				ManaSprite = Sprite.manabar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				ManaSprite = Sprite.manabar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				ManaSprite = Sprite.manabar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				ManaSprite = Sprite.manabar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				ManaSprite = Sprite.manabar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				ManaSprite = Sprite.manabar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				ManaSprite = Sprite.manabar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				ManaSprite = Sprite.manabar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				ManaSprite = Sprite.manabar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				ManaSprite = Sprite.manabar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				ManaSprite = Sprite.manabar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				ManaSprite = Sprite.manabar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				ManaSprite = Sprite.manabar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				ManaSprite = Sprite.manabar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				ManaSprite = Sprite.manabar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				ManaSprite = Sprite.manabar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				ManaSprite = Sprite.manabar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				ManaSprite = Sprite.manabar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				ManaSprite = Sprite.manabar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				ManaSprite = Sprite.manabar1;
			}
			if (CurrentHealth <= 0) {
				ManaSprite = Sprite.manabar0;
			}
		} else {
			ManaSprite = new Sprite(0, 0xff000000);
		}
		return ManaSprite;
	}

	public Sprite renderStaminaExperiment(Mob mob, int size) {
		double CurrentHealth = mob.stamina;
		double MaxHealth = mob.maxstamina;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		/*
		 * if (mob.stamina < mob.maxstamina|| mob.incombat) { displayTimeS = 0;
		 * } else if (displayTimeS < 151){ displayTimeS++; } if (displayTime <
		 * 150) {
		 */
		if (mob.stamina < mob.maxstamina) {
			displayTimeS = 0;
			displayS = true;
		} else if (displayTimeS < 151) {
			displayS = false;
			displayTimeS++;
		}
		if (displayS) {
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				StaminaSprite = Sprite.StaminaBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				StaminaSprite = Sprite.StaminaBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				StaminaSprite = Sprite.StaminaBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				StaminaSprite = Sprite.StaminaBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				StaminaSprite = Sprite.StaminaBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				StaminaSprite = Sprite.StaminaBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				StaminaSprite = Sprite.StaminaBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				StaminaSprite = Sprite.StaminaBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				StaminaSprite = Sprite.StaminaBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				StaminaSprite = Sprite.StaminaBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				StaminaSprite = Sprite.StaminaBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				StaminaSprite = Sprite.StaminaBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				StaminaSprite = Sprite.StaminaBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				StaminaSprite = Sprite.StaminaBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				StaminaSprite = Sprite.StaminaBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				StaminaSprite = Sprite.StaminaBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				StaminaSprite = Sprite.StaminaBar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				StaminaSprite = Sprite.StaminaBar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				StaminaSprite = Sprite.StaminaBar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				StaminaSprite = Sprite.StaminaBar1;
			}
			if (CurrentHealth <= 0) {
				StaminaSprite = Sprite.StaminaBar0;
			}
		} else {
			StaminaSprite = new Sprite(0, 0xff000000);
		}
		return StaminaSprite;
	}

	public void renderManaExperiment(Screen screen, Mob mob, int x, int y, boolean fixed, int size) {
		int CurrentMana = (int) mob.mana;
		int MaxMana = (int) mob.maxmana;
		int Special = MaxMana - 1;
		int BarSpace = size;
		int Slope = MaxMana / BarSpace;
		// int hb= 20;
		int s1 = Special - Slope;// 38
		int s2 = s1 - Slope;// 36
		int s3 = s2 - Slope;// 34
		int s4 = s3 - Slope;// 32
		int s5 = s4 - Slope;// 30
		int s6 = s5 - Slope;// 28
		int s7 = s6 - Slope;// 26
		int s8 = s7 - Slope;// 24
		int s9 = s8 - Slope;// 22
		int s10 = s9 - Slope;// 20
		int s11 = s10 - Slope;// 18
		int s12 = s11 - Slope;// 16
		int s13 = s12 - Slope;// 14
		int s14 = s13 - Slope;// 12
		int s15 = s14 - Slope;// 10
		int s16 = s15 - Slope;// 8
		int s17 = s16 - Slope;// 6
		int s18 = s17 - Slope;// 4
		int s19 = s18 - Slope;// 2

		if (mob.mana < mob.maxmana || mob.incombat) {
			displayTimeM = 0;
		} else if (displayTimeM < 151) {
			displayTimeM++;
		}
		if (displayTimeM < 150) {
			if (CurrentMana == MaxMana || CurrentMana > MaxMana) {
				screen.renderSprite(x, y, Sprite.manabar20, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s1)) {
				screen.renderSprite(x, y, Sprite.manabar19, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s2) && (CurrentMana < s1)) {
				screen.renderSprite(x, y, Sprite.manabar18, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s3) && (CurrentMana < s2)) {
				screen.renderSprite(x, y, Sprite.manabar17, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s4) && (CurrentMana < s3)) {
				screen.renderSprite(x, y, Sprite.manabar16, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s5) && (CurrentMana < s4)) {
				screen.renderSprite(x, y, Sprite.manabar15, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s6) && (CurrentMana < s5)) {
				screen.renderSprite(x, y, Sprite.manabar14, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s7) && (CurrentMana < s6)) {
				screen.renderSprite(x, y, Sprite.manabar13, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s8) && (CurrentMana < s7)) {
				screen.renderSprite(x, y, Sprite.manabar12, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s9) && (CurrentMana < s8)) {
				screen.renderSprite(x, y, Sprite.manabar11, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s10) && (CurrentMana < s9)) {
				screen.renderSprite(x, y, Sprite.manabar10, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s11) && (CurrentMana < s10)) {
				screen.renderSprite(x, y, Sprite.manabar9, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s12) && (CurrentMana < s11)) {
				screen.renderSprite(x, y, Sprite.manabar8, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s13) && (CurrentMana < s12)) {
				screen.renderSprite(x, y, Sprite.manabar7, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s14) && (CurrentMana < s13)) {
				screen.renderSprite(x, y, Sprite.manabar6, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s15) && (CurrentMana < s14)) {
				screen.renderSprite(x, y, Sprite.manabar5, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s16) && (CurrentMana < s15)) {
				screen.renderSprite(x, y, Sprite.manabar4, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s17) && (CurrentMana < s16)) {
				screen.renderSprite(x, y, Sprite.manabar3, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s18) && (CurrentMana < s17)) {
				screen.renderSprite(x, y, Sprite.manabar2, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s19) && (CurrentMana < s18)) {
				screen.renderSprite(x, y, Sprite.manabar1, fixed);
			}
			if (CurrentMana <= 0) {
				screen.renderSprite(x, y, Sprite.manabar0, fixed);
			}
		}
	}

	public void renderStaminaExperiment(Screen screen, Mob mob, int x, int y, boolean fixed, int size) {
		int CurrentStamina = (int) mob.stamina;
		int MaxStamina = (int) mob.maxstamina;
		int Special = MaxStamina - 1;
		int BarSpace = size;
		int Slope = MaxStamina / BarSpace;
		// int hb= 20;
		int s1 = Special - Slope;// 38
		int s2 = s1 - Slope;// 36
		int s3 = s2 - Slope;// 34
		int s4 = s3 - Slope;// 32
		int s5 = s4 - Slope;// 30
		int s6 = s5 - Slope;// 28
		int s7 = s6 - Slope;// 26
		int s8 = s7 - Slope;// 24
		int s9 = s8 - Slope;// 22
		int s10 = s9 - Slope;// 20
		int s11 = s10 - Slope;// 18
		int s12 = s11 - Slope;// 16
		int s13 = s12 - Slope;// 14
		int s14 = s13 - Slope;// 12
		int s15 = s14 - Slope;// 10
		int s16 = s15 - Slope;// 8
		int s17 = s16 - Slope;// 6
		int s18 = s17 - Slope;// 4
		int s19 = s18 - Slope;// 2

		if (CurrentStamina == MaxStamina || CurrentStamina > MaxStamina) {
			screen.renderSprite(x, y, Sprite.StaminaBar20, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s1)) {
			screen.renderSprite(x, y, Sprite.StaminaBar19, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s2) && (CurrentStamina < s1)) {
			screen.renderSprite(x, y, Sprite.StaminaBar18, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s3) && (CurrentStamina < s2)) {
			screen.renderSprite(x, y, Sprite.StaminaBar17, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s4) && (CurrentStamina < s3)) {
			screen.renderSprite(x, y, Sprite.StaminaBar16, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s5) && (CurrentStamina < s4)) {
			screen.renderSprite(x, y, Sprite.StaminaBar15, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s6) && (CurrentStamina < s5)) {
			screen.renderSprite(x, y, Sprite.StaminaBar14, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s7) && (CurrentStamina < s6)) {
			screen.renderSprite(x, y, Sprite.StaminaBar13, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s8) && (CurrentStamina < s7)) {
			screen.renderSprite(x, y, Sprite.StaminaBar12, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s9) && (CurrentStamina < s8)) {
			screen.renderSprite(x, y, Sprite.StaminaBar11, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s10) && (CurrentStamina < s9)) {
			screen.renderSprite(x, y, Sprite.StaminaBar10, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s11) && (CurrentStamina < s10)) {
			screen.renderSprite(x, y, Sprite.StaminaBar9, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s12) && (CurrentStamina < s11)) {
			screen.renderSprite(x, y, Sprite.StaminaBar8, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s13) && (CurrentStamina < s12)) {
			screen.renderSprite(x, y, Sprite.StaminaBar7, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s14) && (CurrentStamina < s13)) {
			screen.renderSprite(x, y, Sprite.StaminaBar6, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s15) && (CurrentStamina < s14)) {
			screen.renderSprite(x, y, Sprite.StaminaBar5, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s16) && (CurrentStamina < s15)) {
			screen.renderSprite(x, y, Sprite.StaminaBar4, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s17) && (CurrentStamina < s16)) {
			screen.renderSprite(x, y, Sprite.StaminaBar3, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s18) && (CurrentStamina < s17)) {
			screen.renderSprite(x, y, Sprite.StaminaBar2, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s19) && (CurrentStamina < s18)) {
			screen.renderSprite(x, y, Sprite.StaminaBar1, fixed);
		}
		if (CurrentStamina <= 0) {
			screen.renderSprite(x, y, Sprite.StaminaBar0, fixed);
		}
	}
}