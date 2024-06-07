package com.appwebexamen.appwebexamen.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;

/*
 * clase que implementa mapRow y que mapea la respuesta de la consulta a un objeto 
 * que representa el resultado a mostrar en la vista
 */

public class ListadoMapper implements RowMapper<ListadoDTO>{

	@Override
	public ListadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ListadoDTO ld = new ListadoDTO();
		
		ld.setSucursalNombre(rs.getString("sucursal_nombre"));
		ld.setMedicoNombre(rs.getString("medico_nombre"));
		ld.setTipoAtencionDescripcion(rs.getString("tipo_atencion_descripcion"));
		ld.setMascotaNombre(rs.getString("mascota_nombre"));
		ld.setDuenoNombre(rs.getString("dueno_nombre"));
		ld.setFechaRealizacion(rs.getDate("fecha_proxima_revision"));
		ld.setBoxAtencion(rs.getString("box_atencion"));
		
		return ld;
	}

}
