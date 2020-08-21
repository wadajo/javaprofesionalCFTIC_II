package principal;

import java.util.Scanner;

import service.NotasService;

public class Menu {

	public static void main(String[] args) {
		NotasService servicio = new NotasService();
		Scanner sc = new Scanner(System.in);
		int selector = 0;
	do {
		menu();
		selector = Integer.parseInt(sc.nextLine());
		switch (selector) {
		case 1:
			System.out.println("Introduce una nota a ingresar: ");
			double nota = Double.parseDouble(sc.nextLine());
			servicio.ingresarNota(nota);
			break;
		case 2:
			System.out.println("La media de las notas es: "+servicio.calcularMedia());
			break;
		case 3:
			System.out.println("Ha habido "+servicio.calcularAprobados()+" aprobados(más de 6(seis)).");
			break;
		case 4:
			System.out.println("Estas son todas las notas registradas: ");
			servicio.devolverNotas().forEach(n->System.out.println("Nota: "+n)); 
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
		System.out.println("1. Ingresar nota");
		System.out.println("2. Calcular media");
		System.out.println("3. Contar aprobados");
		System.out.println("4. Ver todas las notas");
		System.out.println("0. Salir");
	}

}
