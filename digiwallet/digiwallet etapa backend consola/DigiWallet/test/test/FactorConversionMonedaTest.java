package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import wallet.Transferencias;

/*
 * Test unitario para proceso conversion de dinero por medio de un factor
 */
class FactorConversionMonedaTest {

	@Test
	public void testAplicaFactorConversionAMonto() {
		// se proporciona el monto a convertir y el factor de conversión
		
		BigDecimal monto = BigDecimal.valueOf(1000);
		BigDecimal factor = BigDecimal.valueOf(0.0011);
		// para el valor esperado se usa método del BigDecimal
		BigDecimal valorEsperado = monto.multiply(factor);
	    assertEquals(valorEsperado, Transferencias.calculoAplicaFactorConversion(monto, factor)); 
	}
}
