package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.Contacto;

/* Nuevo contacto, Recuperar contacto, Elminar contacto, Mostrar todos
 * 
 */

public class AgendaService {
	List<Contacto> misContactos = new ArrayList<>();
	String ruta = "prueba.txt";
	
	public boolean escribir(Contacto nuevo) {
		try(FileOutputStream fos = new FileOutputStream(ruta, true);
				PrintStream out = new PrintStream(fos)){
			out.println(nuevo);
		} catch (FileNotFoundException ex){
			System.out.println("El directorio es incorrecto.");
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean agregarContacto (Contacto c1) {
		if (!existeContacto(c1.getEmail())) {
			misContactos.add(c1);
			escribir(c1);
			return true;
		}
		return false;
	}
	
	public Contacto buscarContacto(String busqueda) {
		for (Contacto c : misContactos) {
			if (c.getEmail().contains(busqueda))
				return c;
			else System.out.println("No existe un contacto con ese email");
		}
		return null;		
	}	
	
	public void mostrarContactos(){		
		misContactos.forEach(c->System.out.println(c));
	}
	
	public Contacto eliminarContacto (Contacto aEliminar) {
		if (misContactos.removeIf(c->c.getEmail().contains((CharSequence) aEliminar.getEmail()))) {
			return aEliminar;
		} else	{
			System.out.println("No se ha podido eliminar.");
			return null;
		}			
	}

	private boolean existeContacto(String emailComprobar) {		
		for (Contacto c : misContactos) {
			if (c.getEmail().contains((CharSequence) emailComprobar)) {
				return true;
			}
		}
		return false;
	}
}
