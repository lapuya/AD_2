package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidades.Coche;
import modelo.entidades.Pasajero;
import modelo.persistencia.interfaces.DaoPasajero;

public class DaoPasajeroMySql implements DaoPasajero {
	
	private Connection conexion;

	
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/bbdd";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean alta(Pasajero p) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into pasajeros (NOMBRE,EDAD,PESO) "
				+ " values(?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setDouble(3, p.getPeso());

			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + p);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}
	
	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from pasajeros where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
		} catch (SQLException e) {
			borrado = false;
			System.out.println("baja -> No se ha podido dar de baja el pasajero con"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 
	}
	
	@Override
	public Pasajero obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Pasajero pasajero = null;
		
		String query = "select ID,NOMBRE,EDAD,PESO from pasajeros "
				+ "where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el "
					+ "pasajero con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return pasajero;
	}

	@Override
	public List<Pasajero> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> pasajeros = new ArrayList<>();
		
		String query = "select ID,NOMBRE,EDAD,PESO from pasajeros";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));

				pasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros.");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return pasajeros;
	}
	
	public boolean altaACoche(int idPasajero, int idCoche) {
		if(!abrirConexion()){
			return false;
		}	
		
		boolean alta = true;
		String query = "UPDATE pasajeros SET COCHE_ID = ? WHERE id = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ps.setInt(1, idCoche);
			ps.setInt(2, idPasajero);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
			
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros.");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return alta;
	}
}
