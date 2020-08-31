package principal;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import service.GestorBBDD;

public class Menu {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int selector = 0;
		GestorBBDD servicio = new GestorBBDD();
		DateTimeFormatter personalizado = DateTimeFormatter.ofPattern("EEEE dd 'de' LLL 'Hora 'HH:mm:ss");
		
		System.out.println("--Cajero Virtual--");
		System.out.println("Para continuar, introduce tu número de cuenta.");
		int cuenta = Integer.parseInt(sc.nextLine());
		if (null!=servicio.existeCuenta(cuenta)) {
			System.out.println("Bienvenido de nuevo.");
		do {
			menu();
			selector = Integer.parseInt(sc.nextLine());
			switch (selector) {
			case 1:									
				System.out.println("¿Cuánto dinero desea retirar?");
				if (servicio.retirar(cuenta, Integer.parseInt(sc.nextLine())))
					System.out.println("Ha retirado correctamente ese monto de su cuenta.");
				break;
			case 2:
				System.out.println("¿Cuánto dinero desea ingresar?");
				if (servicio.ingresar(cuenta, Integer.parseInt(sc.nextLine())))
					System.out.println("Se ha ingresado correctamente a su cuenta.");
				break;
			case 3:
				System.out.println("Su saldo es de "+servicio.saldoActual(cuenta)+" euros.");
				break;
			case 4:
				if (!servicio.mostrarUltimos(cuenta).isEmpty()) {
				System.out.println("Estos son sus últimos movimientos:");
				servicio.mostrarUltimos(cuenta)
					.forEach(m->{System.out.println("\nFecha: "+m.getFecha().format(personalizado));
								System.out.println("Operación: "+m.getOperacion());
								System.out.println("Cantidad: "+m.getDinero());
						});
				} else System.out.println("No se registran movimientos en ese período.");
				break;
			case 5:
				System.out.println("Introduzca el número de cuenta de destino: ");
				int destino=Integer.parseInt(sc.nextLine());
				if (null!=servicio.existeCuenta(destino)) {
					System.out.println("Ingrese la cantidad a transferir");
					if (servicio.transferir(cuenta,destino,Integer.parseInt(sc.nextLine())))
							System.out.println("Se realizó la transferencia correctamente.");;
				} else System.out.println("No existe esa cuenta de destino.");
				break;
			case 0:
				System.out.println("Has salido. ¡Hasta luego!");
				break;
			default:
				System.out.println("Ingresa una opción válida.");
			}
		}while (selector!=0);
		sc.close();
		} else System.out.println("No existe una cuenta con ese número. ¡Adiós!");
	}
	
	private static void menu() {
		System.out.println("\n1. Retirar dinero.");
		System.out.println("2. Ingresar dinero.");
		System.out.println("3. Mostrar saldo.");
		System.out.println("4. Mostrar últimos movimientos (últimos 30 días).");
		System.out.println("5. Transferir dinero a otra cuenta.");
		System.out.println("0. Salir");
	}
}
