package com.mcpekorea.peanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Copyright 2015 ChalkPE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PEAnalyzer {
	public static Pattern FUNCTION_LINE = Pattern.compile("([0-9a-f]{8}) (<.*>):");
	public static Pattern CONTENT_LINE = Pattern.compile(" {2}([0-9a-f]{6}):\t(.*?)\t(.*?)(?:\t; (.*))?" + System.lineSeparator());
	
	private File file;
	private File cacheDirectory;
	private boolean includeContent = false;
	
	private HashMap<UnsignedInteger, Line> lines;
	private boolean cached = false;
	private boolean isAnalyzing = false;

	public PEAnalyzer(String filePath, String cacheDirectoryPath, boolean includeContent) throws IOException {
		this(new File(filePath),new File(cacheDirectoryPath), includeContent);
	}
	
	public PEAnalyzer(String filePath, File cacheDirectory, boolean includeContent) throws IOException {
		this(new File(filePath), cacheDirectory, includeContent);
	}
	
	public PEAnalyzer(File file, String cacheDirectoryPath, boolean includeContent) throws IOException {
		this(file, new File(cacheDirectoryPath), includeContent);
	}
	
	public PEAnalyzer(File file, File cacheDirectory, boolean includeContent) throws IOException {
		this.file = file;
		this.cacheDirectory = cacheDirectory;
		this.includeContent = includeContent;
		this.lines = new HashMap<UnsignedInteger, Line>(1048576); //2^20
		
		if(!this.file.exists()){
			throw new IllegalArgumentException("file must be exists");
		}
		this.loadCache();
	}
	
	private void loadCache(){
		new Thread(){
			@Override
			public void run(){
				long startTime = System.currentTimeMillis();
				cached = cacheDirectory.exists();
				
				if(!cached){
					startAnalyze();
					return;
				}
				
				File[] subdirecties = cacheDirectory.listFiles(new FileFilter(){
					@Override
					public boolean accept(File file){
						return file.isDirectory();
					}
				});
				
				if(subdirecties.length == 0){
					startAnalyze();
					return;
				}
				
				for(File subdirectory : subdirecties){
					for(File file : subdirectory.listFiles()){
						load(file);
					}
				}
				System.out.println("Cached! time: " + (System.currentTimeMillis() - startTime));
			}
		}.start();
	}

	
	public void startAnalyze(){
		if(!this.isAnalyzing){
			this.cached = false;
			new Thread(){
				@Override
				public void run(){
					analyze();
				}
			}.start();
		}
	}
	
	private void analyze(){
		long startTime = System.currentTimeMillis();
		if(this.cached){
			return;
		}
		
		this.isAnalyzing = true;
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(this.file));
			while(true){
				String read = reader.readLine();
				if(read == null){
					break;
				}
				
				if(read.equals("")){
					continue;
				}
				
				Matcher m = CONTENT_LINE.matcher(read + System.lineSeparator());
				if(this.includeContent && m.matches()){
					this.put(new ContentLine(new UnsignedInteger(m.group(1)), m.group(2), m.group(3), m.groupCount() > 3 ? m.group(4) : ""));
				}else{
					m = FUNCTION_LINE.matcher(read);
					if(m.matches()){
						this.put(new FunctionLine(new UnsignedInteger(m.group(1)), m.group(2)));
					}
				}
			}
			this.cached = true;
			System.out.println("Analyzed! time: " + (System.currentTimeMillis() - startTime));
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			this.isAnalyzing = false;
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void put(Line line){
		this.lines.put(line.getOffset(), line);
		this.save(line);
	}
	
	public void save(Line line){
		File file = line.getOffset().getFile(cacheDirectory);
		ObjectOutputStream oos = null;
		
		if(file.exists()){
			return;
		}
		
		System.out.println("Saving " + line);
		
		try{
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(line);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(oos != null){
				try{
					oos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public Line get(UnsignedInteger offset){
		Line line = this.lines.get(offset);
		if(line == null){
			return load(offset);
		}else{
			return line;
		}
	}
	
	public Line load(UnsignedInteger offset){
		return this.load(offset.getFile(cacheDirectory));
	}
	
	public Line load(File file){
		ObjectInputStream ois = null;
		
		if(!file.exists()){
			startAnalyze();
			return null;
		}
		
		try{
			ois = new ObjectInputStream(new FileInputStream(file));
			
			Line line = (Line) ois.readObject();
			if(line instanceof FunctionLine || this.includeContent){
				this.put(line);
			}
			
			return line;
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}finally{
			if(ois != null){
				try{
					ois.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
