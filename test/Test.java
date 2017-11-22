package test;
import java.io.IOException;

import source.*;

public class Test {
	public static void main(String[] args) {
		SitemapGenerator sitemap = new SitemapGenerator("http://northkorea.nk");
		FileData file = new FileData(new File("sitemap.xml"));
		try {
			sitemap.execute("/home/sevenmoor/promenade_fr.d");
			file.readFile("/home/sevenmoor/promenade_fr.d");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
