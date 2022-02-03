package controlador;

import java.util.List;
import java.util.Scanner;

import modelo.entidades.Coche;
import modelo.entidades.Pasajero;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.DaoPasajeroMySql;
import modelo.persistencia.interfaces.DaoCoche;
import modelo.persistencia.interfaces.DaoPasajero;

public class Controller {
	
	//Atributos DAO de los coches y de los pasajeros
	DaoCoche dc = new DaoCocheMySql();
	DaoPasajero dp = new DaoPasajeroMySql();
	
	
	//Método run -> gestiona el menu principal
	public void run() {
		int op;
		
		op = menu();
		while (op != 7) {
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
				case 6:
					gestionarPasajeros();
					break;
			}
			op = menu();
		}
	}

	//Ofrece el menú principal de los pasajeros
	private void gestionarPasajeros() {
		int op;
		
		op = menuPasajeros();
		while (op != 8) {
			switch (op) {
				case 1:
					añadirPasajero();
					break;
				case 2:
					borrarPasajero();
					break;
				case 3:
					consultarPasajero();
					break;
				case 4:
					listarPasajeros();
					break;
				case 5:
					añadirPasajeroACoche();
					break;
				case 6:
					eliminarPasajeroDeCoche();
					break;
				case 7:
					listarPasajerosDeCoche();
					break;
			}
			op = menuPasajeros();
		}
	}
	
	// ------------------------------------- MÉTODOS REQUISITO 2 ---------------------------------------------------------------
	
	//Pide el id de un coche y, si existe, lista sus pasajeros (opcion 7 del menu de pasajeros)
	private void listarPasajerosDeCoche() {
		Scanner sc = new Scanner(System.in);
		int idCoche;
		Coche c;
		
		System.out.println("Introduce el id del coche cuyos pasajeros quieres saber: ");
		idCoche = sc.nextInt();
		c = dc.obtener(idCoche);
		if (c == null)
			System.out.println("Este coche no está en la BBDD.");
		else {
			System.out.println("Los pasajeros de este coche son: ");
			listarSusPasajeros(c.getId());
		}
		
	}

	//DAO de pasajeros obtiene la lista con el id. Si la lista no es null, tiene pasajeros y los imprime por pantalla
	private void listarSusPasajeros(int id) {
		List<Pasajero> pasajeros = dp.listarPasajerosEnCoche(id);
		if (pasajeros != null) {
			for (Pasajero p : pasajeros)
				System.out.println(p);
		} else
			System.out.println("Este coche no tiene pasajeros.");
		
	}
	
	//Elimina los pasajeros de un coche -> se lista todos los coches que tengan pasajeros y se llama al metodo
	//eliminarPasajero() que se encargará de eliminar como tal
	private void eliminarPasajeroDeCoche() {
		System.out.println("Los coches con pasajero que existen actualmente son: ");
		boolean conPasajeros = listarCochesConPasajeros();
		
		if(conPasajeros)
			eliminarPasajero();
		else 
			System.out.println("No hay ningun coche con pasajeros actualmente.");
	}

	//Elimina el pasajero -> pide id de coche y pasajero, los DAO's los obtiene
	//comprueba si existen y se da de baja de la BBDD
	private void eliminarPasajero() {
		Scanner sc = new Scanner (System.in);
		int idBuscarCoche, idBuscarPasajero;
		Coche c;
		Pasajero p;
		System.out.println("Elija un coche introduciendo su id: ");
		idBuscarCoche = sc.nextInt();

		System.out.println("Elija un pasajero introduciendo su id: ");
		idBuscarPasajero = sc.nextInt();
	
		c = dc.obtener(idBuscarCoche);
		p = dp.obtener(idBuscarPasajero);
		if (c == null)
			System.out.println("Este coche no esta en la BBDD.");
		else if (p == null) {
			System.out.println("Este pasajero no esta en la BBDD.");
		} else if (!dp.pasajeroEnCoche(p.getId(), c.getId()))
			System.out.println("Este pasajero no está en este coche o en ningun coche");
			
		else {
			if (dp.BajaDeCoche(p.getId()))
				System.out.println("Se ha dado de baja el pasajero con id " + p.getId() + " del coche con id "
						+ c.getId() + " correctamente.");
		}
	}

	//Lista los coches que tienen pasajeros -> si tiene pasajeros se imprime el coche y sus pasajeros
	private boolean listarCochesConPasajeros() {
		List<Coche> coches = dc.listar();
		List<Pasajero> pasajeros;
		boolean conPasajeros = false;
		
		for (Coche c : coches) {
			pasajeros = dp.listarPasajerosEnCoche(c.getId());
			if (pasajeros != null) {
				System.out.println("*****************************************************");
				System.out.println(c);
				conPasajeros = true;
			}
			for (Pasajero p : pasajeros)
				System.out.println(p);
			System.out.println("*****************************************************");

		}
		return conPasajeros;
		
	}

	//Añade un pasajero a un coche -> se lista todos los coches, se pide los id's y si existen en BBDD
	//se añade al coche
	private void añadirPasajeroACoche() {
		Scanner sc = new Scanner (System.in);
		int idBuscarCoche, idBuscarPasajero;
		Coche c;
		Pasajero p;
		
		System.out.println("Los coches que existen actualmente son: ");
		listarCoches();
		System.out.println("Elija un coche introduciendo su id: ");
		idBuscarCoche = sc.nextInt();
		
		System.out.println("Los pasajeros que existen actualmente son: ");
		listarPasajeros();
		System.out.println("Elija un pasajero introduciendo su id: ");
		idBuscarPasajero = sc.nextInt();
	
		c = dc.obtener(idBuscarCoche);
		p = dp.obtener(idBuscarPasajero);
		if (c == null)
			System.out.println("Este coche no esta en la BBDD.");
		else if (p == null) {
			System.out.println("Este pasajero no esta en la BBDD.");
		} else {
			if (dp.altaACoche(p.getId(), c.getId()))
				System.out.println("Se ha introducido un pasajero en el coche.");
		}
	}
	// ------------------------------------- CRUD PASAJEROS ---------------------------------------------------------------
	private void listarPasajeros() {
		List<Pasajero> pasajeros = dp.listar();
		
		for (Pasajero p : pasajeros)
			System.out.println(p);
		
	}

	private void consultarPasajero() {
		Scanner sc = new Scanner (System.in);
		int idBuscar;
		Pasajero p;
		
		System.out.println("Introduzca el ID del pasajero a buscar: ");
		idBuscar = sc.nextInt();
		
		p = dp.obtener(idBuscar);
		if (p == null)
			System.out.println("El pasajero con ID " + idBuscar + " NO se encuentra en la BBDD");
		else 
			System.out.println(p);
		
	}

	private void borrarPasajero() {
		Scanner sc = new Scanner (System.in);
		int idBorrar;
		
		System.out.println("Introduzca el id del pasajero a borrar: ");
		idBorrar = sc.nextInt();
		if (dp.baja(idBorrar)) 
			System.out.println("Se ha borrado con éxito de la BBDD");
		else
			System.out.println("NO se ha podido borrar el elemento de la BBDD");
		
	}

	private void añadirPasajero() {
		String nombre;
		int edad;
		double peso;
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Introduzca el nombre del pasajero: ");
		nombre = sc.nextLine();
		System.out.println("Introduzca la edad: ");
		edad = sc.nextInt();
		System.out.println("Introduzca el peso: ");
		peso = sc.nextDouble();
		
		Pasajero p = new Pasajero(edad, peso, nombre);
		
		if(dp.alta(p))
			System.out.println("Se ha añadido el pasajero.");
		else
			System.out.println("Ha habido un problema al añadir el pasajero a la BBDD");
		
	}

	// ------------------------------------- FIN CRUD PASAJEROS ------------------------------------------------------------

	
	// ------------------------------------- CRUD COCHES ---------------------------------------------------------------
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
			sc.nextLine(); //limpiar
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

	// ------------------------------------- FIN CRUD COCHES ---------------------------------------------------------------
	

	// ------------------------------------- MENÚS ------------------------------------------------------------
	private int menu() {
		int op;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("---------- GESTOR DE COCHES ----------");
		System.out.println("1. Añadir nuevo coche.");
		System.out.println("2. Borrar coche por ID.");
		System.out.println("3. Consulta coche por ID.");
		System.out.println("4. Modificar coche por ID.");
		System.out.println("5. Listar coches");
		System.out.println("6. Gestionar pasajeros.");
		System.out.println("7. Terminar el programa.");
		op = sc.nextInt();
		
		while (op < 1 || op > 6) {
			System.out.println("ERROR EN LA ELECCION. ELIJA UN VALOR ENTRE 1 Y 5");
			System.out.println("1. Añadir nuevo coche.");
			System.out.println("2. Borrar coche por ID.");
			System.out.println("3. Consulta coche por ID.");
			System.out.println("4. Modificar coche por ID.");
			System.out.println("5. Listar coches");
			System.out.println("6. Gestionar pasajeros.");
			System.out.println("7. Terminar el programa.");
			
			op = sc.nextInt();

		}
		return op;
	}
	
	private int menuPasajeros() {
		Scanner sc = new Scanner(System.in);
		int op;
		
		System.out.println("1. Añadir nuevo pasajero.");
		System.out.println("2. Borrar pasajero por ID.");
		System.out.println("3. Consultar pasajero por ID.");
		System.out.println("4. Listar todos los pasajeros.");
		System.out.println("5. Añadir pasajero a un coche.");
		System.out.println("6. Eliminar pasajero de un coche");
		System.out.println("7. Listar los pasajeros de un coche.");
		System.out.println("8. Volver al menú principal.");
		op = sc.nextInt();
		while (op < 1 || op > 7) {
			System.out.println("ERROR EN LA ELECCION. Elija un valor entre 1 y 5");
			System.out.println("1. Añadir nuevo pasajero.");
			System.out.println("2. Borrar pasajero por ID.");
			System.out.println("3. Consultar pasajero por ID.");
			System.out.println("4. Listar todos los pasajeros.");
			System.out.println("5. Añadir pasajero a un coche.");
			System.out.println("6. Eliminar pasajero de un coche");
			System.out.println("7. Listar los pasajeros de un coche.");
			System.out.println("8. Volver al menú principal.");
			op = sc.nextInt();
			
		}
		return op;
	}
	
}
