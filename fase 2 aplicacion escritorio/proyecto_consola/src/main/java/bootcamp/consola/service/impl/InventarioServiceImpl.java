package bootcamp.consola.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootcamp.consola.dao.IInventarioDao;
import bootcamp.consola.model.dto.InventarioExistenciasDto;
import bootcamp.consola.service.IInventarioService;

@Service
public class InventarioServiceImpl implements IInventarioService{

	@Autowired
	private IInventarioDao inventarioDao;
	
	@Override
	public List<InventarioExistenciasDto> recuperarInventarioPorSucursal(int idSucursal) {
		return inventarioDao.recuperarInventarioPorSucursal(idSucursal);
	}

}
