package bootcamp.consola.dao;

import java.util.List;

import bootcamp.consola.model.dto.InventarioExistenciasDto;

public interface IInventarioDao {

	List<InventarioExistenciasDto> recuperarInventarioPorSucursal(int idSucursal);
}
