package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="product")
@XmlType(propOrder= {"available","wholesalerPrice","publicPrice","stock"})
public class Product {
	private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts = 0;
    
    static double EXPIRATION_RATE=0.60;
    
    public Product() {
		this.id = ++totalProducts;
    };
    
	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = ++totalProducts;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.publicPrice = new Amount(wholesalerPrice * 2);//wholesalerPrice * 2;
		this.available = available;
		this.stock = stock;
	}
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="publicPrice")
	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount  publicPrice) {
		this.publicPrice = publicPrice;
	}
	@XmlElement(name="wholesalerPrice")
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.publicPrice = new Amount(wholesalerPrice.getValor() * 2);
		this.wholesalerPrice = wholesalerPrice;
	}
	@XmlElement(name="available")
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	@XmlElement(name="stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
		if(stock > 0) {
			this.available = true;
		}
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}
	
	public void expire() {
		//this.publicPrice = this.getPublicPrice()*EXPIRATION_RATE;
		publicPrice.setValor(this.getPublicPrice().getValor()*EXPIRATION_RATE);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice="
				+ wholesalerPrice + ", available=" + available + ", stock=" + stock + "]";
	}
	
	

    

    
}
