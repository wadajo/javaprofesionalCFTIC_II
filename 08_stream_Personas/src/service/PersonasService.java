package service;

import java.util.ArrayList;
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
		
		return masJoven;
	}
	//TODO el n�mero de personas cuya edad es superior a la media
	public int superiorMedia() {
		
	}
	//TODO personas ordenadas por edad
	public List<Persona> ordenadasPorEdad(){
		
	}
	//TODO lista nombres de personas
	public List<String> nombres(){
		
	}
	//TODO dominio sea igual al indicado
	public List<Persona> personasDominio(String dominio){
		
	}
	
	
	
	
}
