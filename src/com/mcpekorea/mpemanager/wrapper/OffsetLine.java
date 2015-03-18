package com.mcpekorea.mpemanager.wrapper;

public class OffsetLine extends Line {
	private static final long serialVersionUID = 8914269129705581523L;
	
	private final String offset;
	
	public OffsetLine(int lineNumber, String string, String offset){
		super(lineNumber, string);
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
