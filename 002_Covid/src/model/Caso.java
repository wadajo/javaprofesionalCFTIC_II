package model;

import java.util.Date;

public class Caso {
	private String ccaa_iso;
	private Date fecha;
	private int num_casos;
	
	
	public Caso(String comunidad, Date fecha, int casos) {
		this.setComunidad(comunidad);
		this.fecha = fecha;
		this.num_casos = casos;
	}
	
	public String getComunidad() {		
		return ccaa_iso;
	}
	public void setComunidad(String comunidad) {
		String comunidadFull=null;
		switch(comunidad) {
			case "AN":
				comunidadFull="Andalucía";
				break;
			case "AR":
				comunidadFull="Aragón";
				break;
			case "AS":
				comunidadFull="Asturias";
				break;
			case "CN":
				comunidadFull="Canarias";
				break; 
			case "CB":
				comunidadFull="Cantabria";
				break;
			case "CM":
				comunidadFull="Castilla-La Mancha";
				break; 
			case "CL":
				comunidadFull="Castilla y León";
				break;
			case "CT":
				comunidadFull="Cataluña";
				break; 
			case "EX":
				comunidadFull="Extremadura";
				break;
			case "GA":
				comunidadFull="Galicia";
				break; 
			case "IB":
				comunidadFull="Islas Baleares";
				break;
			case "RI":
				comunidadFull="La Rioja";
				break; 
			case "MD":
				comunidadFull="Comunidad de Madrid";
				break;
			case "MC":
				comunidadFull="Murcia";
				break; 
			case "NC":
				comunidadFull="Navarra";
				break;
			case "PV":
				comunidadFull="País Vasco";
				break; 
			case "VC":
				comunidadFull="Comunidad Valenciana";
				break;
			case "CE":
				comunidadFull="Ceuta";
				break;
			case "ML":
				comunidadFull="Melilla";
				break;
			default:
				comunidadFull="S-D";
				break;
		}		
		this.ccaa_iso = comunidadFull;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getCasos() {
		return num_casos;
	}
	public void setCasos(int casos) {
		this.num_casos = casos;
	}
}
