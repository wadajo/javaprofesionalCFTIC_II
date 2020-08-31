package model;

import java.util.List;

public class Cuenta {
	int id;
	List<Titular> titulares;
	int saldo=0;
	String tipoCuenta;
	
	public Cuenta(int id, Titular titular, String tipoCuenta) {
		this.id = id;
		this.titulares.add(titular);
		this.tipoCuenta = tipoCuenta;
	}
	public Cuenta(int id, List<Titular> titulares, int saldo, String tipoCuenta) {
		this.id = id;
		this.titulares = titulares;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
	}	
	public Cuenta(int id, List<Titular> titulares, String tipoCuenta) {
		this.id = id;
		this.titulares = titulares;
		this.tipoCuenta = tipoCuenta;
	}
	public Cuenta () {		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Titular> getTitulares() {
		return titulares;
	}
	public void setTitulares(List<Titular> titulares) {
		this.titulares = titulares;
	}
	public Titular getT() {
		return titulares.get(0);
	}
	public void setT(Titular t) {
		titulares.add(t);
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
}
