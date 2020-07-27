package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Caso;
import util.Utilidades;

public class GestorCasos {
	private final String RUTA="datos_5_7_2020.csv";
	private final String SEPARADOR = ",";
	private Path path;
	public static SimpleDateFormat personalizado = new SimpleDateFormat("yyyy-MM-dd",new Locale("es","ES"));
	
	public GestorCasos() {
		path=Paths.get(RUTA);
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Caso> casosEntreFechas (Date dsd, Date hasta){
		return	streamCasos()
				.filter(p->p.getFecha().after(dsd))
				.filter(p->p.getFecha().before(hasta))
				.collect(Collectors.toList());
	}
	
	public int casosEnUnDia (Date dia) {
		return	streamCasos()
				.filter(p->p.getFecha().getTime()==dia.getTime())				
				.collect(Collectors.summingInt(p->p.getCasos()));
	}
	/* intento fallido
	public Date fechaPicoContagiosV1() {
		Map<Date,Integer> casosPorDia = new HashMap<Date, Integer>();
		casosPorDia=streamCasos()		
				//.map(p->casosPorDia.put(p.getFecha(), casosEnUnDia(p.getFecha())))
				.collect(Collectors.toMap((p->p.getFecha()), (p->casosEnUnDia(p.getFecha()))));
		Date fechaPico = casosPorDia.
				//.collect();
	}
	*/ 
	
	public Date fechaPicoContagiosV2() {
		return streamCasos()
			.max((d1,d2)->casosEnUnDia(d1.getFecha())-casosEnUnDia(d2.getFecha()))
			.map(Caso::getFecha)
			.get();		
	}
	
	public Double mediaDiaria () {
		return streamCasos()
			.collect(Collectors.averagingInt(p->casosEnUnDia(p.getFecha())));
			
	}
	
	public int totalPositivos (String comunidad) {
		return streamCasos()
				.filter(c->c.getComunidad().equalsIgnoreCase(comunidad))
				.collect(Collectors.summingInt(Caso::getCasos));
	}
	
	public Map<String,List<Caso>> casosXComunidad (){
		return streamCasos()
				.collect(Collectors.groupingBy(Caso::getComunidad));
	}
	
	public List<Caso> casosComunidad(String comunidad) { 
		return	streamCasos()
			.filter(p->p.getComunidad().equalsIgnoreCase(comunidad))
			.collect(Collectors.toList());
	}
	
	private Stream<Caso> streamCasos(){
		try {
			return Files.lines(path,StandardCharsets.UTF_8)
					.map(p->(Utilidades.devolverCasoDsdString(p, SEPARADOR)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
