package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contacto;

public class BaseServicio {
	
	public boolean insertarContacto(Contacto c1) {
		try (Connection conn=Datos.getConnection()){
			String sql= "INSERT INTO miscontactos.contactos (nombre,email,edad) VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c1.getNombre());
			ps.setString(2, c1.getEmail());
			ps.setInt(3, c1.getEdad());			
			ps.execute();
			return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	public boolean eliminarContacto(Contacto c1) {
		try (Connection conn=Datos.getConnection()){			
				String sql= "DELETE FROM miscontactos.contactos WHERE email = ? ";
				PreparedStatement ps = conn.prepareStatement(sql);			
				ps.setString(1, c1.getEmail());	
				return(ps.executeUpdate()!=0);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public Contacto buscarContacto (String email) {
		try (Connection conn=Datos.getConnection()){	
			Contacto nuevo=new Contacto();
			String sql= "SELECT nombre,email,edad FROM miscontactos.contactos WHERE email=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				nuevo.setNombre(rs.getString("nombre"));
				nuevo.setEmail(rs.getString("email"));
				nuevo.setEdad(rs.getInt("edad"));
			}
				return nuevo;			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public List<Contacto> mostrarContactos(){
		List<Contacto> contactos=new ArrayList<>();
		try (Connection conn=Datos.getConnection()){	
			
			String sql= "SELECT * FROM miscontactos.contactos";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Contacto nuevo=new Contacto();
				nuevo.setId(rs.getInt("idcontacto"));
				nuevo.setNombre(rs.getString("nombre"));
				nuevo.setEmail(rs.getString("email"));
				nuevo.setEdad(rs.getInt("edad"));
				contactos.add(nuevo);
			}
				return contactos;			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	
}
