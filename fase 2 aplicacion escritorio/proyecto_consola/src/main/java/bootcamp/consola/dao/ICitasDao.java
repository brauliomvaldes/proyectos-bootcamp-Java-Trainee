package bootcamp.consola.dao;

import java.util.List;

import bootcamp.consola.model.dto.CitasDto;

public interface ICitasDao {

	List<CitasDto> consultaCitas(String fechaConsultada);
}
