package bootcamp.consola.service;

import java.util.List;

import bootcamp.consola.model.dto.InventarioExistenciasDto;

public interface IInventarioService {
	
	List<InventarioExistenciasDto> recuperarInventarioPorSucursal(int idSucursal);
}
