package source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class SearchMatcher {
	private ArrayList<String> matches = new ArrayList<String>();
	private ArrayList<String> searchWords = new ArrayList<String>();

	public SearchMatcher(){}
	
	public void splitSearchWords(String search){
		String[] temporary = search.split(" ");
		for(String word : temporary) {
			searchWords.add(word);
		}
	}
	
	public void clearSearchWords() {
		searchWords.clear();
		matches.clear();
	}

	public void matchKeywords(String fileName) throws IOException, ClassNotFoundException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
		try {
			DocumentBuilder dBuilder = builderFactory.newDocumentBuilder(); 
			Document document = dBuilder.parse(fileName);
			document.normalize();
			NodeList rootNodeList= document.getElementsByTagName("root");
			Node rootNode = rootNodeList.item(0); 
			Element rootElement = (Element) rootNode;
			NodeList fileNodeList = rootElement.getElementsByTagName("file"); 
			for (int temp = 0; temp < fileNodeList.getLength(); temp++) {
				Node fileNode = fileNodeList.item(temp);
				Element fileElement = (Element) fileNode;
				for(String word : searchWords) {
					if ((fileElement.getTextContent().contains(word.toLowerCase()))&&!(matches.contains(fileElement.getElementsByTagName("location").item(0).getTextContent()))){
						matches.add(fileElement.getElementsByTagName("location").item(0).getTextContent()); 
					}
				}
			}		
		}catch(ParserConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (SAXException e) {
			System.err.println(e.getMessage());
		}	
	}
	
	public ArrayList<String> getMatches(){
		return matches;
	}
	
}
