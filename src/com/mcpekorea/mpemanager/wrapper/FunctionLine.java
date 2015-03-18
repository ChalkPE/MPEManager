package com.mcpekorea.mpemanager.wrapper;

import java.util.ArrayList;
import java.util.List;

public class FunctionLine extends OffsetLine {
	private static final long serialVersionUID = 5087202240516751185L;
	
	private final String function;
	private List<ContentLine> children;
	
	public FunctionLine(int lineNumber, String string, String offset, String function){
		super(lineNumber, string, offset);
		this.function = function;
		
		this.children = new ArrayList<ContentLine>();
	}
	
	public String getFunction(){
		return this.function;
	}
	
	public List<ContentLine> getChildren(){
		return children;
	}
	
	public void setChildren(List<ContentLine> children){
		this.children = children;
	}
	
	public boolean addChild(ContentLine child){
		return this.children.add(child);
	}
	
	public ContentLine removeChild(int childIndex){
		return this.children.remove(childIndex);
	}
	
	public boolean removeChild(ContentLine child){
		return this.children.remove(child);
	}
}