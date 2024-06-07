package bootcamp.consola.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import bootcamp.consola.model.Inventario;
import bootcamp.consola.model.dto.CitasDto;
import bootcamp.consola.model.dto.InventarioExistenciasDto;
import bootcamp.consola.service.ICitasService;
import bootcamp.consola.service.IInventarioService;

/*
 * clase que controla los algoritmos
 * sus 2 opciones permiten procesar y consultar a la BBDD
 */

@Component
public class ConsolaController {

	// para ingreso de información desde teclado
	private final Scanner leeteclado;
	// servicio que trae existencias 
	private final IInventarioService inventarioService;
	// servici que trae citas por fecha
	private final ICitasService citasService;

	// constructor que inyecta servicios e instancia scanner
	public ConsolaController(IInventarioService inventarioService, ICitasService citasService) {
		this.inventarioService = inventarioService;
		this.citasService = citasService;
		leeteclado = new Scanner(System.in);
	}

	public void menu() {

		boolean running = true;

		List<String> menu = new ArrayList<>();
		menu.add("\n      Veterinaria HappyPet");
		menu.add("------------ M E N U ----------");
		menu.add("1. Consultar Stock por Sucursal");
		menu.add("2. Consultar citas por Fecha");
		menu.add("3. Salir");

		while (running) {

			for (String m : menu) {
				System.out.println(m);
			}
			String opcion = leeteclado.next();

			switch (opcion) {
			case "1":
				// algoritmo para chequeo de existencias
				consultaExistenciasPorSucursal();
				break;
			case "2":
				// algoritmo para revisar citas por fechas
				citasPorFecha();
				break;
			case "3":
				// termina
				running = false;
			}
		}
		System.out.println("programa finalizado");
		System.exit(0);
	}

	private void consultaExistenciasPorSucursal() {
		while (true) {
			try {
				System.out.print("\nIndique el id de la sucursal (0=termina) ? ");
				int idSucursal = leeteclado.nextInt();
				// termina con cero
				if (idSucursal == 0) {
					return;
				}
				// continua
				List<InventarioExistenciasDto> resultado = inventarioService.recuperarInventarioPorSucursal(idSucursal);
				if (resultado.size() > 0) {
					System.out.println();
					// recorre listado de existencias segun la sucursal
					for (InventarioExistenciasDto i : resultado) {
						Inventario inventario = i.getInventario();
						// revisa stock crítico según requerimientos
						if ((inventario.getTipoProductoId() == 2 && inventario.getExistencia() < 15)
								|| (inventario.getTipoProductoId() == 1 && inventario.getExistencia() < 5)) {
							// alerta
							System.out.println("- "+inventario.getNombre() + " " + inventario.getMarca() + " quedan "
									+ inventario.getExistencia() + " unidades");
						} else {
							System.out.println("\nEn la sucursal no hay productos bajo stock crítico");
						}
					}
				} else {
					System.out.println("\nEl Id de sucursal no es válido");
				}

			} catch (Exception e) {
				if(leeteclado.hasNext()) leeteclado.next();
				System.out.println("\nOpción no valida, reintente");
			}
		}
	}

	private void citasPorFecha() {
		while (true) {
			System.out.print("\nIndique fecha de la cita, formato AAAA-MM-DD (0=termina) ? ");
			String fecha = leeteclado.next();
			// sale con cero
			if (fecha.equals("0")) {
				return;
			}
			// valida que la fecha este en formato correcto y que sea válida
			boolean resultado = validaFecha(fecha);
			// si es válida
			if (resultado) {				
				// continua
				System.out.println();
				List<CitasDto> citas = citasService.consultaCitas(fecha);
				if (citas.size() > 0) {
					for (CitasDto cita : citas) {
						// listado
						System.out.print("- "+cita.getMascota_nombre()+" "+cita.getTipo_mascota_descripcion()+" tiene ");
						// agrega texto
						if(cita.getTipo_atencion_id()==1) System.out.print("consulta ");
						// completa mensaje
						System.out.println(cita.getTipo_atencion_descripcion());
					}
				} else {
					System.out.println("\nNo hay citas programadas para la fecha consultada");
				}
			}else {
				System.out.println("Fecha inválida. Inténtalo nuevamente.");
			}
		}
	}
	
	// valida fecha de tipo string lo convierte a tipo fecha
	public static boolean validaFecha(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
        	// variable instanciada sólo para efectos de chequeo de parseo
            LocalDate date = LocalDate.parse(fecha, formatter);
            // si se realiza el parseo de string a date esta válida
            return true;
        } catch (Exception e) {
        	// no fue posible realizar el parseo
        	return false;            
        }
	}

}
