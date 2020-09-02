package service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Pais;

public abstract class BaseService {
	public abstract Stream<Pais> getStream();
	
	public Long contarPaises() {
		return getStream()
				.count();
	}
	public List<Pais> paisesEnRegion (String region) {
		return getStream()
				.filter(p->p.getRegion().equalsIgnoreCase(region))
				.collect(Collectors.toList());
	}
	public Pais paisMasPoblado() {
		return getStream()
				.collect(Collectors.maxBy((p1,p2)->p1.getPoblacion().intValue()-p2.getPoblacion().intValue()))
				.orElse(new Pais());
	}
	public int paisesConMasHabitantesQue (Long habitantes) {
		return getStream()
				.collect(Collectors.filtering((p->p.getPoblacion()>habitantes), Collectors.counting())).intValue();
	}
	public List<Pais> paisesCuyoNombreContenga (String nom) {
		return getStream()
				.collect(Collectors.filtering((p->p.getNombre().contains(nom)), Collectors.toList()));
		}
	public Double[] coordenadasDesdeAlpha2Code (String alpha2code) {
		return getStream()
				.filter(p->p.getAlpha2Code().equalsIgnoreCase(alpha2code))
				.map(p->p.getCoordenadas())
				.findFirst()
				.orElse(new Double[2]);
	}
	public Double poblacionMediaRegion (String region) {
		return paisesEnRegion(region).stream()
				.collect(Collectors.averagingLong(p->p.getPoblacion()));
	}
	public Long poblacionTotalRegion (String region) {		
		return getStream()
			.filter(p->p.getRegion().equalsIgnoreCase(region))
			.mapToLong(Pais::getPoblacion)
			.sum();
	}	
	public Map<String,List<Pais>> paisesPorRegion() {
		return getStream()
				.collect(Collectors.groupingBy(Pais::getRegion));
	}
	public Map<String,Long> poblacionDeRegionesMap () {
		return getStream()
			.collect(Collectors.groupingBy(Pais::getRegion,Collectors.summingLong(Pais::getPoblacion)));			
	}	
}
