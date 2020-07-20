package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Contacto;

public class AgendaService {
	List<Contacto> misContactos = new ArrayList<>();
	String ruta = "prueba.txt";
	String ruta2 = "prueba_bis.txt";
	static StringBuilder nombre = new StringBuilder("");
	static StringBuilder email = new StringBuilder("");
	static StringBuilder telefono = new StringBuilder("");
	
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
	
	public boolean agregarContacto (String nombreNuevo, String emailNuevo, String telNuevo) {
		//if (!existeContacto(c1.getEmail())) {
			/*nombre = nombre.append(nombreNuevo);
			nombre = nombre.append("--");
			email = email.append(emailNuevo);
			email = email.append("--");
			telefono = telefono.append(telNuevo);
			telefono = telefono.append("--");
			*/
			try(FileOutputStream fos = new FileOutputStream(ruta, true);
					PrintStream out = new PrintStream(fos)){
				out.println(nombreNuevo+"--"+emailNuevo+"--"+telNuevo);
			} catch (FileNotFoundException ex){
				System.out.println("El directorio es incorrecto.");
				ex.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		//}
		//return false;
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
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr)){
					String linea=br.readLine();
					while(null!=linea) {
						System.out.println(linea);
						linea=br.readLine();
					}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean eliminarContacto (String emailAEliminar) {
		try(FileOutputStream fos = new FileOutputStream(ruta2, true);
				PrintStream out = new PrintStream(fos);
				FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr)){
			String linea=br.readLine();
			while(null!=linea) {
				out.println(linea);
				linea=br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileReader fr2 = new FileReader(ruta2);
				BufferedReader br = new BufferedReader(fr2);
				){
					String linea=br.readLine();
					FileOutputStream fos = new FileOutputStream(ruta, false);
					PrintStream out = new PrintStream(fos);	
					while(null!=linea) {						
						linea=br.readLine();
						if (!linea.contains(emailAEliminar)) {
							out.println(linea);
							continue;
						}
					} out.close(); 					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void existeContacto(String emailComprobar) {		
		String[] nombresSeparados = nombre.deleteCharAt(nombre.length() - 1).toString().split("--");
		String[] emailSeparados = email.deleteCharAt(email.length() - 1).toString().split("--");
		String[] telSeparados = telefono.deleteCharAt(telefono.length() - 1).toString().split("--");
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr)){
					String linea=br.readLine();
					int nroLinea=0;					
					while(null!=linea) {
						System.out.println(linea);
						linea=br.readLine();
						
						++nroLinea;
					}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
