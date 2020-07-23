package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import model.Caso;

public class Utilidades {
	public static SimpleDateFormat personalizado = new SimpleDateFormat("yyyy-MM-dd",new Locale("es","ES"));
	
	public static Caso devolverCasoDsdString (String casoEnCadena, String separador) {
		String[] datos = casoEnCadena.split(separador);	
		
		try {
			return new Caso(datos[0],personalizado.parse(datos[1]),Integer.parseInt(datos[2]));
		} catch (NumberFormatException e) {			
			e.printStackTrace();
			return null;
		} catch (ParseException e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	public static String devolverCasoDsdPedido (Caso casoPedido, String separador) {		
		return casoPedido.getComunidad()+separador+personalizado.format(casoPedido.getFecha())+separador+casoPedido.getCasos();		
}
}
