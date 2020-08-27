package principal;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

import service.BaseService;
import service.GestorBBDD;
import service.JsonService;

public class Menu {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int selector = 0;
		System.out.println("Aplicación de Casos Covid - España 2020: ");
		do {
			menu();
			selector = Integer.parseInt(sc.nextLine());
			switch (selector) {
			case 1:				
				System.out.println("Introduce la ruta del fichero a cargar en la BBDD: ");
				String ruta = sc.nextLine();
				GestorBBDD manejo = new GestorBBDD();
					if (manejo.opcion1(ruta))
					System.out.println("Finalizó la carga del fichero.");					
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
		System.out.println("\n1. Agregar casos desde fichero.");
		System.out.println("0. Salir");
	}
}
