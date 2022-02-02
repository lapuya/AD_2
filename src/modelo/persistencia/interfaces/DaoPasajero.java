package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidades.Pasajero;

public interface DaoPasajero {
	
	public boolean alta(Pasajero p);
	public boolean baja(int id);
	public Pasajero obtener(int id);
	public List<Pasajero> listar();
	public boolean altaACoche(int idPasajero, int idCoche);


}
