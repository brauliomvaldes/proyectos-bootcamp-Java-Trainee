package com.appwebexamen.appwebexamen;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.appwebexamen.appwebexamen.dao.impl.SucursalDaoImpl;
import com.appwebexamen.appwebexamen.model.dto.SucursalDTO;
import com.appwebexamen.appwebexamen.service.impl.SucursalServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SucursalServiceTest {

    @Mock
    private SucursalDaoImpl sucursalDao;

    @InjectMocks
    private SucursalServiceImpl sucursalService;

    @Test
    public void testListar() {
        // Datos sintéticos
        String nombreSucursal1 = "Casa MAtriz";
        int idSucursal1 = 1;
        String nombreSucursal2 = "Sucursal Jose Perez";
        int idSucursal2 = 2;

        SucursalDTO sucursalEsperada1 = new SucursalDTO();
        sucursalEsperada1.setNombre("Casa MAtriz");
        sucursalEsperada1.setId(1);

        SucursalDTO sucursalEsperada2 = new SucursalDTO();
        sucursalEsperada2.setNombre("Sucursal Jose Perez");
        sucursalEsperada2.setId(2);

        List<SucursalDTO> sucursales = new ArrayList<>();
        sucursales.add(sucursalEsperada1);
        sucursales.add(sucursalEsperada2);

        // Configuración del mock
        when(sucursalDao.listar()).thenReturn(sucursales);

        List<SucursalDTO> sucursalesEsperadas = sucursalService.listar();

        // Verificación
        assertNotNull(sucursalesEsperadas);
        assertEquals(2, sucursalesEsperadas.size());
        assertEquals("Casa MAtriz", sucursalesEsperadas.get(0).getNombre());
        assertEquals(1, sucursalesEsperadas.get(0).getId());
        assertEquals("Sucursal Jose Perez", sucursalesEsperadas.get(1).getNombre());
        assertEquals(2, sucursalesEsperadas.get(1).getId());
    }
}
