package com.mcpekorea.mpemanager;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mcpekorea.mpemanager.wrapper.ContentLine;
import com.mcpekorea.mpemanager.wrapper.FunctionLine;
import com.mcpekorea.mpemanager.wrapper.Line;
import com.mcpekorea.mpemanager.wrapper.Offset;

public class MPEReader {
	public static Pattern FUNCTION_LINE = Pattern.compile("([0-9a-f]{8}) (<.*>):");
	public static Pattern CONTENT_LINE = Pattern.compile(" {2}([0-9a-f]{6}):\t(.*?)\t(.*?)(?:\t; (.*))?$", Pattern.MULTILINE);
	
	private LineNumberReader reader;
	
	public MPEReader(FileReader reader){
		this.reader = new LineNumberReader(reader);
	}
	
	public Line readLine() throws IOException {
		Matcher m = null;
		
		String string = reader.readLine();
		if(string == null){
			return null;
		}
		if(string.trim().length() == 0){
			return new Line("");
		}
		m = CONTENT_LINE.matcher(string);
		if(m.matches()){
			return new ContentLine(string, new Offset(m.group(1)), m.group(2), m.group(3), m.groupCount() > 3 ? m.group(4) : null);
		}
		m = FUNCTION_LINE.matcher(string);
		if(m.matches()){
			return new FunctionLine(string, new Offset(m.group(1)), m.group(2));
		}
		
		return new Line(string);
	}
}
