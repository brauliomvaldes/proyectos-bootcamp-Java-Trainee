package bootcamp.consola.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ConsolaControllerTest {
	
	// validación con fechas y formato válidos
		@ParameterizedTest
		@ValueSource(strings = { "2024-02-25", "2024-10-02", "2024-01-01", "2024-12-21", "2024-08-01", "2024-11-01" })
		void testTrueFecha(String fecha) {
			assertAll(() -> assertTrue(ConsolaController.validaFecha(fecha)));
		}

		// validación con fechas o formatos no válidos 
		@ParameterizedTest
		@ValueSource(strings = { "201-02-30", "2024-13-01", "", "d", "5,2d", "6-", ",7", "3-8", "0x9", "x10", "f110", "q201", "555r",
				"1,0,10" })
		void testFalseFecha(String fecha) {
			assertAll(() -> assertFalse(ConsolaController.validaFecha(fecha)));
		}

}
