package source;

import java.io.*;
import java.text.SimpleDateFormat;

public class SitemapGenerator {

	private String root;

	public SitemapGenerator(String root) {
		this.root = root;
	}

	public void iterate(String currentDirectory, BufferedWriter writer) throws IOException {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		File currentFile = new File(currentDirectory);

		/*
		 * if the current file is a directory, we list its content, iterate through it,
		 * and for each child element, we check if it is a directory or a file. If it's
		 * the latter we add an entry to the sitemap, else go trough this function one
		 * level deeper.
		 */
		if (currentFile.isDirectory()) {
			String[] content = currentFile.list();
			for (int i = 0; i < content.length; i++) {
				String subDirectory = currentDirectory + "/" + content[i];
				File subFile = new File(subDirectory);
				if (subFile.isDirectory())
					iterate(subDirectory, writer);
				else {
					if (subDirectory.endsWith(".html")) {
						writer.write("\t\t<url>\n\t\t\t<loc>" + root + subDirectory + "</loc>\n\t\t\t<lastmod>"
								+ date.format(subFile.lastModified())
								+ "</lastmod>\n\t\t\t<priority>0.5</priority>\n\t\t</url>");
						writer.newLine();
						System.out.println("Entry for: " + subDirectory);
					}
				}
			}
		}
	}

	public void execute(String currentDirectory) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("sitemap.xml"));
		writer.write(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\t<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
		writer.newLine();
		iterate(currentDirectory, writer);
		writer.write("\t</urlset>");
		writer.close();
	}

}
