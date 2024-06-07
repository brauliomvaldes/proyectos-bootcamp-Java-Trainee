package com.appwebexamen.appwebexamen.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * clase dto para mapear la respuesta desde la vista (combobox)
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeleccionDTO {

	private int sucursal;
	private int tipo;
	
}
