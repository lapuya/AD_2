package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidades.Coche;

/*
 * Interfaz del DAO Coche
 */
public interface DaoCoche {
	public boolean alta(Coche c);
	public boolean baja(int id);
	public boolean modificar(Coche c);
	public Coche obtener(int id);
	public List<Coche> listar();

}
