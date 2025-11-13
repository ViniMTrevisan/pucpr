from app.salario_minimo import Salario

def test_salario_minimo():
    salario_minimo = 1518.00
    salario_usuario = 3036.00

    resultado = Salario.calcular_minimo(salario_minimo, salario_usuario)
    assert resultado == 2

def test_salario_minimo_adicional():
    salario_minimo = 1518.00

    assert Salario.calcular_minimo(salario_minimo, 0) == 0
    
    resultado = Salario.calcular_minimo(salario_minimo, -1000)
    assert resultado == 'invalido'
