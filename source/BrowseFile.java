package source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BrowseFile {

	public BrowseFile() {
	}

	public void iterate(String currentDirectory, BufferedWriter writer) throws IOException {
		File currentFile = new File(currentDirectory);
		if (currentFile.isDirectory()) {
			String[] content = currentFile.list();
			for (int i = 0; i < content.length; i++) {
				String subDirectory = currentDirectory + "/" + content[i];
				File subFile = new File(subDirectory);
				if (subFile.isDirectory())
					iterate(subDirectory, writer);
				if (subFile.isFile()) {
					if (subDirectory.endsWith(".html")) {
						FileData file = new FileData(new File(subDirectory));
						file.readFile();
						file.save(writer);
					}
				}
			}
		}
	}

	public void execute(String currentDirectory) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("keywords.xml"));
		writer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\t<root>\n");
		iterate(currentDirectory, writer);
		writer.write("\t</root>");
		writer.close();
	}
}
