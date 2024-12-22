package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao{
	private Connection connection;
	
	public void connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		this.connection = DriverManager.getConnection(url, user, pass);
	}
	public void disconnect() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	public Employee getEmployee(int userNumber, String password) {
		Employee empleado = null;
		String query = "select * from employee where userNumber = ? and password = ? ";
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			ps.setInt(1,userNumber);
			ps.setString(2,password);
		  	
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		empleado =  new Employee(rs.getInt(1), rs.getInt(2), rs.getString(3)); 
	        	}
	        }
	    }  catch (SQLException e) {
			// TODO: handle exception
		}
		
		return empleado; 
	}
	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub		
		ArrayList<Product> products = new ArrayList<>();
		String query = "select * from inventory";
		if (this.connection == null) {
            throw new IllegalStateException("Database connection is not established. Call connect() first.");
        }
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			
	        try (ResultSet rs = ps.executeQuery()) {
	        	while (rs.next()) {
	        		Product product = new Product(rs.getString("name"), rs.getFloat("wholesalerPrice"), rs.getBoolean("available"), rs.getInt("stock"));
	        		products.add(product);
	        	}
	        }
	    }  catch (SQLException e) {
			// TODO: handle exception
		}
		
		return products; 
	}
	@Override
	public boolean writeInventory(ArrayList <Product> inventory) {
		// TODO Auto-generated method stub
		String query = "insert into historical_inventory (id_product, name, wholesalerPrice, available, stock, created_at) values"
				+ "(?, ?, ?, ?, ?, ?);";
		if (this.connection == null) {
            throw new IllegalStateException("Database connection is not established. Call connect() first.");
        }
		try (PreparedStatement ps = connection.prepareStatement(query)) { 	
        	for (Product product : inventory) {
        		 ps.setInt(1, product.getId());
                 ps.setString(2, product.getName());
                 ps.setDouble(3, product.getWholesalerPrice().getValor());
                 ps.setBoolean(4, product.isAvailable());
                 ps.setInt(5, product.getStock());
                 ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                 int results = ps.executeUpdate(); 
			}

            return true;
	    }  catch (SQLException e) {
			// TODO: handle exception
	    	e.printStackTrace();
	    	return false;
		}
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		String query = "insert into inventory (id, name, wholesalerPrice, available, stock) values"
				+ "(?, ?, ?, ?, ?);";
		if (this.connection == null) {
            throw new IllegalStateException("Database connection is not established. Call connect() first.");
        }
		try (PreparedStatement ps = connection.prepareStatement(query)) { 	
			ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getWholesalerPrice().getValor());
            ps.setBoolean(4, product.isAvailable());
            ps.setInt(5, product.getStock());
            int results = ps.executeUpdate(); 
	    }  catch (SQLException e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	}
	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		String query = "update inventory set stock = stock + ? where name = ?";
		if (this.connection == null) {
            throw new IllegalStateException("Database connection is not established. Call connect() first.");
        }
		try (PreparedStatement ps = connection.prepareStatement(query)) { 	
        	ps.setInt(1, stock);
        	ps.setString(2, name);
        	int results = ps.executeUpdate(); 
	    }  catch (SQLException e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	}
	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub
		String query = "delete from inventory where name = ?";
		if (this.connection == null) {
            throw new IllegalStateException("Database connection is not established. Call connect() first.");
        }
		try (PreparedStatement ps = connection.prepareStatement(query)) { 	
        	ps.setString(1, name);
        	int results = ps.executeUpdate(); 
	    }  catch (SQLException e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	}
}
