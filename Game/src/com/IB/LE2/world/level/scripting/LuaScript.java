package com.IB.LE2.world.level.scripting;

import java.net.URL;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.UI_Manager;

public class LuaScript implements Runnable {

	private final String path;
	private final Globals globals;
	private final URL script;
	private final LuaValue chunk;
	
	private boolean should_close = false;
	
	private Thread thread;
	
	public LuaScript(String path) {
		this.path = path;
		this.script = LuaScript.class.getResource(path);
		if (script == null) {
			Boot.log("Missing script at: " + path, "LuaScript.java", true);
		}
		this.globals = JsePlatform.standardGlobals();
		chunk = globals.loadfile(path);
	}
	
	//public static String TAGMOB = "com.IB.LE2.world.entity.mob.TagMob";
	public void AddGeneralGlobals() {
		addGlobal("level", Boot.getLevel());
		addGlobal("g", Boot.get());
		addGlobal("GAME", Game.class);
		addGlobal("menu", UI_Manager.Current());

		addGlobal(" ", this.getClass());
//		addGlobal("level", Boot.get().getLevel());
//		addGlobal("g", Boot.get());
		//addGlobal("key", Boot.get().getInput());
		//addGlobal("key", Boot.get()); <= Crashes lua when used
	}
	
	public boolean call(String function) {
		if (should_close)
			return false;
		
        LuaValue func = globals.get(function);
        if (!func.isnil()) {
        	func.call();
        	return true;
        }
        
        return false;
	}
	
	public boolean call(String function, Object... arg) {
		if (should_close)
			return false;
		
        LuaValue func = globals.get(function);
        if (!func.isnil()) {
        	
        	if (arg.length == 1)
        		func.call(CoerceJavaToLua.coerce(arg[0]));
        	
        	if (arg.length == 2)
            	func.call(CoerceJavaToLua.coerce(arg[0]), CoerceJavaToLua.coerce(arg[1]));

        	if (arg.length >= 3) 
            	func.call(CoerceJavaToLua.coerce(arg[0]), CoerceJavaToLua.coerce(arg[1]), CoerceJavaToLua.coerce(arg[2]));

        	if (arg.length > 3) {
        		Boot.log("Three is the maximum argument count of LuaJ functions. Additional arguments are ignored!", "LuaScript.java", true);
        	}
        	
        	return true;
        }
        
        return false;
	}
	
	public void addGlobal(String name, Object obj) {
		globals.set(name, CoerceJavaToLua.coerce(obj));
	}
	
	public void run() {
		if (script != null) {
			chunk.call();
		}
	}
	
	private void TerminateGracefully() {
		this.should_close = true;
	}

	public void Terminate() {
		try {
			if (thread != null)
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void SetThread(Thread t) {
		this.thread = t;
	}
}
