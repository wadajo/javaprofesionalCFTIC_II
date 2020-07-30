package model;

public class Contacto {
	int idcontacto;
	String nombre;
	String email;
	int edad;
	
	public Contacto(int idcontacto, String nombre, String email, int edad) {
		this.idcontacto = idcontacto;
		this.nombre = nombre;
		this.email = email;
		this.edad = edad;
	}
	public Contacto(String nombre, String email, int edad) {
		this.nombre = nombre;
		this.email = email;
		this.edad = edad;
	}
	public Contacto() {
	}
	public int getId() {
		return idcontacto;
	}
	public void setId(int id) {
		this.idcontacto = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
}
