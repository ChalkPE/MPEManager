package com.mcpekorea.mpemanager.wrapper;

public class OffsetLine extends Line implements Comparable<OffsetLine> {
	private final Offset offset;
	
	public OffsetLine(String string, Offset offset){
		super(string);
		this.offset = offset;
	}
	
	public Offset getOffset(){
		return this.offset;
	}
	
	@Override
	public boolean hasData(){
		return true;
	}
	
	@Override
	public int compareTo(OffsetLine another){
		return this.offset.compareTo(another.getOffset());
	}
}
