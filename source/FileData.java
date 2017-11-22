package Source;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileData {
	private File file; 
	private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<String> keywords = new ArrayList<String>();
	private String location; 
	
	public FileData(File currentFile) {
		super();
		this.file = currentFile;
		
		
	}
	
	public void readFile(String fileName) throws IOException{
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		 
		  StringBuffer stringBuffer = new StringBuffer();
		  String line = null;
		 
		  while((line = bufferedReader.readLine())!=null){
		 
			  Pattern p = Pattern.compile("<title>(\\W+)</title>");
			  Matcher m = p.matcher(stringBuffer);
			  if (m.find()) {
				  String codeGroup = m.group(1);
				  System.out.format(codeGroup);
			  }
		  }
		   
		  System.out.println(stringBuffer);
	}
     
	

	public File getFile() {
		return file;
	}

	public String getDate() {
		return date.format(file.lastModified());
	}


	public ArrayList<String> getKeywords() {
		return keywords;
	}


	public String getVocation() {
		return location;
	}

	@Override
	public String toString() {
		return "FileData [file=" + file + ", date=" + date + ", keywords=" + keywords + ", location=" + location + "]";
	}

	
	
	
	
	
	

}
