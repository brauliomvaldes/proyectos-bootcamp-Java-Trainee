package bootcamp.consola.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * clase dto para recibir respuesta a consulta de citas por fecha
 * 
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CitasDto {

	private String mascota_nombre;
	private String tipo_mascota_descripcion;
	private int tipo_atencion_id;
	private String tipo_atencion_descripcion;
	
}
