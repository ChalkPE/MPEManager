package com.mcpekorea.peanalyzer;

public class UnsignedInteger {
	private int unsignedInteger;

	public UnsignedInteger(UnsignedInteger UnsignedInteger){
		this.unsignedInteger = UnsignedInteger.getUnsignedInteger();
	}
	
	public UnsignedInteger(int singedNumber){
		this.unsignedInteger = singedNumber - 1 - Integer.MAX_VALUE;
	}
	
	public UnsignedInteger(Integer integer){
		this(integer.intValue());
	}
	
	public UnsignedInteger(long singedNumber){
		this((int) (singedNumber & 0xFFFFFFFF));
	}
	
	public UnsignedInteger(String singedNumber, int radix){
		this(Long.parseLong(singedNumber, radix));
	}
	
	public UnsignedInteger(String singedNumber){
		this(singedNumber, 16);
	}
	
	public int getUnsignedInteger(){
		return unsignedInteger;
	}
	
	public long getSignedInteger(){
		return ((long) unsignedInteger) + 1 + Integer.MAX_VALUE;
	}
	
	@Override
	public String toString(){
		return String.format("%08x", getSignedInteger());
	}
	
	@Override
	public int hashCode(){
		return unsignedInteger >>> 6;
	}
}