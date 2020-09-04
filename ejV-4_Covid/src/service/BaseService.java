package service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Caso;

//como tiene un método abstracto, la clase debe ser abstracta
public abstract class BaseService {
	public abstract Stream<Caso> getStream();
	
	public List<Caso> casosEntreFechas (Date fechaIni, Date fechaFin){
		return getStream()
			.filter(p->p.getFecha().getTime()>fechaIni.getTime()&&
						p.getFecha().getTime()<fechaFin.getTime()) //compara long
			.collect(Collectors.toList());
	}
	
	// también se puede usar equals de Date, está sobreescrito
	public int casosEnDia (Date fecha) {
		return getStream()
			.filter(c->c.getFecha().compareTo(fecha)==0)
			.collect(Collectors.summingInt(Caso::getPositivos));
	}
	
	public Date picoContagios() {
		return getStream()
				.collect(Collectors.groupingBy(Caso::getFecha))
				.keySet().stream()
				.max((d1,d2)->casosEnDia(d1)-casosEnDia(d2))
				.get();
	}
	public Date picoContagios(String comunidad) {
		return getStream()
				.filter(c->c.getComunidad().equalsIgnoreCase(comunidad))
				.collect(Collectors.groupingBy(Caso::getFecha))
				.keySet().stream()
				.max((d1,d2)->casosEnDia(d1)-casosEnDia(d2))
				.get();
	}
	public int mediaPositivos() {
		Map<Date,List<Caso>> grupoPorFecha = getStream()
				.collect(Collectors.groupingBy(Caso::getFecha));
		return grupoPorFecha.values().stream()
				.collect(Collectors.averagingInt(l->l.stream().mapToInt(Caso::getPositivos).sum()))
				.intValue();
	}
		
	public int totalPositivosComunidad (String comunidad) {
		return getStream()
				.filter(c->c.getComunidad().equals(comunidad))
				.mapToInt(Caso::getPositivos)
				.sum();
	}
	public double mediaPositivosComunidad (String comunidad) {
		return getStream()
				.filter(c->c.getComunidad().equals(comunidad))
				.mapToInt(Caso::getPositivos)
				.average()
				.orElse(0.0);
	}
	public Map<String,List<Caso>> casosComunidad (String comunidad){
		return getStream()
				.collect(Collectors.groupingBy(Caso::getComunidad));
	}
}
