package com.IB.LE2.input.UI.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.asset.audio.Audio;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.asset.graphics.SpriteSheet;
import com.IB.LE2.input.Commands;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Container;
import com.IB.LE2.input.UI.components.basic.UI_Keylistener;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.hardware.Keyboard;

public abstract class UI_Menu implements KeyListener {
	
	public Sprite bg;
	public SpriteSheet s_bg;
	public UI_Container ui;
	public int x;
	public int y;
	public boolean enabled = false;
	public static int index = 0;
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		
		if (ui == null)
			ui = new UI_Container();
	}
	
	public void UpdateUnloaded() { }
	public void update() { }
	public void render(Screen screen) {	}
	
	public void continueGame() {
		//UI_Manager.UnloadCurrent();
		if (!Boot.get().getLevel().players.contains(Boot.get().getPlayer())) {
			Boot.get().getPlayer().removed = false;
			Boot.get().getLevel().add(Boot.get().getPlayer());
			//Boot.get().getLevel().loadLua();
		}
		
		Boot.getPlayer().ShowHUD();
	}
	
	public void SetVolume(float level) {
		System.out.println("Setting TO: " + level);
		Audio.SetVolume(level);
		
		//PlayPrevious();
	}
	
	public void PlayMusic(String name, String file) {
		Audio.PlayMusic(name, file, true);
	}
	
	public void PlayPrevious() {
		if (Audio.previous_music == null)
			Audio.StopMusic();
		else
			Audio.PlayMusic(
				Audio.previous_music.name, 
				Audio.previous_music.path
			);
	}
	
	public int progressBar(int total_frames, double max_val, double current_val) {
		double percent = current_val / max_val;
		int frame = (int)(percent * total_frames);
		
		return (total_frames - 1) - frame;
	}
	
	public void ShowMainMenu() {
		UI_Manager.Load(Game.MainMenu);
	}
	
	public void ShowConsole() {
		if (UI_Manager.ConsoleMenu == null) UI_Manager.ConsoleMenu = new TagMenu("CmdMenu");
		UI_Manager.Load(UI_Manager.ConsoleMenu);
	}
	
	public void RunCommand(String text) {
		Commands.Execute(text, Boot.get().getPlayer());
	}
	
	public String SelectVictimTile() {
		return Commands.SelectTile(Screen.xo, Screen.yo);
	}
	public void SelectVictims() {
		Commands.SelectVictims(-1, -1);
	}
	
	public void addUI(UI_Root component) {
		this.ui.addUI(component);
	}
	
	public void SetFocused(String id) {
		UI_Root element = GetElementById(id);
		if (element instanceof UI_Clickable) {
			UI_Manager.focused = (UI_Clickable)element;
			element.SetFocused(true);
		}
	}
	
	public UI_Root GetElementById(String id) {
		for (UI_Root e : ui.getAll()) {
			if (e.GetID().equals(id))
				return e;
		}
		
		return null;
	}
	
	public boolean ElementExists(String id) {
		return GetElementById(id) != null;
	}
	
	public String GetElementText(String id) {
		if (ElementExists(id))
			return GetElementById(id).GetText();
		else return "";
	}
	
	public void SetElementText(String id, String text) {
		if (ElementExists(id))
			GetElementById(id).SetText(text);
	}
	
	public void SetElementImage(String id, Sprite sprite) {
		if (ElementExists(id))
			GetElementById(id).SetImage(sprite);
	}
	
	public void SuspendWorldInput() {
		Boot.get().getPlayer().input.suspendInput();
	}
	
	public void ResumeWorldInput() {
		Boot.get().getPlayer().input.resumeInput();
	}
	
	public Keyboard getKey() {
		return Boot.get().key;
	}
	
	public void OnLoad() {
		
	}
	
	public void OnUnload() {
		
	}
	
	public Sprite getBG() {
		return bg;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (ui != null)
			for (UI_Keylistener element : ui.getFields()) {
				element.KeyPressed(e);
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("Key Released: " + e.getKeyChar());
	}
	
}
