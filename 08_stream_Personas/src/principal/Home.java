package principal;

import service.PersonasService;

public class Home {

	public static void main(String[] args) {
		PersonasService servicio = new PersonasService();
		System.out.println("La persona más joven es "+servicio.obtenerMasJoven().getNombre()+
				" con "+servicio.obtenerMasJoven().getEdad()+" años.");
	}

}
