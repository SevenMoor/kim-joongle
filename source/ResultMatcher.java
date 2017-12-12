package Source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultMatcher extends FileData {
	private ArrayList<String> location = new ArrayList<String>();

	public ResultMatcher(File currentFile) {
		super(currentFile);

	}

	public void browseFile(String searchWord, String fileName) throws IOException, ClassNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;

		BufferedWriter writer = new BufferedWriter(new FileWriter("test.xml"));
		while ((line = reader.readLine()) != null) {
			// If we encounter a h1 or title tag...
			Pattern title = Pattern.compile("<file>(.*?)</file>", Pattern.CASE_INSENSITIVE);
			Matcher matcher = title.matcher(line);
			while (matcher.find()) {
				if (line.contains(searchWord)) {
					String codeGroup = matcher.group(1);
					if (codeGroup != null) {
						
						location.add(codeGroup);

					}
					for (String loc : location) {

						System.out.println(loc);
						writer.write(loc + "\n");

					}

				}

			}

		}
		reader.close();
		writer.close();
	}

	public String toString() {
		String result = "Result [location=" + location + "]";
		for (String loc : location) {
			result += "\n\t->" + loc;
		}
		return result;
	}
}
