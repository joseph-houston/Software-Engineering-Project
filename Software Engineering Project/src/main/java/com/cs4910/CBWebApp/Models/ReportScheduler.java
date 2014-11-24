package com.cs4910.CBWebApp.Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReportScheduler {
	public static void storeScheduleRecord(String path, String record) throws IOException {
		File file = new File(path);
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file, true); 
		writer.write(record + "\n"); 
		writer.flush();
		writer.close();			
	}
	
	public static void readScheduleRecords(String path) throws IOException {
		/*FileReader fr = new FileReader(path); 
		char [] a = new char[50];
		fr.read(a);
		for(char c : a)
			System.out.print(c);
		fr.close();	
		*/
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		 
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	 
		reader.close();		
	}
}
