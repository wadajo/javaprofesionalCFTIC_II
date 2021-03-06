package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
	
	public static LocalDate devolverEnLocalDate (Date vieja) {
		return Instant.ofEpochMilli(vieja.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDateTime devolverEnLocalDateTime (Date vieja) {
		return Instant.ofEpochMilli(vieja.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
	}
	
	public static Date devolverEnDate (LocalDate nueva) {		
		return Date.from(nueva.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
