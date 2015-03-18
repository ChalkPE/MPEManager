package com.mcpekorea.mpemanager.wrapper;

public class ContentLine extends OffsetLine {
	private static final long serialVersionUID = 9085780861882827185L;
	
	private final String hex;
	private final String code;
	private final String comment;
	
	private FunctionLine parent;
	
	public ContentLine(int lineNumber, String string, String offset, String hex, String code, String comment) {
		super(lineNumber, string, offset);
		this.hex = hex;
		this.code = code;
		this.comment = comment == null ? "" : comment;
		
		this.parent = null;
	}
	
	public String getHex(){
		return this.hex;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	public FunctionLine getParent(){
		return parent;
	}
	
	public void setParent(FunctionLine parent){
		this.parent = parent;
	}
}
