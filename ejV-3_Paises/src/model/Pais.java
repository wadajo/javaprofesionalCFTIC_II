package model;

public class Pais {
	String nombre;
	String capital;
	Double area;  //experimentando con clases Wrapper
	Long poblacion;	
	String region;
	String alpha2Code;
	Double[] coordenadas;
		
	public Pais() {
	}
	public Pais(String nombre, String capital, Double area, Long poblacion, String region) {
		this.nombre = nombre;
		this.capital = capital;
		this.area = area;
		this.poblacion = poblacion;
		this.region = region;
	}
	public Pais(String nombre, String capital, Double area, Long poblacion, String region, String alpha2Code,
			Double[] coordenadas) {
		this.nombre = nombre;
		this.capital = capital;
		this.area = area;
		this.poblacion = poblacion;
		this.region = region;
		this.alpha2Code = alpha2Code;
		this.coordenadas = coordenadas;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Long getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(Long poblacion) {
		this.poblacion = poblacion;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAlpha2Code() {
		return alpha2Code;
	}
	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}
	public Double[] getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(Double[] coordenadas) {
		this.coordenadas = coordenadas;
	}		
}
