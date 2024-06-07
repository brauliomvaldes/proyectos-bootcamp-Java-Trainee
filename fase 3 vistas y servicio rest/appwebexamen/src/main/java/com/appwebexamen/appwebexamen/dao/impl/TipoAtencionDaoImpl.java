package com.appwebexamen.appwebexamen.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.appwebexamen.appwebexamen.dao.ITipoAtencionDao;
import com.appwebexamen.appwebexamen.model.dto.TipoAtencionDTO;

/*
 * clase repositorio para traer todos los tipos de atención ordenados alfabéticamente
 * 
 */

@Repository
public class TipoAtencionDaoImpl implements ITipoAtencionDao {

	private final JdbcTemplate jdbcTemplate;

	public TipoAtencionDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TipoAtencionDTO> listar() {

		try {
			String sql = "select * from tipo_atencion order by tipo_atencion_descripcion;";
			
			return jdbcTemplate.query(sql, (rs, rowNum) -> 
			new TipoAtencionDTO(rs.getInt("tipo_atencion_id"), rs.getString("tipo_atencion_descripcion")));
			
		}catch(Exception e) {
			System.out.println("error: "+e.getMessage());
			return Collections.emptyList();
		}
	}

}
