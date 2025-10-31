package br.com.vinicius.JUnit;

import br.com.vinicius.JUnit.codigos.Calculadora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculadoraTest {

    @Test
    void deveSomarDoisNumeros() {
        Calculadora calculadora = new Calculadora();
        int esperado = 10;
        int atual = calculadora.somar(5, 5);
        assertEquals(esperado, atual, "A soma de 5 + 5 deve ser 10");
    }

    @Test
    void deveSubtrairDoisNumeros() {
        Calculadora calculadora = new Calculadora();
        int esperado = 0;
        int atual = calculadora.subtrair(5, 5);
        assertEquals(esperado, atual, "A subtração de 5 - 5 deve ser 0");
    }
}
