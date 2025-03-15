package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="product")
@XmlType(propOrder= {"available","wholesalerPrice","publicPrice","stock"})
@Entity
@Table(name="inventory")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column
    private String name;
	@Transient
	private Amount publicPrice;
	@Transient
    private Amount wholesalerPrice;
	@Column
	private double price;
	@Column
    private boolean available;
    @Column
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
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.price = wholesalerPrice;
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
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
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
