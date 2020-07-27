package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Pedido;
import model.PedidoTienda;
import util.Utilidades;

public class GestorPedidosTotales {
	private final String RUTA="pedidos_totales.txt";	
	private final String SEPARADOR = "-";
	private Path path;
	
	public GestorPedidosTotales() {
		path=Paths.get(RUTA);
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*  
	 * 	Clase Tarea:
	 * 	recibe nombre de la tienda y dirección de su fichero parcial 
	 * 	y va a llevar los de la fecha actual
	 *  al pedido global utilizando las clases de este método
	 */
	
	public void grabarPedidosTotales(List<PedidoTienda> pedidos) {
		pedidos.forEach(p->
		{
		try {
			Files.writeString(path, 
							Utilidades.devolverPedidoTiendaDsdPedido(p, SEPARADOR)+System.lineSeparator(), 
							StandardCharsets.UTF_8,
							StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();				
		}
		});
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
