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


public class SearchMatcher extends FileData {
	private ArrayList<String> location = new ArrayList<String>();

	public SearchMatcher(File currentFile) {
		super(currentFile);

	}

	public void browseFile(String searchWord, String fileName) throws IOException, ClassNotFoundException {

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
				if (fileElement.getTextContent().contains(searchWord.toLowerCase())) {
					//System.out.println(fileElement.getElementsByTagName("location").item(0).getTextContent());
					location.add(fileElement.getElementsByTagName("location").item(0).getTextContent()); 
				}
			}
			for(String loc : location) System.out.println(loc); 
			
			
		}catch(ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		
	}
	
}
