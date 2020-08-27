package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Movimiento;

public class GestorBBDD {
	public boolean existeCuenta (int cuenta){
		try (Connection conn=Datos.getConnection()){
			String sql="SELECT * FROM bancabd.cuentas WHERE numeroCuenta=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cuenta);			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return true;	
			}					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	
	public boolean ingresar (int cuenta, int dinero) {
		Movimiento m1 = new Movimiento(cuenta,LocalDateTime.now(),dinero,"ingreso");
		try (Connection conn=Datos.getConnection()){			
			modificarSaldo(m1);
			//registrarMovimiento(m1);						
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
				//registrarMovimiento(m1);
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
