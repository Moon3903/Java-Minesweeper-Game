package com.zetcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;

public class StreamBasic {

	public String readEntry() {
		try {
			StringBuffer buffer = new StringBuffer();
			
			int tmp;
			tmp = System.in.read();
			
			while (tmp != '\n' && tmp != -1) {
				buffer.append((char)tmp);
				tmp = System.in.read();
			}
			return buffer.toString().trim();
		}
		catch (IOException e) {
			return null;
		}
	}
	
	public String readLine() {
		String line = "";
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(isr);
		try {
			line = in.readLine();
		}
		catch (IOException e) {
			System.err.println(e);
		}
		return line;
	}
	
	public String readFile(Path file) {
		try(BufferedReader br = new BufferedReader(new FileReader(file.toString()))){
			StringBuilder fileContents = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				fileContents.append(line);
				fileContents.append(System.lineSeparator());
				line = br.readLine();
			}//end while
			return fileContents.toString();
		}//end try
		catch (IOException e) {
			System.err.println(e);
		}//end catch
		return null;
	}
	
	public void writeFile(String file,String str) throws IOException{
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		System.out.println("writing "+str+" to "+file);
		writer.println(str);
		writer.close();
	}
}

