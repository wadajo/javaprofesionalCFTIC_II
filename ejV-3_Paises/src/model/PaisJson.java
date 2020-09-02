package model;

public class PaisJson {
	String name;
	String capital;
	Double area;
	Long population;
	String region;
	String alpha2Code;
	Double[] latlng;
	
	public PaisJson() {
	}
	public PaisJson(String name, String capital, Double area, Long population, String region) {
		this.name = name;
		this.capital = capital;
		this.area = area;
		this.population = population;
		this.region = region;
	}
	public PaisJson(String name, String capital, Double area, Long population, String region, String alpha2Code,
			Double[] latlng) {
		this.name = name;
		this.capital = capital;
		this.area = area;
		this.population = population;
		this.region = region;
		this.alpha2Code = alpha2Code;
		this.latlng = latlng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getPopulation() {
		return population;
	}
	public void setPopulation(Long population) {
		this.population = population;
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
	public Double[] getLatlng() {
		return latlng;
	}
	public void setLatlng(Double[] latlng) {
		this.latlng = latlng;
	}
	
}
