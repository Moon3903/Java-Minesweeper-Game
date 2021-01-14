package com.zetcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HighScore extends StreamBasic implements InterfaceHighScore {
	
	private Path path;
	private Path file;
	private Path pathFile;
	private String pathString;
	
	public HighScore(String name) {
		file = Paths.get(name);
		path = Paths.get("src/resources/highscore");
		pathString = "src/resources/highscore/"+name;
		pathFile = checkFiles(path,file);
	}
	
	public void compareHS(int baru) {
		int tmp = Integer.parseInt(readFile());
		String ok = new String();
		if(tmp>baru) {
			ok = Integer.toString(baru);
			try {
				writeFile(ok);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(tmp==-1) {
			ok = Integer.toString(baru);
			try {
				writeFile(ok);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getHS() {
		
		int tmp = Integer.parseInt(readFile());
		String hs = new String();
		hs="                                                                                      highscore: ";
		int second,minute;
		if (tmp==-1) {
			hs=hs+"--:--";
			return hs;
		}
		else {
			second = tmp%60;
			minute = tmp/60;
			if(minute < 10) {
				hs = hs+'0'+Integer.toString(minute)+':';
			}
			else {
				hs = hs+Integer.toString(minute)+':';
			}
			if(second < 10) {
				hs = hs+'0'+Integer.toString(second);
			}
			else {
				hs = hs+Integer.toString(second);
			}
		}
		return hs;
	}
	
	public Path checkFiles(Path path, Path file) {
		Path absPath = path.resolve(file);
		try {
			if(Files.notExists(path)) {
				System.out.println("creating path");
				Files.createDirectories(path);
			}
			if(Files.notExists(absPath)) {
				System.out.println("creating file");
				Files.createFile(absPath);
				System.out.println("created");
				this.writeFile("-1");
			}
		}
		catch (IOException x) {
			System.err.println(x);
			return null;
		}
		return absPath;
	}
	
	//overriding
	public String readFile() {
		try(BufferedReader br = new BufferedReader(new FileReader(pathFile.toString()))){
			String line = br.readLine();
			return line;
		}//end try
		catch (IOException e) {
			System.err.println(e);
		}//end catch
		return null;
	}
	//overriding
	public void writeFile(String str) throws IOException{
		super.writeFile(pathString, str);
	}
}
