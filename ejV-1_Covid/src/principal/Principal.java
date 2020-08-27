package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import model.Caso;
import service.BaseService;
import service.JsonService;

public class Principal {

	/*public static void main(String[] args) throws ParseException {
		BaseService service  = new JsonService("datos_ccaas.json");
		String fecha1= "2020-04-01";
		String fecha2= "2020-04-05";
		String fecha3= "2020-05-05";
		SimpleDateFormat personalizado = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Caso> lista=service.casosEntreFechas(personalizado.parse(fecha1), personalizado.parse(fecha2));
		System.out.println("Casos entre "+fecha1+" y "+fecha2);
		lista.forEach(c->System.out.println(c.getComunidad()+"-"+c.getPositivos()+"-"+c.getFecha()));
		
		//System.out.println("\nEl pico fue "+personalizado.format(service.picoContagios()));
		
		System.out.println("\nTotal positivos Madrid: "+service.totalPositivosComunidad("Madrid"));
		Map<String,List<Caso>> tabla=service.casosComunidad("Madrid");
		tabla.forEach((k,v)->{
			System.out.println("\nComunidad: "+k);
			v.forEach(c->System.out.println(personalizado.format(c.getFecha())));
			});
		
		System.out.println("\nMedia de positivos diarios: "+service.mediaPositivos());
	}*/

}
