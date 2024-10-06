package dao;
import model.Product;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;

public interface Dao {
public void connect() throws SQLException ;
public void disconnect()throws SQLException ;
public Employee getEmployee(int userNumber, String password) ;
public ArrayList<Product> getInventory ();
public boolean writeInventory (ArrayList<Product> inventory);

}
 