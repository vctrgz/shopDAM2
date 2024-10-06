package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplFile implements Dao {

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
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
		FileReader fr;
		try {
			fr = new FileReader(f);
		
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		while (linea != null) {
			String[] resultado = linea.split(";");
			String[] resultadoFinal;
			String nombre = null;
			double precio = 0;
			int stock = 0;
			for (int i = 0; i < resultado.length; i++) {
				resultadoFinal = resultado[i].split(":");
				switch (i) {
				case 0:
					nombre = resultadoFinal[1];
					break;
				case 1:
					precio = Double.parseDouble(resultadoFinal[1]);
					break;
				case 2:
					stock = Integer.parseInt(resultadoFinal[1]);
					break;
				}
			}
			temporal.add(new Product(nombre, precio, true, stock));

			linea = br.readLine();
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return temporal;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		
		LocalDate fecha = LocalDate.now();
		String rutaCarpeta = System.getProperty("user.dir") + File.separator + "files";
		String rutaArchivo = rutaCarpeta + File.separator + "inventory_" + fecha.toString() + ".txt";
		LocalDateTime fechaHora = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss");
		System.out.println(rutaArchivo);
		File file = new File(rutaArchivo);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			int numeroTotalProductos = 0;
			for (Product product : inventory) {
				if (product != null) {
					pw.write(product.getId() + ";Product=" + product.getName() + ";Stock=" + product.getStock() + "\n");
					numeroTotalProductos++;
				}
			}
			pw.write("Numero total de productos: " + numeroTotalProductos);
			pw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
