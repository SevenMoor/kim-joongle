package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import exception.IncorrectDirectoryException;

import source.BrowseFile;
import source.SitemapGenerator;

public class KimConsole {
	public static void main(String[] args){
		if(args.length==0){
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in)); //Giving the console a short name
			SitemapGenerator sitemap = new SitemapGenerator("http://northkorea.nk/");
			BrowseFile fileBrowser = new BrowseFile();
			
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
				fileBrowser.execute(directory);
				//System.out.println("Search Kim-Joongle: ");
			} catch (IOException e) {
				System.err.println("Data Corrupted by Capitalism!");
			} catch (IncorrectDirectoryException e) {
				System.err.println(e.getMessage());
			}
		}
		else if(args[0].equals("-d")){
				if(args[2].equals("-c")&&args[3].equals("sitemap")){
					System.out.println("Lol!");
					SitemapGenerator sitemap = new SitemapGenerator("http://northkorea.nk/");
					try {
						sitemap.execute(args[1]);
						System.out.println("Successfully created Capitalism-free Sitemap!");
					} catch (IOException e) {
						System.err.println("Data Corrupted by Capitalism!");
					} catch (IncorrectDirectoryException e) {
						System.err.println(e.getMessage());
					}
				}
				else if(args[2].equals("-c")&&args[3].equals("keywords")){
					BrowseFile fileBrowser = new BrowseFile();
					try {
						fileBrowser.execute(args[1]);
						System.out.println("Successfully created Capitalism-free Keywords List!");
					} catch (IOException e) {
						System.err.println("Data Corrupted by Capitalism!");
					} catch (IncorrectDirectoryException e) {
						System.err.println(e.getMessage());
					}
				}
				else if(args[2].equals("-s")){
					String search="";
					for(int i =0; i<args.length-3;i++){
						search+=args[i+3]+" ";
					}
					if(args.length==3) System.err.println("You look to have censored yourself... You didn't search for anything!");
					else System.err.println("Sorry! The government doesn't allow to search for "+search+"yet!");
				}
		}
	}

}
