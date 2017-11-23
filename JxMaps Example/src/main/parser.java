package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.teamdev.jxmaps.LatLng;

public class parser {
	
	private List<LatLng> points;
	
	parser(String fileName) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Load the input XML document, parse it and return an instance of the
		// Document class.
		Document document = builder.parse(new File(fileName));

		points = new ArrayList<LatLng>();
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
		//		Element elem = (Element) node;
		
				// Get the value of the ID attribute.
				String lat = node.getAttributes().getNamedItem("lat").getNodeValue();
				String lon = node.getAttributes().getNamedItem("lon").getNodeValue();
				double dlat = Double.parseDouble(lat);
				double dlon = Double.parseDouble(lon);
				points.add(new LatLng(dlat,dlon));
			}
		}		
		
	}
	
	public List<LatLng> getListOfPoint(){
		return points;
	}

}
