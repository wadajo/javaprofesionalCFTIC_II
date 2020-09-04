package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import model.CaseJson;
import model.Caso;

public class Utilidades {
	public static SimpleDateFormat personalizado = new SimpleDateFormat("dd/MM/yyyy",new Locale("es","ES"));
	
	public static Caso convertirJsonACaso(CaseJson c) {
		return new Caso(
				convertirAbreviaturaANombre(c.getCcaa_iso()),
				c.getFecha(),
				c.getNum_casos()
				);
	}
	
	public static Caso stringToCaso(String caso) {
		String[] datos = caso.split("[,]");		
		try {
			return new Caso(
					convertirAbreviaturaANombre(datos[0]),					
					personalizado.parse(datos[2]),
					Integer.parseInt(datos[1]));
		} catch (NumberFormatException e) {			
			e.printStackTrace();
			return null;
		} catch (ParseException e) {			
			e.printStackTrace();
			return null;
		}		
	}

	private static String convertirAbreviaturaANombre(String siglas) {
		HashMap<String, String> comunidades = new HashMap<>();
		comunidades.put("AN", "Andalucía");
		comunidades.put("AR", "Aragón");
		comunidades.put("AS", "Asturias");
		comunidades.put("CN", "Canarias");
		comunidades.put("CB", "Cantabria");
		comunidades.put("CM", "Castilla-La Mancha");
		comunidades.put("CL", "Castilla y León");
		comunidades.put("CT", "Cataluña");
		comunidades.put("EX", "Extremadura");
		comunidades.put("GA", "Galicia");
		comunidades.put("IB", "Islas Baleares");
		comunidades.put("RI", "La Rioja");
		comunidades.put("MD", "Madrid");
		comunidades.put("MC", "Murcia");
		comunidades.put("NC", "Navarra");
		comunidades.put("PV", "País Vasco");
		comunidades.put("VC", "Valencia");
		comunidades.put("CE", "Ceuta");
		comunidades.put("ML", "Melilla");
		return comunidades.get(siglas);
	}	

}
