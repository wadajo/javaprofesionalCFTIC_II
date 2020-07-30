package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Datos {
	static String driver;
	static String cadenaConexion;
	static String user;
	static String password;
	static String FILE="credenciales.json";
	
	//en el bloque estático debe leerse el JSON
	static {
		try {
			cargarPropiedades();
		} catch (JsonIOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonSyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(cadenaConexion,user,password);
	}
	
	private static void cargarPropiedades() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonObject conn=JsonParser.parseReader(new FileReader(FILE)).getAsJsonObject();
		driver = conn.get("driver").getAsString();
		cadenaConexion = conn.get("cadenaConexion").getAsString();
		user = conn.get("user").getAsString();
		password = conn.get("password").getAsString();
	}
}
