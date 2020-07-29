package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Caso;
import service.GestorCasos;
import util.Utilidades;

public class Pruebas {
	public static SimpleDateFormat personalizado = new SimpleDateFormat("yyyy-MM-dd");	
	public static DateTimeFormatter mostrar=DateTimeFormatter.ofPattern("d MMMM uuuu",Locale.forLanguageTag("es-ES"));
	
	public static void main(String[] args) {
		GestorCasos servicio = new GestorCasos();
		
		String ccaa="Comunidad de Madrid";
		//hay igual cantidad de registros (Caso) para todas las CCAA: uno por día, del 31/01 al 05/07
		System.out.println("Cantidad de registros para "+ccaa+": "+servicio.casosComunidad(ccaa).size()+"\n");
		
		Date dsd;
		Date hasta;
		Date unDia;
		try {
			/*
			dsd=personalizado.parse("2020-04-01");
			hasta = personalizado.parse("2020-05-01");
			servicio.casosEntreFechas(dsd, hasta).forEach(c->System.out.println(
					"Comunidad: "+c.getComunidad()+
					" - Casos: "+c.getCasos()+
					" - Fecha: "+personalizado.format(c.getFecha())));
			*/
			unDia=personalizado.parse("2020-03-20");
			LocalDate unDiaLD = Utilidades.devolverEnLocalDate(unDia);
			System.out.println("Casos en el día "+unDiaLD.format(mostrar)+" en todo el país: "+servicio.casosEnUnDia(unDia));
			
			System.out.println("La fecha pico de contagios fue: "+Utilidades.devolverEnLocalDate(servicio.fechaPicoContagiosV2()).format(mostrar)+
			" con "+servicio.casosEnUnDia(servicio.fechaPicoContagiosV2())+ " casos.");
			
			//System.out.println("El promedio diario de casos registrados en España ha sido: "+servicio.mediaDiaria()+" casos.");
			System.out.println("La comunidad de "+ccaa+" registró en este período un total de "+servicio.totalPositivos(ccaa)+
			" casos.");
			
			//genero un Map a partir del método
			Map<String, List<Caso>> tabla = servicio.casosXComunidad();
			//le pregunto si esa tabla contiene valores para una cierta key, que sé que son Comunidades
			System.out.println("\n¿Hay casos de "+ccaa+"? "+tabla.containsKey(ccaa));
			//le pido cuántos elementos contiene la tabla, es decir, cuántas List (una por key)
			System.out.println("Elementos: "+tabla.size()+", una lista por Comunidad.");
			/*
			 * le pido un value, del tipo Caso, en la posición indicada (5, ordenada según la List, 
			 * que a su vez viene del stream y del CSV, es decir, cronológicamente desde el 31/01), 
			 * para la key especificada (una Comunidad en la variable ccaa)
			 */
			Caso registro = tabla.get(ccaa).get(5); 
			System.out.println("Un registro de "+registro.getComunidad()+": "+registro.getCasos()+" casos, el día "+registro.getFecha());
			//genero un Set de objetos que contienen tanto las key como los values
			Set<Entry<String, List<Caso>>> conjunto = tabla.entrySet();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
