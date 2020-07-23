package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import service.GestorCasos;

public class Home {
	public static SimpleDateFormat personalizado = new SimpleDateFormat("yyyy-MM-dd",new Locale("es","ES"));
	
	public static void main(String[] args) {
		
		GestorCasos servicio = new GestorCasos();
		String ccaa="Murcia";
		System.out.println("Cantidad de casos en "+ccaa+": "+servicio.casosComunidad(ccaa).size()+"\n");
		
		Date dsd;
		Date hasta;
		try {
			dsd=personalizado.parse("2020-04-01");
			hasta = personalizado.parse("2020-05-01");
			servicio.casosEntreFechas(dsd, hasta).forEach(c->System.out.println(
					"Comunidad: "+c.getComunidad()+
					" - Casos: "+c.getCasos()+
					" - Fecha: "+personalizado.format(c.getFecha())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
