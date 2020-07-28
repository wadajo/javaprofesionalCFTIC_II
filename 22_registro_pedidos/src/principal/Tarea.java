package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.Pedido;
import model.PedidoTienda;
import service.GestorPedidos;
import service.GestorPedidosTotales;

public class Tarea implements Runnable {
	String nombreTienda;
	String ruta=""; //antes daba NullPointerException
	static Lock lc=new ReentrantLock();
	
	public Tarea(String nombreTienda, String ruta) {
		super();
		this.nombreTienda = nombreTienda;
		this.ruta = ruta;
	}
	
	GestorPedidosTotales servicio = new GestorPedidosTotales();
	GestorPedidos servicioParcial = new GestorPedidos(ruta);
	
	@Override
	public void run() {		
		lc.lock();
		ejecutar(nombreTienda);	
		lc.unlock();
	}
	
	private void ejecutar (String nombreTienda) {
		List<Pedido> listaHoy=servicioParcial.pedidosFechaActual();
		List<PedidoTienda> listaPedidosTienda = new ArrayList<>();
		listaHoy.forEach(p->{			
			listaPedidosTienda.add(new PedidoTienda(nombreTienda,
					p.getProducto(), 
					p.getUnidades(), 
					p.getPrecioUnitario(), 
					p.getSeccion(), 
					p.getFecha()));
		});
		servicio.grabarPedidosTotales(listaPedidosTienda);
	}
	
}
