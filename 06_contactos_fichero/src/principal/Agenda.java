package principal;

import java.util.Scanner;

import model.Contacto;
import service.AgendaService;

public class Agenda {

	public static void main(String[] args) {
		AgendaService servicio = new AgendaService();
		Scanner sc = new Scanner(System.in);
		int selector = 0;
	do {
		menu();
		selector = Integer.parseInt(sc.nextLine());
		switch (selector) {
		case 1:
			ingresarContacto(servicio);
			break;
		case 2:
			busqueda(servicio);
			break;
		case 3:
			eliminar(servicio);
			break;
		case 4:
			mostrar(servicio);
			break;
		case 0:
			System.out.println("Has salido.");
			sc.close();
			break;
		default:
			System.out.println("Elige una opción válida.");
		}	
	}while (selector!=0);
	}

	private static void eliminar(AgendaService servicio) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingresa parte del email del contacto que deseas eliminar: ");
		String busqueda = sc.nextLine();
		servicio.eliminarContacto(busqueda);
		System.out.println("Se ha eliminado el contacto. ");
		}

	private static void mostrar(AgendaService servicio) {
		servicio.mostrarContactos();
	}

	private static void busqueda(AgendaService servicio) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingresa parte del email del contacto que deseas: ");
		String busqueda = sc.nextLine();
		if (null!=servicio.buscarContacto(busqueda)) {
			Contacto encontrado = servicio.buscarContacto(busqueda);
			System.out.println("Se ha encontrado el contacto. Sus datos son: \n"+encontrado);
		} else {
			System.out.println("No se ha encontrado el contacto.");
		}
	}

	private static void ingresarContacto(AgendaService servicio) {
		Scanner sc = new Scanner(System.in);		
		System.out.println("Añade el nombre del contacto: ");
		String nombre = sc.nextLine();
		System.out.println("Añade su email: ");
		String email = sc.nextLine();
		System.out.println("Añade su teléfono: ");
		String telefono = sc.nextLine();
		if (servicio.agregarContacto(nombre,email,telefono)) {
			System.out.println("Has añadido un contacto con éxito.");
		} else {
			System.out.println("Ya has añadido ese contacto. Ingresa uno nuevo.");
		}
	}

	private static void menu() {
		System.out.println("\nMenú (pulsa número para seleccionar): ");
		System.out.println("1. Ingresar nuevo contacto");
		System.out.println("2. Buscar contacto");
		System.out.println("3. Eliminar contacto");
		System.out.println("4. Mostrar todos los contactos");
		System.out.println("0. Salir");
	}

}
