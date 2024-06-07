package bootcamp.consola.service;

import java.util.List;

import bootcamp.consola.model.dto.CitasDto;

public interface ICitasService {

	public List<CitasDto> consultaCitas(String fechaConsultada);
}
