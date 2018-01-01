package com.IB.SL.level.worlds;

import org.w3c.dom.Element;

public class LevelExit {
	
	public String xml;
	public int tx, ty;
	public int xo, yo, w, h;
	
	public LevelExit(Element eElement) {
		xml = (eElement.getAttribute("to"));
		
		tx = Integer.parseInt(eElement.getAttribute("x"));
		ty = Integer.parseInt(eElement.getAttribute("y"));
		
		xo = Integer.parseInt(eElement.getAttribute("xo"));
		yo = Integer.parseInt(eElement.getAttribute("yo"));
		
		w = Integer.parseInt(eElement.getAttribute("w"));
		h = Integer.parseInt(eElement.getAttribute("h"));
	}
	
	public String toString() {
		return "A level exit to: " + this.xml + " at x: " + tx + " and y: " + ty;
	}
}