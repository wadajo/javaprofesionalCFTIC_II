package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Persona;

public class PersonasService {
	List<Persona> personas=new ArrayList<>();
	public PersonasService() {
		personas.add(new Persona("Alex","al@gg.com",20));				
		personas.add(new Persona("Juan","alfd@gg.com",29));
		personas.add(new Persona("Elena","al@gg.es",17));
		personas.add(new Persona("Marta","alaaoi@gg.com",34));
		personas.add(new Persona("Lucas","alert@gg.es",44));
		personas.add(new Persona("Alicia","sdfl@gg.com",35));
	}
	//persona más joven
	public Persona obtenerMasJoven() {
		//Persona masJoven=null;
		Stream<Persona> todas = personas.stream();
		Persona masJoven=todas
			.sorted((n1,n2)->n1.getEdad()-n2.getEdad())
			.findFirst().get();
		
		// también podía ser .stream().min(p1,p2)->(p.getEdad()-p2.getEdad()).orElse(null)
		
		return masJoven;
	}
	// el n�mero de personas cuya edad es superior a la media
	public int superiorMedia() {
		double media=
		personas
			.stream()
			.mapToDouble(n->n.getEdad())
			.average()
			.orElse(0);
		return(int) personas.stream()
							.filter(p->p.getEdad()>media)
							.count();
		
	}
	// personas ordenadas por edad
	public List<Persona> ordenadasPorEdad(){
		return personas.stream()
				.sorted((n1,n2)->n1.getEdad()-n2.getEdad())
				.collect(Collectors.toList());
	}
	// ordenadas primero por nombre y luego por edad, dos criterios en dos Comparator distintos
	public List<Persona> ordenadasPorNombreYEdad(){
		Comparator<Persona> comp1 = (p1,p2)->p1.getNombre().compareTo(p2.getNombre());
		Comparator<Persona> comp2 = (p1,p2)->p1.getEdad()-p2.getEdad();
		return personas.stream()
				.sorted(comp1.thenComparing(comp2))
				.collect(Collectors.toList());
	}
	
	// lista nombres de personas
	public List<String> nombres(){
		return personas.stream()
						.map(p->p.getNombre())
						.collect(Collectors.toList());
	}
	//TODO dominio sea igual al indicado
	public List<Persona> personasDominio(String dominio){		
		return personas.stream()
						.filter(p->p.getEmail().endsWith(dominio.toLowerCase()))
						.collect(Collectors.toList());
	}
	
	
	
	
}
