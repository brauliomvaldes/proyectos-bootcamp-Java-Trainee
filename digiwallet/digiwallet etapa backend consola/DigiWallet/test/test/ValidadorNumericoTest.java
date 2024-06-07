package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import service.ValidadorNumerico;

/*
 * Test unitario para proceso de validación de ingreso de números
 */
class ValidadorNumericoTest {
	
	// validación con valores enteros válidos
	@ParameterizedTest
	@ValueSource(strings = {"1","2","3","4","5","6","7","8","9","10","110","201","555","1010"})
	void testTrueInt(String num) {
		assertAll(() -> assertTrue(ValidadorNumerico.validaStrInt(num)));
	}
	// validación con valores enteros no válidos
	@ParameterizedTest
	@ValueSource(strings = {"1.2","2a","","d","5,2d","6-",",7","3-8","0x9","x10","f110","q201","555r","1,0,10"})
	void testFalseInt(String num) {
		assertAll(() -> assertFalse(ValidadorNumerico.validaStrInt(num)));
		assertAll(() -> assertFalse(ValidadorNumerico.validaStrInt(num)));
	}
	// validación con valores de punto flotante válidos
	@ParameterizedTest
	@ValueSource(strings = {"1.3","2.0","3.4","4.3","5.1","6.2","7.1","8.8","9.9","10.2","110.0","201.5","555.2","1010.3"})	
	void testTrueDouble(String num) {
		assertAll(() -> assertTrue(ValidadorNumerico.validaStrDouble(num)));
	}
	// validación con valores de punto flotante no válidos
	@ParameterizedTest
	@ValueSource(strings = {"1,3","2,,0",".3.4","4a.3","f5.1","6-2","-7-1","8/8","9s","","110,,.0","201..5","555w.2","101s0.3"})	
	void testFalseDouble(String num) {
		assertAll(() -> assertFalse(ValidadorNumerico.validaStrDouble(num)));
		assertAll(() -> assertFalse(ValidadorNumerico.validaStrDouble(num)));
	}
}
