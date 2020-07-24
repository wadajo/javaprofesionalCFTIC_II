package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Pedido;
import util.Utilidades;

public class GestorPedidos  {
	private final String RUTA="pedidos.txt";
	private final String SEPARADOR = "-";
	private Path path;
	private SimpleDateFormat personalizado = new SimpleDateFormat("dd/MM/yyyy",new Locale("es","ES"));
	
	public GestorPedidos() {
		path=Paths.get(RUTA);
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//almacena el pedido recibido
	public void grabarPedido(Pedido pedido) {
		try {
			Files.writeString(path, 
							Utilidades.devolverPedidoDsdPedido(pedido, SEPARADOR)+System.lineSeparator(), 
							StandardCharsets.UTF_8,
							StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();				
		}
	}	
	
	//media de ventas pedidos de esa seccion
	public double promedioSeccion(String seccion) {
		return streamPedidos()
		.filter(p->p.getSeccion().equalsIgnoreCase(seccion))			
		.collect(Collectors.averagingDouble(p->p.getSubtotal()));
	}

	//ventas totales de el producto indicado
	public double totalProducto(String producto) {
		return streamPedidos()
				.filter(p->p.getProducto().equalsIgnoreCase(producto))
				.collect(Collectors.summarizingDouble(p->p.getSubtotal()))
				.getSum();
	}

	//devuelve el pedido con venta superior	
	public Pedido pedidoSuperior() {
		return streamPedidos()
				.max((p1,p2)->(p1.getSubtotal()<p2.getSubtotal()?-1:1))
				.orElse(null);
	}

	//devuelve lista de pedidos de una secci�n
	public List<Pedido> pedidosSeccion(String seccion) { 
			return	streamPedidos()
				.filter(p->p.getSeccion().equalsIgnoreCase(seccion))
				.collect(Collectors.toList());
	}
	
	//devuelve el pedido con fecha m�s reciente
	public Pedido pedidoMasReciente() {
		return streamPedidos()
				.max((t1,t2)->t1.getFecha().compareTo(t2.getFecha()))
				// .max((t1,t2)->(t1.getFecha().before(t2.getFecha()))?-1:1);
				// .max((t1,t2)->(t1.getFecha().getTime()<t2.getFecha().getTime()?-1:1))
				.orElse(null);
		
	}
	//devuelve lista de pedidos, posteriores a la fecha indicada
	public List<Pedido> pedidosPosterioresFecha(Date fecha) {
		return streamPedidos()
				.filter(p->p.getFecha().after(fecha))
				.collect(Collectors.toList());
	}
	
	
	//lista de nombres de secci�n, no repetidas
	public List<String> secciones() { 
			return	streamPedidos()
					//.map(p->p.getSeccion())
					.map(Pedido::getSeccion)
					.distinct()
					.collect(Collectors.toList());		
	}
	
	public List<Pedido> pedidosRangoFecha(LocalDate fechaInicial, Period periodo) {
		LocalDate fechaFinal = fechaInicial.plus(periodo);
		return streamPedidos()
				.filter(p->p.getFecha().after((Utilidades.devolverEnDate(fechaInicial)))&&p.getFecha().before((Utilidades.devolverEnDate(fechaFinal))))
				.collect(Collectors.toList());
		
	}
	
	private Stream<Pedido> streamPedidos(){
		try {
			return Files.lines(path,StandardCharsets.UTF_8)
					.map(p->(Utilidades.devolverPedidoDsdString(p, SEPARADOR)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
