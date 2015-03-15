package com.mcpekorea.mpemanager.wrapper;

public class OffsetLine extends Line {
	private final String offset;
	
	public OffsetLine(String string, String offset){
		super(string);
		this.offset = offset;
	}
	
	public String getOffset(){
		return this.offset;
	}
	
	@Override
	public boolean hasData(){
		return true;
	}
}
