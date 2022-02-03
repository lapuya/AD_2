package modelo.entidades;

import java.util.Objects;

public class Coche {
	private String matricula, marca, modelo, color;
	private int id;
	
	//Constructor
	public Coche(String matricula, String marca, String modelo, String color) {
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
	}
	
	public Coche() {
		// TODO Auto-generated constructor stub
	}

	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Coche [id=" + id + ", matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", color="
				+ color + "]";
	}
	
	//Métodos equals y hashCode para la comparación de objetos (no pueden coincidir en id ni matricula)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coche coche = (Coche) o;

        return this.id == coche.id || this.getMatricula().equals(coche.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricula, marca, modelo, color);
    }

}