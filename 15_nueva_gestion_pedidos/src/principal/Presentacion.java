package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import model.Pedido;
import service.GestorPedidos;

public class Presentacion {

	public static void main(String[] args) throws ParseException {
		String[] fechas= {"11/11/2002","12/11/2022","21/11/2015","22/11/2013","31/12/2002",};
		SimpleDateFormat personalizado = new SimpleDateFormat("dd/MM/yyyy");
		
		GestorPedidos servicio = new GestorPedidos();
		/*
		servicio.grabarPedido(new Pedido ("Cama",3,255.5,"Hogar",personalizado.parse(fechas[0])));
		servicio.grabarPedido(new Pedido ("Lámpara",1,15.9,"Hogar",personalizado.parse(fechas[1])));
		servicio.grabarPedido(new Pedido ("Bicicleta",1,95.5,"Deportes",personalizado.parse(fechas[2])));
		servicio.grabarPedido(new Pedido ("Balón",2,5.9,"Deportes",personalizado.parse(fechas[3])));
		servicio.grabarPedido(new Pedido ("Toallón",1,1.5,"Baño",personalizado.parse(fechas[4])));
		/*
		servicio.grabarPedido(new Pedido ("Cama",3,255.5,"Hogar",personalizado.parse(fechas[0])));
		servicio.grabarPedido(new Pedido ("Lámpara",1,15.9,"Hogar",personalizado.parse(fechas[1])));
		servicio.grabarPedido(new Pedido ("Bicicleta",1,95.5,"Deportes",personalizado.parse(fechas[2])));
		servicio.grabarPedido(new Pedido ("Balón",2,5.9,"Deportes",personalizado.parse(fechas[3])));
		servicio.grabarPedido(new Pedido ("Toallón",1,1.5,"Baño",personalizado.parse(fechas[4])));
		*/
		/*
		servicio.pedidosPosterioresFecha(personalizado.parse("20/04/1995")).forEach(p->System.out.println(p.getProducto()));
		servicio.pedidosSeccion("Hogar").forEach(p->System.out.println(p.getProducto()));;
		*/
		String fecha= "01/10/2012";
		Date fechaEnDate = personalizado.parse(fecha);
		DateTimeFormatter formato=DateTimeFormatter.ofPattern("dd/MM/uuuu");
		LocalDate fechaEnLD = LocalDate.parse(fecha, formato);
		//LocalDate fechaEnLD = LocalDate.ofInstant(fechaEnDate.toInstant(), ZoneId.systemDefault());
		System.out.println(fechaEnDate);
		System.out.println(fechaEnLD);
		servicio.pedidosRangoFecha(fechaEnLD, Period.ofYears(5))
			.forEach(p->System.out.println("Pedidos registrados entre "+fechaEnLD+" y 5 años después: "+p.getProducto()));
	}

}
