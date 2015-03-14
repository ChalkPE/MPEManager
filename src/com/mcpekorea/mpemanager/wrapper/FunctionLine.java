package com.mcpekorea.mpemanager.wrapper;

public class FunctionLine extends OffsetLine {
	private final String function;
	
	public FunctionLine(String string, Offset offset, String function){
		super(string, offset);
		this.function = function;
	}
	
	public String getFunction(){
		return this.function;
	}
}