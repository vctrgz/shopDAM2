package dao;

import model.Product;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Amount;
import model.Employee;

public interface Dao {
	public void connect() throws SQLException;

	public void disconnect() throws SQLException;

	public Employee getEmployee(int userNumber, String password);

	public ArrayList<Product> getInventory();

	public boolean writeInventory(ArrayList<Product> inventory);

	public void addProduct(Product product);

	public void addStock(String name, int stock);

	public void deleteProduct(String name);

}
