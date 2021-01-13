package com.zetcode;

import java.io.IOException;
import java.nio.file.Path;

public interface InterfaceHighScore {
	public void compareHS(int baru);
	public String getHS();
	public Path checkFiles(Path path, Path file);
	public String readFile();
	public void writeFile(String str) throws IOException;
}
