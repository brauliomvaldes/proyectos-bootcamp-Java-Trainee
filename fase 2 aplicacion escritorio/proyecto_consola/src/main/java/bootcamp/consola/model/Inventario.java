package bootcamp.consola.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

	private String nombre;
	private String marca;
	private int tipoProductoId;
	private int existencia;
	
}
