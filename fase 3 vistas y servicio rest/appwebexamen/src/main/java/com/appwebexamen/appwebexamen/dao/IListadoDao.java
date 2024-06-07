package com.appwebexamen.appwebexamen.dao;

import java.util.List;

import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;

public interface IListadoDao {
 
	List<ListadoDTO> listado(int idSucursal, int idCategoria);
}
