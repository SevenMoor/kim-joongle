package source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
		this.location = currentFile.getAbsolutePath();
		this.file = currentFile;
	}

	public void readFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			
			//If we encounter a h1 or title tag...
			Pattern title = Pattern.compile("(<h1>(.*?)</h1>|<title>(.*?)</title>)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = title.matcher(line);
			
			while (matcher.find()) {
				//First process the h1 matches
				String codeGroup = matcher.group(2);
				if (codeGroup != null) {
					char[] weed = codeGroup.toCharArray();
					String weededGroup = new String();
					int inTag = 0;
					
					for(int i=0;i<weed.length;i++){
						if(weed[i]=="<"&&inTag==0) inTag = 1;
						if(weed[i]==">"&&inTag==1) inTag = 0;
						
						if(inTag==0) weededGroup += weed[i];
					}
					
					String[] keys = weededGroup.split(" |&nbsp;|:|\"|-|\\(|\\)|\\?|!|\\.|\\n|\\t|;|,|\'"); //Split according to these characters
					for (String key : keys)
						keywords.add(key);
				}
				
				//Then the title matches
				codeGroup = matcher.group(3);
				if (codeGroup != null) {
					char[] weed = codeGroup.toCharArray();
					String weededGroup = new String();
					int inTag = 0;
					
					for(int i=0;i<weed.length;i++){
						if(weed[i]=='<'&&inTag==0) inTag = 1;
						if(weed[i]=='>'&&inTag==1) inTag = 0;
						
						if(inTag==0) weededGroup += weed[i];
					}
					
					String[] keys = weededGroup.split(" |&nbsp;|:|\"|-|\\(|\\)|\\?|!|\\.|\\n|\\t|;|,|\'");
					for (String key : keys)
						keywords.add(key);
				}
				
				//And remove empty entries
				String empty = new String();
				for (int i = 0; i < keywords.size(); i++) {
					if (keywords.get(i).equals(empty)){
						keywords.remove(i);
						i--;
					}
				}

			}
		}
		reader.close();
	}

	public String getDate() {
		return date.format(file.lastModified());
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public String getLocation() {
		return location;
	}

	public void save(BufferedWriter writer) throws IOException{
		writer.write("<file location=\""+location+"\">\n");
		for(String keyword : keywords){
			writer.write("\t<keyword>"+keyword+"</keyword>\n");
		}
		writer.write("</file>");
	}
	
	@Override
	public String toString() {
		String result = "Result [date=" + date + ", location=" + location + "]";
		for(String keyword : keywords){
			result+="\n\t->"+keyword;
		}
		return result;
	}
}
