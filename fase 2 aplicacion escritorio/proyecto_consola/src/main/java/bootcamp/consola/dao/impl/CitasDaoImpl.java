package bootcamp.consola.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import bootcamp.consola.dao.ICitasDao;
import bootcamp.consola.model.dto.CitasDto;

/*
 * clase repositorio para realizar consultas sobre citas por fecha
 * 
 */

@Repository
public class CitasDaoImpl implements ICitasDao{

	private JdbcTemplate jdbcTemplate;
	
	public CitasDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	@Override
	public List<CitasDto> consultaCitas(String fechaConsultada) {
		try {
			String sql = "SELECT mascota_nombre, tipo_mascota_descripcion, a.tipo_atencion_id, ta.tipo_atencion_descripcion\r\n"
					+ "FROM mascota m inner join tipo_mascota t on t.tipo_mascota_id = m.tipo_mascota_id\r\n"
					+ "inner join atencion a on a.mascota_id = m.mascota_id\r\n"
					+ "inner join tipo_atencion ta on ta.tipo_atencion_id = a.tipo_atencion_id\r\n"
					+ "where a.fecha_proxima_revision like ?;";
			return jdbcTemplate.query(sql, 
					(rs, rowNum)->
							new CitasDto(
											rs.getString("mascota_nombre"),
											rs.getString("tipo_mascota_descripcion"),
											rs.getInt("tipo_atencion_id"),
											rs.getString("tipo_atencion_descripcion")
									)
					, new Object[] {fechaConsultada.trim()+'%'});
		}catch(Exception e) {
			return Collections.emptyList();
		}
	}

}
