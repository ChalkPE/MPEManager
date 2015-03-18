package com.mcpekorea.mpemanager.wrapper;

import java.io.Serializable;

public class Line implements Serializable {
	private static final long serialVersionUID = 5376678343463988300L;
	
	private final int lineNumber;
	private final String string;
	
	public Line(int lineNumber, String string){
		this.lineNumber = lineNumber;
		this.string = string;
	}

	public String getString() {
		return this.string;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public boolean hasData(){
		return false;
	}
}
