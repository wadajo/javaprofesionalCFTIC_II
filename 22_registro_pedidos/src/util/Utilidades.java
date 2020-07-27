package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import model.Pedido;
import model.PedidoTienda;

public class Utilidades {
	public static SimpleDateFormat personalizado = new SimpleDateFormat("dd/MM/yyyy",new Locale("es","ES"));
	
	public static Pedido devolverPedidoDsdString (String pedidoEnCadena, String separador) {
		String[] datos = pedidoEnCadena.split(separador);		
		try {
			return new Pedido(datos[0],Integer.parseInt(datos[1]),Double.parseDouble(datos[2]),datos[3],personalizado.parse(datos[4]));
		} catch (NumberFormatException e) {			
			e.printStackTrace();
			return null;
		} catch (ParseException e) {			
			e.printStackTrace();
			return null;
		}		
	}
	
	public static String devolverPedidoDsdPedido (Pedido objetoPedido, String separador) {		
			return objetoPedido.getProducto()+separador+objetoPedido.getUnidades()+separador+objetoPedido.getPrecioUnitario()+separador+objetoPedido.getSeccion()+separador+personalizado.format(objetoPedido.getFecha());		
	}
	
	public static String devolverPedidoTiendaDsdPedido (PedidoTienda objetoPedido, String separador) {		
			return objetoPedido.getTienda()+separador+devolverPedidoDsdPedido(objetoPedido,separador);	
		
}
	
	public static LocalDate devolverEnLocalDate (Date vieja) {
		return Instant.ofEpochMilli(vieja.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDateTime devolverEnLocalDateTime (Date vieja) {
		return Instant.ofEpochMilli(vieja.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
	}
	
	public static Date devolverEnDate (LocalDate nueva) {		
		return Date.from(nueva.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
