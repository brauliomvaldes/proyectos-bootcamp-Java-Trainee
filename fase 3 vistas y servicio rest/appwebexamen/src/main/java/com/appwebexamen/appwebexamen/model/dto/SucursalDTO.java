package com.appwebexamen.appwebexamen.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * clase dto para recibir la respuesta a la consulta de todas las sucursales
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
	private int id;
	private String nombre;

}
