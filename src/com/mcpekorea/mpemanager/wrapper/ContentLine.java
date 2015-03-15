package com.mcpekorea.mpemanager.wrapper;

public class ContentLine extends OffsetLine {
	private final String hex;
	private final String code;
	private final String comment;
	
	private FunctionLine parent;
	
	public ContentLine(String string, String offset, String hex, String code, String comment) {
		super(string, offset);
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
