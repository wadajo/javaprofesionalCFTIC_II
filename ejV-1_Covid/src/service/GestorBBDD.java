package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import model.Caso;

public class GestorBBDD {
	private SimpleDateFormat personalizado = new SimpleDateFormat("dd-MM",new Locale("es","ES"));
	
	public boolean agregarCaso (Caso c1) {
		try (Connection conn=Datos.getConnection()){
			String sql = "INSERT INTO covid2020.registros (comunidad,fecha,positivos)"
					+ "VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c1.getComunidad());
			ps.setDate(2, new java.sql.Date(c1.getFecha().getTime()));
			ps.setInt(3, c1.getPositivos());			
			ps.execute();
			return true;
		} catch (SQLIntegrityConstraintViolationException ex) {
			System.out.println("No se cargó el registro del día "+personalizado.format(c1.getFecha())+" en "
					+c1.getComunidad()+" porque ya estaba en la Base.");	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	/*
	public boolean recorrerFichero () {
		
	}*/
	
	public boolean opcion1 (String ruta) {
		if (ruta.endsWith("json")) {
				BaseService service  = new JsonService(ruta);
				List<Caso> registros=service.getStream()
					.collect(Collectors.toList());
				registros.forEach(p->agregarCaso(p));
				return true;
			} else if (ruta.endsWith("csv")) {
				BaseService service  = new CsvService(ruta);
				List<Caso> registros=service.getStream()
					.collect(Collectors.toList());
				registros.forEach(p->agregarCaso(p));
				return true;
			} else {
				System.out.println("No se pudo reconocer el archivo.");
			}
		return false;
	}
}
