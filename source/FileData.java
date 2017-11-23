package source;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileData{
	
	private File file; 
	private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<String> keywords = new ArrayList<String>();
	private String location; 
	
	public FileData(File currentFile){
		super();
		this.location = currentFile.getAbsolutePath();
		this.file = currentFile;	
	}
	public void readFile(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		while((line = reader.readLine().toLowerCase())!=null){
			Pattern title = Pattern.compile("(<title>(\\W+)</title>|<h1>(\\W+)</h1>)");
			Matcher matcher = title.matcher(new StringBuffer());
			if (matcher.find()) {
				for(int i=1;i<=matcher.groupCount();i++){  
					String codeGroup = matcher.group(i);
					String[] keys = codeGroup.split(" ");
					for(String key : keys){
						keywords.add(key);
					}
				}
			}
		}
		reader.close();
	}

	public String getDate(){
		return date.format(file.lastModified());
	}
	public ArrayList<String> getKeywords(){
		return keywords;
	}
	public String getLocation(){
		return location;
	}

	@Override
	public String toString() {
		return "FileData[date="+date+",keywords="+keywords+", location="+location +"]";
	}
}
