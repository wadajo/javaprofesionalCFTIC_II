package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;

public class PedidosService {
	//private List<Pedido> pedidos = new ArrayList<>(); ya no necesito Lista a nivel atributo
	private final String RUTA="pedidos.txt";
	private final String SEPARADOR = "-";
	private double facturacion=0.0;
	
	public 	boolean agregarPedido (Pedido p1) {
		if (!existePedido(p1)) {
			try(FileOutputStream fos = new FileOutputStream(RUTA, true);
					PrintStream out = new PrintStream(fos)){
				out.println(p1.getId()+SEPARADOR+p1.getProducto()+SEPARADOR+p1.getPrecio());
				return true;
			} catch (FileNotFoundException ex){
				System.out.println("El directorio es incorrecto. Se ha creado.");
				crearFichero();
				ex.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
		
	private boolean existePedido (Pedido p1) {
		try (FileReader fr = new FileReader(RUTA);
				BufferedReader br= new BufferedReader(fr)){			
			String linea;
			while ((linea=br.readLine())!=null) {
				// el método split devolverá un array de Strings para cada línea (cada Pedido)
				// ; de ahí, saco el primer elemento
				// (el 0), que sé que es el ID; lo parseo a Int
				String id=linea.split(SEPARADOR)[0];
				if (id.equalsIgnoreCase(p1.getId())) {
					System.out.println("Ese ID ya ha sido utilizado por otro producto.");
					return true;
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Pedido> mostrarPendientes() {
		List<Pedido> lista = new ArrayList<>();
		try (FileReader fr = new FileReader(RUTA);
				BufferedReader br= new BufferedReader(fr)){			
			String linea;
			while ((linea=br.readLine())!=null) {
				String[] datos=linea.split(SEPARADOR);
				lista.add(new Pedido(datos[0],datos[1],Integer.parseInt(datos[2])));				
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista; 
	}
	
	public double facturacionPendiente(List<Pedido> pedidos) {
		facturacion=0.0; 
		mostrarPendientes().forEach(p->facturacion+=p.getPrecio());
		return facturacion;
	}
	
	public double facturacionPendiente_V2() {
		facturacion=0.0; 
		try (FileReader fr = new FileReader(RUTA);
				BufferedReader br= new BufferedReader(fr)){			
			String linea;
			while ((linea=br.readLine())!=null) {
				int precio=Integer.parseInt(linea.split(SEPARADOR)[2]);
				facturacion+=precio;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return facturacion;
	}
	
	public boolean eliminarPedidos (String producto) {
		List<Pedido> lista = new ArrayList<>();
		lista=mostrarPendientes();
		lista.removeIf(p->p.getProducto().contains(producto));
		volcarPedidos(lista);
		return false;
	}
	
	public Pedido procesarPedido () {
		List<Pedido> lista = mostrarPendientes();
		Pedido procesado=null;
		if (!lista.isEmpty()) {
			procesado=lista.remove(0);
		}		
		volcarPedidos(lista);
		return procesado;
	}
	
	private Pedido obtenerPedido (String idAObtener) {
		/* también se podría hacer con BufferedReader, sin crear lista, viendo línea por línea
		// si el ID coincide con la posición 0 del String[] que obtenemos
		// Es más prolijo no utilizar List si hay otra opción (por ej. acá que no hay reescritura).
		*/
		List<Pedido> pedidos = mostrarPendientes();		
		for(Pedido p:pedidos) {
			if(p.getId().equalsIgnoreCase(idAObtener)) {
				return p;
			}
		}
		return null;
	}
	
	private Pedido obtenerPedido_V2 (String idAObtener) {
		Pedido buscado=null;		
		try (FileReader fr = new FileReader(RUTA);
				BufferedReader br= new BufferedReader(fr)){			
			String linea;
			while ((linea=br.readLine())!=null) {
				String[] datos=linea.split(SEPARADOR);
				if (idAObtener.equals(datos[0])) {
				buscado=new Pedido(datos[0],datos[1],Integer.parseInt(datos[2]));
				return buscado;
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Pedido priorizarPedido (String pNuevo) {
		Pedido prioritario,anterior;
		prioritario=obtenerPedido(pNuevo);
		List<Pedido> pedidos = mostrarPendientes();	
		if(prioritario!=null) {
			int pos=pedidos.indexOf(prioritario);
			//confirmo que no esté ya en la primera posición
			if(pos>0) {
				//se cambia por el auxiliar, que va en su lugar, menos prioritario
				anterior=pedidos.get(pos-1);
				pedidos.set(pos-1, prioritario);
				pedidos.set(pos, anterior);
			}
		}
		volcarPedidos(pedidos);
		return prioritario;
	}
	
	private void volcarPedidos (List<Pedido> pedidos) {
		try(FileOutputStream fos = new FileOutputStream(RUTA, false); //sobreescritura del txt
				PrintStream out = new PrintStream(fos)){
			pedidos.forEach(p->out.println(p.getId()+SEPARADOR+
										p.getProducto()+SEPARADOR+
										p.getPrecio()));			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void crearFichero() {
		try (PrintStream out = new PrintStream(RUTA)){
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}