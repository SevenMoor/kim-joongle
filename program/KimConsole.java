package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import source.IncorrectDirectoryException;
import source.SitemapGenerator;

public class KimConsole {
	public static void main(String[] args) {
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in)); //Giving the console a short name
		SitemapGenerator sitemap = new SitemapGenerator("http://northkorea.nk/");
		
		System.out.println("----------------------------------- Kim Joongle -----------------------------------");
		try {
			Thread.sleep(500);
			System.out.println("Collectivizing Data.....");
			Thread.sleep(2000);
			System.out.println("Deleting imperialistic Data....");
			Thread.sleep(3200);
			System.out.println("[OK] Purged Database!\n\n");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.print("Enter the Root of the Democratic Database you want to search in: ");
		try {
			String directory = console.readLine();
			sitemap.execute(directory);
			
		} catch (IOException e) {
			System.err.println("Data Corrupted by Capitalism!");
		} catch (IncorrectDirectoryException e) {
			System.err.println(e.getMessage());
		}
	}

}
