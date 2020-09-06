package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Cuenta;
import model.Titular;
import service.GestorBBDD;

public class Administrador {
	static GestorBBDD servicio = new GestorBBDD();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int selector = 0;

		System.out.println("--Administración Cajero Virtual--");		
	
			System.out.println("Bienvenido de nuevo.");
			do {
				menu();
				selector = Integer.parseInt(sc.nextLine());
				switch (selector) {
				case 1:
					if (nuevaCuenta())
						System.out.println("Se ha creado con éxito la cuenta.");
					else 
						System.out.println("No se ha podido crear la cuenta.");
					break;
				case 2:
					if (agregarCliente())
						System.out.println("Se ha agregado el nuevo cliente a la Base.");
					else
						System.out.println("No se ha podido agregar el nuevo cliente.");
					break;
				case 3:
					if(sumarTitulares())
						System.out.println("Se ha añadido el Titular con éxito a esa cuenta.");
					else
						System.out.println("No se ha podido añadir el Titular a la cuenta deseada.");
					break;
				case 0:
					System.out.println("Has salido. ¡Hasta luego!");
					break;
				default:
					System.out.println("Ingresa una opción válida.");
				}
			} while (selector != 0);
			sc.close();		
	}
private static boolean agregarCliente() {
		Scanner sc=new Scanner (System.in);
		System.out.println("Ingrese el DNI del nuevo cliente: ");
		var dni = Integer.parseInt(sc.nextLine());
		if (null==servicio.recuperarTitular(dni)) {
			System.out.println("Ingrese su nombre: ");
			var nombreCliente = sc.nextLine();
			System.out.println("Ingrese su dirección: ");
			var direccion = sc.nextLine();
			System.out.println("Ingrese su teléfono: ");
			var telefono = Integer.parseInt(sc.nextLine());
			return (servicio.crearTitular(new Titular(dni,nombreCliente,direccion,telefono)));
			} else 
				System.out.println("Ya existe un cliente con ese DNI en la Base.");
		sc.close();
		return false;
	}

	private static boolean sumarTitulares() {
		Scanner sc=new Scanner (System.in);
		System.out.println("Ingrese el número de cuenta al que quiere añadir un titular: ");
		int nroCuenta=Integer.parseInt(sc.nextLine());
		Cuenta aModificar = servicio.existeCuenta(nroCuenta);
		if (null!=aModificar) {
			System.out.println("¿El titular a sumar ya existe en la base? (Y/N)");
			String resp = sc.nextLine().substring(0, 1);		
			if (resp.equalsIgnoreCase("Y")) {
				System.out.println("Ingrese su DNI: ");
				int dni = Integer.parseInt(sc.nextLine());
				Titular t=servicio.recuperarTitular(dni);
				return (servicio.sumarTitularACuenta(t, aModificar));
			} else if (resp.equalsIgnoreCase("N")) {
				System.out.println("Agregue el nuevo cliente y vuelva a intentarlo: ");
				agregarCliente();
			} else 
				System.out.println("Respuesta no válida.");
				return false;
		}
		System.out.println("El número de cuenta ingresado no existe en la Base.");
		return false;
	}

	private static boolean nuevaCuenta() {
		Cuenta nueva=new Cuenta();
		List<Titular> titulares=new ArrayList<>();
		int nroCuenta=0;
		String tipoCuenta="";
		Scanner sc=new Scanner (System.in);
		System.out.println("¿El Titular ya existe en la base? (Y/N)");
		String resp = sc.nextLine().substring(0, 1);		
		if (resp.equalsIgnoreCase("N")) {
			System.out.println("Crearemos un nuevo titular. ");
			System.out.print("Ingresa su nombre: ");
			String nombre = sc.nextLine();
			System.out.println("Ingresa su DNI: ");
			int dniNuevo = Integer.parseInt(sc.nextLine());
			System.out.println("Ingresa su dirección: ");
			String direccion = sc.nextLine();
			System.out.println("Ingresa su teléfono: ");
			int telefono = Integer.parseInt(sc.nextLine());
			Titular nuevo = new Titular (dniNuevo,nombre,direccion,telefono);
			titulares.add(nuevo);
			System.out.println("¿Qué número de cuenta quieres asignarle?");
			nroCuenta = Integer.parseInt(sc.nextLine());
			System.out.println("¿Qué tipo de cuenta será?");
			tipoCuenta = sc.nextLine();
			nueva = new Cuenta(nroCuenta,titulares,0,tipoCuenta);
			return servicio.crearCuenta(nueva);
		} else if (resp.equalsIgnoreCase("Y")) {
			System.out.println("Ingrese su DNI: ");
			int dni = Integer.parseInt(sc.nextLine());
			Titular t=servicio.recuperarTitular(dni);
			titulares.add(t);
			System.out.println("¿Qué número de cuenta quieres crear?");
			nroCuenta = Integer.parseInt(sc.nextLine());
			System.out.println("¿Qué tipo de cuenta será?");
			tipoCuenta = sc.nextLine();
			nueva=new Cuenta(nroCuenta,titulares,0,tipoCuenta);
			return servicio.crearCuenta(nueva, titulares.get(0));
		} else {		
			System.out.println("Respuesta no válida.");			
		}
		return false;
	}

	private static void menu() {
		System.out.println("\n1. Añadir nueva cuenta.");
		System.out.println("2. Añadir nuevo cliente.");
		System.out.println("3. Asociar titular a una cuenta existente.");
		System.out.println("0. Salir");
	}

}
