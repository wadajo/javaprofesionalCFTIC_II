package principal;

import java.util.List;
import java.util.Scanner;

import model.Contacto;
import service.BaseServicio;

public class Menu {
	static Scanner sc = new Scanner(System.in);
	static BaseServicio service = new BaseServicio();
	public static void main(String[] args) {		
		int selector = 0;
		do {
			menu();
			selector = Integer.parseInt(sc.nextLine());
			switch (selector) {
			case 1:
				agregar();
				break;
			case 2:
				eliminar();
				break;
			case 3:
				buscar();
				break;
			case 4:
				mostrar();
				break;
			case 0:
				System.out.println("Has salido. ¡Hasta luego!");
				break;
			default:
				System.out.println("Ingresa una opción válida.");
			}
		}while (selector!=0);
		sc.close();
		}		

		private static void menu() {
			System.out.println("1. Ingresar Contacto");
			System.out.println("2. Eliminar Contacto");
			System.out.println("3. Buscar Contacto");
			System.out.println("4. Mostrar todos los Contactos");
			System.out.println("0. Salir");
		}		
		private static void agregar() {			
			System.out.println("Introduce un nombre: ");
			String nombre = sc.nextLine();
			System.out.println("Introduce un email: ");
			String email = sc.nextLine();
			System.out.println("Introduce la edad: ");
			int edad = Integer.parseInt(sc.nextLine());
			if(service.insertarContacto(new Contacto(nombre,email,edad))) {
				System.out.println("Se ha insertado el Contacto en la BBDD.\n");
			} else {
				System.out.println("No se ha podido insertar el Contacto.\n");
			}
		}
		private static void eliminar() {
			System.out.println("Introduce el email del Contacto a eliminar: ");
			Contacto borrado=service.buscarContacto(sc.nextLine());
			if(service.eliminarContacto(borrado)) {
				System.out.println("Has eliminado el contacto.\n");
			} else {
				System.out.println("No se ha podido eliminar el Contacto.\n");
			}
		}
		private static void buscar() {
			System.out.println("Introduce el email del Contacto a buscar: ");
			Contacto buscado=service.buscarContacto(sc.nextLine());
			if(null!=buscado) {
				System.out.println("Se ha encontrado el contacto: ");
				System.out.println("Nombre: "+buscado.getNombre());
				System.out.println("E-mail: "+buscado.getEmail());
				System.out.println("Edad: "+buscado.getEdad()+"\n");
			} else {
				System.out.println("No se ha encontrado el contacto.\n");
			}
			
		}
		private static void mostrar() {
			System.out.println("Estos son los contactos de la base: ");
			List<Contacto> contactosBBDD=service.mostrarContactos();
			contactosBBDD.forEach(c->{
				System.out.println("Nombre: "+c.getNombre());
				System.out.println("Email: "+c.getEmail());
				System.out.println("Edad: "+c.getEdad()+"\n");
			});
		}
}
