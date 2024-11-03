package dao.xml;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Result;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Product;

public class DomWriter {
	
	private Document document;
	
	
	public DomWriter() {
	}	
	
	public boolean generateDocument(ArrayList <Product> inventory) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			
			//PARENT NODE
			//root node
			Element products = document.createElement("products");
			document.appendChild(products);
			products.setAttribute("total", Integer.toString(inventory.size()));
			Element product;
			Element price;
			Element stock;
			Element name;
			
			for (Product producto : inventory) {
				if (producto != null) {
					product = document.createElement("product");
					product.setAttribute("id", Integer.toString(inventory.indexOf(producto)));
					products.appendChild(product);
					
					name = document.createElement("name");
					name.setTextContent(producto.getName());
					product.appendChild(name);
					
					price = document.createElement("price");
					price.setAttribute("currency", "Euro");
					price.setTextContent(producto.getWholesalerPrice().toString());
					product.appendChild(price);
					
					stock = document.createElement("stock");
					stock.setTextContent(Integer.toString(producto.getStock()));
					product.appendChild(stock);
				}
			}
			
			return generateXml();
			
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
			return false;
		}
	}
	
	private boolean generateXml() {
		boolean success = false;
		try {			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(document);
			LocalDate fecha = LocalDate.now();
			String rutaCarpeta = System.getProperty("user.dir") + File.separator + "xml";
			String rutaArchivo = rutaCarpeta + File.separator + "inventory_" + fecha.toString() + ".xml";
			File file = new File(rutaArchivo);
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);
			
			transformer.transform(source, result);
			success = true;
		} catch (IOException e) {
			System.out.println("Error when creating writter file");
		} catch (TransformerException e) {
			System.out.println("Error transforming the document");
		}
		return success;
	}
}
