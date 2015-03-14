package com.mcpekorea.mpemanager.wrapper;

public class Line {
	private final String string;
	
	public Line(String string){
		this.string = string;
	}

	public String getString() {
		return this.string;
	}
	
	public boolean hasData(){
		return false;
	}
}
