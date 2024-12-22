package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJaxb implements Dao{
	JaxbUnMarshaller unMarshaller = new JaxbUnMarshaller();
	JaxbMarshaller marshaller = new JaxbMarshaller();
	
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
		ArrayList<Product> productos = unMarshaller.init().getProducts();
		return productos;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		return marshaller.init(inventory);
	}

	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
