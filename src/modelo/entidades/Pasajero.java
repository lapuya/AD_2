package modelo.entidades;

public class Pasajero {
	
	private int id, edad;
	private Double peso;
	private String nombre;
	
	public Pasajero(int edad, Double peso, String nombre) {
		this.edad = edad;
		this.peso = peso;
		this.nombre = nombre;
	}

	public Pasajero() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Pasajero [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", peso=" + peso +  "]";
	}
}
