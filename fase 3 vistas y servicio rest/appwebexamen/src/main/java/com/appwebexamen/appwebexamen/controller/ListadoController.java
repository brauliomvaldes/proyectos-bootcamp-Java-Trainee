package com.appwebexamen.appwebexamen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.appwebexamen.appwebexamen.model.dto.TipoAtencionDTO;
import com.appwebexamen.appwebexamen.model.dto.ListadoDTO;
import com.appwebexamen.appwebexamen.model.dto.SeleccionDTO;
import com.appwebexamen.appwebexamen.model.dto.SucursalDTO;
import com.appwebexamen.appwebexamen.service.ITipoAtencionService;
import com.appwebexamen.appwebexamen.service.IListadoService;
import com.appwebexamen.appwebexamen.service.ISucursalService;

/*
 * clase controller para mapear las vistas y api rest
 * 
 */


@Controller
@RequestMapping("/listado")
public class ListadoController {

	// inyeccion de servicios
	@Autowired
	private ITipoAtencionService tipoAtencionService;

	@Autowired
	private ISucursalService sucursalService;

	@Autowired
	private IListadoService listadoService;

	@GetMapping("/atenciones")
	public String listar(Model model) {
		List<TipoAtencionDTO> tipos = tipoAtencionService.listar();
		List<SucursalDTO> sucursales = sucursalService.listar();

		model.addAttribute("sucursales", sucursales);
		model.addAttribute("tipos", tipos);
		return "listado.jsp";
	}

	@PostMapping("/atenciones")
	public String mostrarResultados(@ModelAttribute SeleccionDTO seleccion, Model model) {
		List<TipoAtencionDTO> tipos = tipoAtencionService.listar();
		List<SucursalDTO> sucursales = sucursalService.listar();
		model.addAttribute("sucursales", sucursales);
		model.addAttribute("tipos", tipos);

		// llama al servicio para consultar a la base de datos 
		List<ListadoDTO> listado = listadoService.listado(seleccion.getSucursal(), seleccion.getTipo());
		model.addAttribute("listado", listado);
		// consulta si hubo resultados de la búsqueda
		if (listado.size() > 0) {
			if(seleccion.getSucursal() == 0 && seleccion.getTipo() == 0) {
				model.addAttribute("filtrado", "Por todas las próximas atenciones");
			}else {
				model.addAttribute("filtrado", listado.get(0).getSucursalNombre()+"/"+listado.get(0).getTipoAtencionDescripcion());
			}
		}
		return "listado.jsp";
	}

	@GetMapping("/api/atenciones-sucursal")
	public ResponseEntity<List<ListadoDTO>> obtenerListado(@RequestParam int idSucursal,
			@RequestParam int idTipoAtencion) {
		List<ListadoDTO> listado = listadoService.listado(idSucursal, idTipoAtencion);
		return ResponseEntity.ok(listado);
	}
}
