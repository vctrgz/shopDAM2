package dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.xml.DomWriter;
import dao.xml.SaxReader;
import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplXML implements Dao {

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int userNumber, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		ArrayList <Product>temporal = new ArrayList<Product>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			File file = new File ("xml/inputInventory.xml");
			SaxReader saxReader = new SaxReader();
			parser.parse(file, saxReader);
			temporal = saxReader.getProducts();
			
		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		} catch (IOException e) {
			System.out.println("ERROR file not found");
		}
		return temporal;
	}		

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		
		DomWriter domWriter = new DomWriter();
		domWriter.generateDocument(inventory);
		return domWriter.generateDocument(inventory);
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub
		
	}

}
