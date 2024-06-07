package bootcamp.consola.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootcamp.consola.dao.ICitasDao;
import bootcamp.consola.model.dto.CitasDto;
import bootcamp.consola.service.ICitasService;

@Service
public class CitasServiceImpl implements ICitasService{

	@Autowired
	private ICitasDao citasDao;
	
	@Override
	public List<CitasDto> consultaCitas(String fechaConsultada) {
		return citasDao.consultaCitas(fechaConsultada);
	}

}
