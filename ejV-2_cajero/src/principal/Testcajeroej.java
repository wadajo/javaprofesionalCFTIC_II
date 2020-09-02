package principal;

import java.util.List;

import model.Cuenta;
import service.GestorBBDD;

public class Testcajeroej {

	public static void main(String[] args) {
		GestorBBDD servicio = new GestorBBDD();
		
		List<Cuenta> todas=servicio.mostrarCuentas();
		for (Cuenta una : todas) {
			System.out.println("ID cuenta: "+una.getId());
			System.out.println("Saldo: "+una.getSaldo());
			System.out.println("Tipo de cuenta: "+una.getTipoCuenta());
			System.out.println("Titulares: "); una.getTitulares().forEach(c->System.out.println(c.getNombre()));
		}

	}

}
