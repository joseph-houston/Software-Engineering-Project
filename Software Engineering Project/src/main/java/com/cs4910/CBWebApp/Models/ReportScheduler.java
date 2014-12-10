package com.cs4910.CBWebApp.Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportScheduler {
	public static void storeScheduleRecord(File file, String record) throws IOException {
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file, true); 
		writer.write(record + "\n"); 
		writer.flush();
		writer.close();			
	}
	
	public static List<String> readScheduleRecords(File file) throws IOException {	
		List<String> records = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			records.add(line);		}
		reader.close();	
		
		return records;
	}
}
