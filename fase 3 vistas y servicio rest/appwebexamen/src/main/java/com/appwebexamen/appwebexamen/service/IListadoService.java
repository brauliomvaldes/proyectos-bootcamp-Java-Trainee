package com.appwebexamen.appwebexamen.service;

import java.util.List;

import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;

public interface IListadoService {
	public List<ListadoDTO> listado(int idSucursal, int idTipo);
}
