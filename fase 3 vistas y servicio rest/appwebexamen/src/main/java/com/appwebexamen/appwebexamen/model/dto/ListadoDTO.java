package com.appwebexamen.appwebexamen.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * clase dto que representa la respuesta a la consulta sobre citas
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListadoDTO {
	
	private String sucursalNombre;
	private String medicoNombre;
	private String tipoAtencionDescripcion;
	private String mascotaNombre;
	private String duenoNombre;
	private Date fechaRealizacion;
	private String boxAtencion;

}
