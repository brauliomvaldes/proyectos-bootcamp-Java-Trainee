package com.appwebexamen.appwebexamen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appwebexamen.appwebexamen.dao.ISucursalDao;
import com.appwebexamen.appwebexamen.model.dto.SucursalDTO;
import com.appwebexamen.appwebexamen.service.ISucursalService;

/*
 * clase servicio para trae una lista de sucursales
 * 
 */

@Service
public class SucursalServiceImpl implements ISucursalService{

	@Autowired
	private ISucursalDao sucursalDao;
	
	@Override
	public List<SucursalDTO> listar() {
		return sucursalDao.listar();
	}

}
