package com.mcpekorea.mpemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.mcpekorea.mpemanager.wrapper.Line;
import com.mcpekorea.mpemanager.wrapper.OffsetLine;

public class MPEManager {
	private File rootDirectory;
	
	public MPEManager(File rootDirectory){
		this.rootDirectory = new File(rootDirectory, "MPEManager");
		this.rootDirectory.mkdir();
	}
	
	public void parseMPE(File mpeFile){
		try{
			MPEReader reader = new MPEReader(new FileReader(mpeFile));
			Line line;
			while((line = reader.readLine()) != null){
				if(line instanceof OffsetLine){
					File file = new File(this.rootDirectory, ((OffsetLine) line).getOffset());
					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(line);
					oos.close();
				}
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}