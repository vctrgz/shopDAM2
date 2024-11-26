package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount {
double value;
String currency;

public Amount () {
	currency = "€";
};

public Amount(double value){
	this.value = value;
	currency = "€";
}
@XmlValue
public double getValor() {
	return value;
}
public void setValor(double value) {
	this.value = value;
}
@XmlAttribute(name="currency")
public String getCurrency() {
	return currency;
}
public String toString() {
	return value + " " + currency;
}
}
