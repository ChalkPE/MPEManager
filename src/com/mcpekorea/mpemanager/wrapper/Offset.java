package com.mcpekorea.mpemanager.wrapper;

public class Offset implements Comparable<Offset> {
	private final long offset;
	
	public Offset(Offset another){
		this(another.getOffset());
	}
	
	public Offset(String offset){
		this(Long.parseLong(offset, 16));
	}
	
	public Offset(long offset){
		this.offset = offset;
	}
	
	public long getOffset(){
		return this.offset;
	}
	
	@Override
	public String toString(){
		return String.format("%08d", this.offset);
	}
	
	@Override
	public int compareTo(Offset another){
		return Long.compare(this.offset, another.getOffset());
	}
}