class Salario:
        @staticmethod
        def calcular_minimo(salario_minimo, salario_usuario):
            # inválido quando o salário do usuário for negativo
            if salario_usuario < 0:
                return "invalido"

            quantidade = salario_usuario / salario_minimo

            return quantidade if salario_usuario != 0 else 0
