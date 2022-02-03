package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidades.Pasajero;

/*
 * Interfaz del DAO pasajero
 */
public interface DaoPasajero {
	
	public boolean alta(Pasajero p);
	public boolean baja(int id);
	public Pasajero obtener(int id);
	public List<Pasajero> listar();
	public boolean altaACoche(int idPasajero, int idCoche);
	public boolean pasajeroEnCoche(int idPasajero, int idCoche);
	public boolean BajaDeCoche(int id);
	public List<Pasajero> listarPasajerosEnCoche(int id);


}
