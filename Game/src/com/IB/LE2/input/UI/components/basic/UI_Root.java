package com.IB.LE2.input.UI.components.basic;

import com.IB.LE2.Boot;
import com.IB.LE2.input.Mouse;
import com.IB.LE2.media.graphics.Screen;

public abstract class UI_Root {

	public String id = "-1";
	public String text = "";
	
	public boolean enabled = true;
	
	public abstract void update();
	public abstract void render(Screen screen);
	
	public void unload() { }
	
	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}
	
	public void SetID(String ID) {
		this.id = ID;
	}
	
	public String GetID() {
		return id;
	}
	
	public String GetText() {
		return text;
	}
	
	public void SetText(String text) {
		this.text = text;
	}
	
	public boolean checkBounds(int x, int y, int width, int height) {
		int scale = Boot.scale;
		x *= scale;
		y *= scale;
		width *= scale;
		height *= scale;

		if (Mouse.getX() < x + width && Mouse.getX() > x && Mouse.getY() < y + height && Mouse.getY() > y) {
			return true;
		} else {
			return false;
		}
	}
}