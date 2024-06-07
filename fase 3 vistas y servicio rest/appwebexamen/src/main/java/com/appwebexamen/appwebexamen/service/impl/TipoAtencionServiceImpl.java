package com.appwebexamen.appwebexamen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appwebexamen.appwebexamen.dao.ITipoAtencionDao;
import com.appwebexamen.appwebexamen.model.dto.TipoAtencionDTO;
import com.appwebexamen.appwebexamen.service.ITipoAtencionService;

/*
 * clase servicio para trae una lista de tipos de atención
 * 
 */

@Service
public class TipoAtencionServiceImpl implements ITipoAtencionService{

	@Autowired
	private ITipoAtencionDao tipoAtencionDao;
	
	@Override
	public List<TipoAtencionDTO> listar() {
		return tipoAtencionDao.listar();
	}

	
	
}
