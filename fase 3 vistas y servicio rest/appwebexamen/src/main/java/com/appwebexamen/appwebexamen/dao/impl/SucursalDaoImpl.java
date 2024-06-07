package com.appwebexamen.appwebexamen.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.appwebexamen.appwebexamen.dao.ISucursalDao;
import com.appwebexamen.appwebexamen.model.dto.SucursalDTO;

/*
 * clase repositorio para todas traer las sucursales ordenadas alfabéticamente
 * 
 */

@Repository
public class SucursalDaoImpl implements ISucursalDao {

	private final JdbcTemplate jdbcTemplate;

	public SucursalDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<SucursalDTO> listar() {
		try {

			String sql = "select * from sucursal order by sucursal_nombre;";
			
			return jdbcTemplate.query(sql,
					(rs, rowNum) -> new SucursalDTO(rs.getInt("sucursal_id"), rs.getString("sucursal_nombre")));
		} catch (Exception e) {
			System.out.println("error: "+e.getMessage());
			return Collections.emptyList();
		}
	}

}
