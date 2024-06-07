package com.appwebexamen.appwebexamen.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.appwebexamen.appwebexamen.mapper.ListadoMapper;
import com.appwebexamen.appwebexamen.dao.IListadoDao;
import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;
/*
 * clase repositorio para realizar las consultas sobre las citas a la base de datos
 * 
 */

@Repository
public class ListadoDaoImpl implements IListadoDao {

	private final JdbcTemplate jdbcTemplate;

	public ListadoDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ListadoDTO> listado(int idSucursal, int idTipo) {
		try {
			List<ListadoDTO> productos = null;
			String sql = "select s.sucursal_nombre, md.medico_nombre, t.tipo_atencion_descripcion, m.mascota_nombre, d.dueno_nombre,\r\n"
					+ "a.fecha_proxima_revision, a.box_atencion\r\n"
					+ "from atencion a inner join sucursal s on a.sucursal_id = s.sucursal_id\r\n"
					+ "inner join medico md on a.medico_id = md.medico_id\r\n"
					+ "inner join tipo_atencion t on a.tipo_atencion_id = t.tipo_atencion_id\r\n"
					+ "inner join mascota m on a.mascota_id = m.mascota_id\r\n"
					+ "inner join dueno d on m.dueno_id = d.dueno_id\r\n";
			if(!(idSucursal == 0 && idTipo == 0)) {
				sql += "where s.sucursal_id = ? and t.tipo_atencion_id = ?;";
				productos = jdbcTemplate.query(sql, new ListadoMapper(), new Object[] {idSucursal, idTipo});
			}else {
				productos = jdbcTemplate.query(sql, new ListadoMapper());
			}
			return productos;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			// devuelve una lista vacia si no se encontraron citas
			return Collections.emptyList();
		}
	}
}
