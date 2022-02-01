package controlador;

import java.util.List;
import java.util.Scanner;

import modelo.entidades.Coche;


import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.interfaces.DaoCoche;

public class Controller {
	
	DaoCoche dc = new DaoCocheMySql();
	
	public void run() {
		
		int op;
		
		op = menu();
		
		while (op != 6) {
			switch (op)
			{
				case 1:
					añadirCoche();
					break;
				case 2:
					borrarCoche();
					break;
				case 3:
					consultarCoche();
					break;
				case 4:
					modificarCoche();
					break;
				case 5:
					listarCoches();
					break;
			}
			op = menu();
		}
		
		
	}

	private void listarCoches() {
		List<Coche> coches = dc.listar();
		
		for (Coche c : coches)
			System.out.println(c);
		
	}

	private void consultarCoche() {
		Scanner sc = new Scanner (System.in);
		int idBuscar;
		Coche c;
		
		System.out.println("Introduzca el ID del coche a buscar: ");
		idBuscar = sc.nextInt();
		
		c = dc.obtener(idBuscar);
		if (c == null)
			System.out.println("El coche con ID " + idBuscar + " NO se encuentra en la BBDD");
		else 
			System.out.println(c);
	}

	private void modificarCoche() {
		Scanner sc = new Scanner (System.in);
		int idBuscar;
		Coche c;
		
		System.out.println("Introduzca el ID del coche a modificar: ");
		idBuscar = sc.nextInt();
		
		c = dc.obtener(idBuscar);
		if (c == null)
			System.out.println("El coche con ID " + idBuscar + " NO se encuentra en la BBDD");
		else {
			System.out.println("Introduzca la nueva matricula: ");
			c.setMatricula(sc.nextLine()); 
			System.out.println("Introduzca la nueva marca: ");
			c.setMarca(sc.nextLine());
			System.out.println("Introduzca el nuevo modelo: ");
			c.setModelo(sc.nextLine());
			System.out.println("Introduzca el nuevo color: ");
			c.setColor(sc.nextLine());
			
			dc.modificar(c);

		}
		
	}

	private void borrarCoche() {
		Scanner sc = new Scanner (System.in);
		int idBorrar;
		
		System.out.println("Introduzca el id del coche a borrar: ");
		idBorrar = sc.nextInt();
		if (dc.baja(idBorrar)) 
			System.out.println("Se ha borrado con éxito de la BBDD");
		else
			System.out.println("NO se ha podido borrar el elemento de la BBDD");
	}

	private void añadirCoche() {
		String matricula, marca, modelo, color;
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Introduzca la matricula del coche:");
		matricula = sc.nextLine();
		System.out.println("Introduzca la marca del coche: ");
		marca = sc.nextLine();
		System.out.println("Introduzca el modelo del coche: ");
		modelo = sc.nextLine();
		System.out.println("Introduzca el color del coche: ");
		color = sc.nextLine();
				
		Coche c = new Coche (matricula, marca, modelo, color);

		if (dc.alta(c))
			System.out.println("Coche añadido a la BBDD correctamente");
		 else 
			System.out.println("Se ha producido un ERROR al añadir el coche a la BBDD");
	}

	private int menu() {
		int op;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("---------- GESTOR DE COCHES ----------");
		System.out.println("1. Añadir nuevo coche.");
		System.out.println("2. Borrar coche por ID.");
		System.out.println("3. Consulta coche por ID.");
		System.out.println("4. Modificar coche por ID.");
		System.out.println("5. Listar coches");
		System.out.println("6. Terminar el programa.");
		op = sc.nextInt();
		
		while (op < 1 || op > 6) {
			System.out.println("ERROR EN LA ELECCION. ELIJA UN VALOR ENTRE 1 Y 5");
			System.out.println("1. Añadir nuevo coche.");
			System.out.println("2. Borrar coche por ID.");
			System.out.println("3. Consulta coche por ID.");
			System.out.println("4. Modificar coche por ID.");
			System.out.println("5. Listar coches");
			System.out.println("6. Terminar el programa.");
			
			op = sc.nextInt();

		}
		return op;
	}
	
	
}
