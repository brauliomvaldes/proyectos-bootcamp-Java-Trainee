package com.appwebexamen.appwebexamen.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appwebexamen.appwebexamen.dao.IListadoDao;
import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;
import com.appwebexamen.appwebexamen.service.IListadoService;

/*
 * clase servicio para trae listado de citas
 * 
 */

@Service
public class ListadoServiceImpl implements IListadoService{

	private final IListadoDao listadoDao;
	
	public ListadoServiceImpl(IListadoDao listadoDao) {
		this.listadoDao=listadoDao;
	}
	
	@Override
	public List<ListadoDTO> listado(int idSucursal, int idTipo) {
		return listadoDao.listado(idSucursal, idTipo);
	}

}
