package bootcamp.consola.model.dto;

import bootcamp.consola.model.Inventario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * clase dto para recibir respuesta a consulta de existencias por sucursal
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventarioExistenciasDto {
	private Inventario inventario;
}
