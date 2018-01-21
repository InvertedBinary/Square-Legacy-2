package com.IB.SL.graphics.UI.menu;

import java.util.ArrayList;
import java.util.Stack;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.graphics.UI.UI;
import com.IB.SL.graphics.UI.part.UI_Button;
import com.IB.SL.input.Keyboard;

public class UI_Menu {
	
	public Sprite bg;
	public SpriteSheet s_bg;
	public UI ui;
	public font8x8 font8x8 = new font8x8();
	public font font = new font();
	public int x;
	public int y;
	public boolean enabled = false;
	public static UI_Menu current;
	public static int index = 0;
	
	public static byte suspend = 0;
	public final static byte SUS_ALL = 2;
	public final static byte SUS_UPD = 1;
	public final static byte SUS_NON = 0;
	
	public static ArrayList<UI_Menu> history = new ArrayList<UI_Menu>();
	public static ArrayList<UI_Menu> menus = new ArrayList<UI_Menu>();

	public static MainMenu MainMenu;
	public static PauseMenu PauseMenu;
	
	public static ConsoleMenu ConsoleMenu;
	
	public static SettingsMenu settingsMenu;
	public static GameplayMenu gpMenu;
	public static AudioMenu audMenu;
	public static VideoMenu vidMenu;

	public void addMenus() {
		menus.add(settingsMenu = new SettingsMenu(0, 0, Sprite.pauseOptions));
		menus.add(MainMenu = new MainMenu(0, 0, Sprite.Title));
		menus.add(ConsoleMenu = new ConsoleMenu(0, 0, Sprite.bgFade));

		menus.add(PauseMenu = new PauseMenu(0, 0, Sprite.pauseMenu));

		current = MainMenu;
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		ui = new UI();
	}
	
	public void updateUnloaded() {
	}
	
	public void update() {
		if (current != null) {
			if (current.enabled) {
				current.update();
				if (current != null) {
				current.ui.update();
				}
			}
		}
	}
	
	public void render(Screen screen) {
		if (current != null) {
			if (current.enabled) {
			current.render(screen);
			current.ui.render(screen);
			}
		}
	}
	
	public void navUp() {
		UI_Menu menu;
		if (history.size() > 0) {
		menu = history.get(history.size() - 1);
		index = history.size() - 1;
		} else {
		menu = history.get(0);
		index = 0;
		}
		if (menu != null) {
		if (current != null) {
			unloadMenu(current);
		}
		current = menu; 
		current.enabled = true;
		
		for (int i = index; i < history.size(); i++) {
			history.remove(i);
		}
		}
	}
	
	public void load(UI_Menu menu, boolean override) {
		System.out.println("Attempting to load: " + menu);
		if (current == null || override == true) {
			if (menu != null) {
				if (current != null) {
					unload(current);
				}
				current = menu;
				current.enabled = true;
				current.onLoad();
			}
		}
	}
	

	public void unload(UI_Menu menu) {
		history.add(current);
		unloadMenu(menu);
	}
	
	private void unloadMenu(UI_Menu menu) {
		if (menu != null) {
		if (menu.enabled) {
		ArrayList<UI_Button> buttons = menu.ui.ui_Buttons;
		for (UI_Button btns : buttons) {
			btns.clicked = false;
			btns.hover = false;
		}
		current.onUnload();
		current = null;
		menu.enabled = false;
		
	}
  }
}
	public Keyboard getKey() {
		return Boot.get().key;
	}
	
	public void onLoad() {
		
	}
	
	public void onUnload() {
		
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getBG() {
		return bg;
	}
}