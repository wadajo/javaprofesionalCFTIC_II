package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Cuenta;
import model.Movimiento;
import model.Titular;

public class GestorBBDD {
	public boolean crearTitular(Titular titular) {
		try (Connection conn=Datos.getConnection()){
			String sql="INSERT INTO bancabd.clientes VALUES (?,?,?,?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, titular.getDni());
			ps.setString(2, titular.getNombre());
			ps.setString(3, titular.getDireccion());
			ps.setInt(4, titular.getTelefono());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean crearCuenta(Cuenta nueva) {
		try (Connection conn=Datos.getConnection()){
			String sql1="INSERT INTO bancabd.cuentas VALUES (?,?,?)";
			String sql2="INSERT INTO bancabd.titulares VALUES (?,?)";
			String sql3="INSERT INTO bancabd.clientes VALUES (?,?,?,?)";
			
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, nueva.getId());
			ps1.setInt(2, 0);
			ps1.setString(3, nueva.getTipoCuenta());
			ps1.execute();
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, nueva.getId());
			ps2.setInt(2, nueva.getT().getDni());
			ps2.execute();
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps3.setInt(1, nueva.getT().getDni());
			ps3.setString(2, nueva.getT().getNombre());
			ps3.setString(3, nueva.getT().getDireccion());
			ps3.setInt(4, nueva.getT().getTelefono());
			ps3.execute();			
				return true;				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("No se ha podido crear la cuenta, inténtalo de nuevo.");
		return false;
	}
	public boolean crearCuenta(Cuenta nueva, Titular existente) {
		try (Connection conn=Datos.getConnection()){
			String sql1="INSERT INTO bancabd.cuentas VALUES (?,?,?)";
			String sql2="INSERT INTO bancabd.titulares VALUES (?,?)";			
			
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, nueva.getId());
			ps1.setInt(2, 0);
			ps1.setString(3, nueva.getTipoCuenta());
			ps1.execute();
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, nueva.getId());
			ps2.setInt(2, nueva.getT().getDni());
			ps2.execute();			
				return true;				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("No se ha podido crear la cuenta, inténtalo de nuevo.");
		return false;
	}
	public boolean sumarTitularACuenta(Titular t, Cuenta existente) {
		try (Connection conn=Datos.getConnection()){			
			String sql="INSERT INTO bancabd.titulares VALUES (?,?)";
						
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, existente.getId());
			ps.setInt(2, t.getDni());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	// TODO no funciona, pensar
	public List<Cuenta> mostrarCuentas () {
		List<Cuenta> cuentas = new ArrayList<>();
		List<Titular> titulares=new ArrayList<>();
		int id=0;
		int saldo=0;
		String tipoCuenta="";
		try (Connection conn=Datos.getConnection()){
			String sql="SELECT idCuenta AS id,dni,nombre,direccion,telefono,saldo,tipocuenta "
					+ "FROM bancabd.titulares INNER JOIN bancabd.clientes ON clientes.dni=titulares.idCliente "
					+ "INNER JOIN bancabd.cuentas ON cuentas.numeroCuenta=titulares.idCuenta";
			PreparedStatement ps = conn.prepareStatement(sql);						
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Titular t1 = new Titular(rs.getInt("dni"),rs.getString("nombre"),rs.getString("direccion"),rs.getInt("telefono"));
				id=id==rs.getInt("id")?id:rs.getInt("id");
				if (!titulares.contains(t1)&&id==rs.getInt("id")) {
					titulares.add(t1);
					continue;
				}				
				saldo=saldo==rs.getInt("saldo")?saldo:rs.getInt("saldo");
				tipoCuenta=tipoCuenta==rs.getString("tipocuenta")?tipoCuenta:rs.getString("tipocuenta");				
				Cuenta unaCuenta = new Cuenta(id,titulares,saldo,tipoCuenta);
				cuentas.add(unaCuenta);
			}
			return cuentas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	public Cuenta existeCuenta (int cuenta){
		int saldo=0;
		String tipoCuenta="";
		List<Titular> titulares=new ArrayList<>();		
		try (Connection conn=Datos.getConnection()){
			String sql="SELECT numeroCuenta,saldo,tipocuenta,idCliente FROM bancabd.cuentas INNER JOIN bancabd.titulares \n" + 
					"ON cuentas.numeroCuenta=titulares.idCuenta WHERE idCuenta=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cuenta);			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				saldo=rs.getInt("saldo");
				tipoCuenta=rs.getString("tipocuenta");
				var dni=rs.getInt("idCliente");
				titulares.add(recuperarTitular(dni));													
			}	
			return new Cuenta(cuenta,titulares,saldo,tipoCuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean ingresar (int cuenta, int dinero) {
		Movimiento m1 = new Movimiento(cuenta,LocalDateTime.now(),dinero,"ingreso");
		try (Connection conn=Datos.getConnection()){			
			modificarSaldo(m1);					
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	
	public boolean retirar (int cuenta, int dinero) {
		try (Connection conn=Datos.getConnection()){
		if (comprobarSaldoSuficiente(cuenta, dinero)) {
			Movimiento m1 = new Movimiento(cuenta,LocalDateTime.now(),dinero,"extracción");
				modificarSaldo(m1);
				return true;
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		System.out.println("No dispone de saldo suficiente.");
		return false;
	}
	public List<Movimiento> mostrarUltimos (int cuenta) {
		try (Connection conn=Datos.getConnection()){
			List<Movimiento> ultimosMov = new ArrayList<>();
			String sql="SELECT * FROM bancabd.movimientos WHERE idCuenta=? "
					+ "AND fecha>? ORDER BY fecha DESC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cuenta);
			ps.setString(2, java.sql.Date.valueOf(LocalDate.now().minusDays(30)).toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ultimosMov.add(new Movimiento(cuenta,
						LocalDateTime.parse(rs.getString("fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						rs.getInt("cantidad"),
						rs.getString("operacion")));
			}
			return ultimosMov;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Movimiento>();
	}
	public boolean transferir(int origen, int destino, int dinero) {
		try (Connection conn=Datos.getConnection()){
		if (comprobarSaldoSuficiente(origen, dinero)) {
			Movimiento m1 = new Movimiento(origen,LocalDateTime.now(),dinero,"transferencia-extracción");
			modificarSaldo(m1);
			Movimiento m2 = new Movimiento(destino,LocalDateTime.now(),dinero,"transferencia-ingreso");
			modificarSaldo(m2);
			return true;
			} 
		} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}
	public int saldoActual(int cuenta) {
		int saldoActual=0;
		String sql="SELECT saldo FROM bancabd.cuentas WHERE numeroCuenta=?";
		try(Connection conn=Datos.getConnection()){
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, cuenta);
		ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				saldoActual=rs.getInt("saldo");
			}
		return saldoActual;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public Titular recuperarTitular(int dni) {
		Titular t1 = null;
		try (Connection conn=Datos.getConnection()){
			String sql="SELECT nombre,direccion,telefono FROM bancabd.clientes "
					+ "WHERE dni=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dni);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				t1 = new Titular(
						dni,
						rs.getString("nombre"),
						rs.getString("direccion"),
						rs.getInt("telefono"));
			}
			return t1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	private boolean comprobarSaldoSuficiente (int cuenta, int retira) throws SQLException {
		if (saldoActual(cuenta)>retira) return true;
		else return false;
	}
	private boolean registrarMovimiento (Movimiento m) throws SQLException {
		Connection conn=Datos.getConnection();
		String sql="INSERT INTO bancabd.movimientos "
				+ "(idCuenta,fecha,cantidad,operacion) VALUES (?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, m.getCuenta());
		ps.setString(2, m.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		ps.setInt(3, m.getDinero());
		ps.setString(4, m.getOperacion());
		if (ps.execute())
			return true;
		return false;
	}
	private boolean modificarSaldo (Movimiento m) throws SQLException {
		Connection conn=Datos.getConnection();
		String sql="UPDATE bancabd.cuentas SET saldo=? WHERE numeroCuenta=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, m.getOperacion().equals("ingreso")||m.getOperacion().equals("transferencia-ingreso")? 
				saldoActual(m.getCuenta())+m.getDinero():
				saldoActual(m.getCuenta())-m.getDinero());			
		ps.setInt(2, m.getCuenta());
		ps.execute();
		if (registrarMovimiento(m))
			return true;
		return false;			
	}
	
	

		
}
