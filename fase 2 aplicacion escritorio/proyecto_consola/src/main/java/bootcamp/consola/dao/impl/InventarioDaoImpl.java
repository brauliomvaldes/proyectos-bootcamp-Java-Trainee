package bootcamp.consola.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import bootcamp.consola.dao.IInventarioDao;
import bootcamp.consola.model.Inventario;
import bootcamp.consola.model.dto.InventarioExistenciasDto;

/*
 * clase repositorio para realizar consultas sobre existencias
 * 
 */

@Repository
public class InventarioDaoImpl implements IInventarioDao {

	private final JdbcTemplate jdbcTemplate;

	public InventarioDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<InventarioExistenciasDto> recuperarInventarioPorSucursal(int idSucursal) {
		try {
			String sql = "select i.inventario_nombre, i.inventario_marca, i.tipo_producto_id, i.inventario_existencia" 
					+" from inventario i inner join tipo_producto t on i.tipo_producto_id = t.tipo_producto_id"
					+" inner join sucursal s on i.sucursal_id = s.sucursal_id"
					+" where s.sucursal_id = ?;";
			
			return jdbcTemplate.query(sql, 
					(rs, rowNum)->
							new InventarioExistenciasDto(
									new Inventario(
											rs.getString("inventario_nombre"),
											rs.getString("inventario_marca"),
											rs.getInt("tipo_producto_id"),
											rs.getInt("inventario_existencia")
									))
					, new Object[] {idSucursal});
			
		}catch(Exception e){
			return Collections.emptyList();
		}
	}

}
