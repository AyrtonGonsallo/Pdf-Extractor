package application;

import java.io.File;

public class PdfReader {
	private File file;
	
	 public void setFile(File file) {
		this.file = file;
	}

	public String getResults(){
		return file.getAbsolutePath();
	}
}
