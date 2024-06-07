package service;

import java.math.BigDecimal;
import java.util.Scanner;

public class ValidadorNumerico {
	
	/**
	 * Valida ingreso de un valor tipo int por teclado
	 *
	 * @param scanner, mensaje para el ingreso de datos
	 * @return valor absoluto tipo entero
	 */
	public static int validaInt(Scanner leeteclado, String mensaje) {
		Integer valor = 0;
		do {
			try {
				String numStr = leeteclado.next();
				if (validaStrInt(numStr)) {
					valor = Math.abs(Integer.parseInt(numStr));
					return (int) valor;
				}
			} catch (Exception e) {
				System.out.println("\nvalor no permitido, reintente");
			}
			System.out.print(mensaje);
		} while (true);
	}

	/**
	 * Valida conversión de tipo String a tipo int
	 *
	 * @param String ingresado por teclado
	 * @return boolean
	 */
	public static boolean validaStrInt(String numStr) {
		try {
			Integer.parseInt(numStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Valida ingreso de valor tipo double por teclado
	 *
	 * @param scanner, mensaje para el ingreso de datos
	 * @return valor absoluto tipo BigDecimal
	 */
	public static BigDecimal validaBigDecimal(Scanner leeteclado, String mensaje) {
		BigDecimal valor = BigDecimal.ZERO;
		do {
			try {
				String numStr = leeteclado.next();
				if (validaStrDouble(numStr)) {
					valor = BigDecimal.valueOf(Double.parseDouble(numStr)).abs();
					return valor;
				}
			} catch (Exception e) {
				System.out.println("\nvalor no permitido, reintente");
			}
			System.out.print(mensaje);
		} while (true);
	}

	/**
	 * Valida conversión de tipo String a tipo double
	 *
	 * @param String ingresado por teclado
	 * @return boolean
	 */
	public static boolean validaStrDouble(String numStr) {
		try {
			Double.parseDouble(numStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
