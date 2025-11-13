# ...existing code...
class Matematica:
    @staticmethod
    def eh_par(n):
        return n % 2 == 0

    @staticmethod
    def media(lista):
        if not isinstance(lista, (list, tuple)):
            raise TypeError
        if len(lista) == 0:
            return None

        total = 0
        for v in lista:
            if isinstance(v, bool) or not isinstance(v, (int, float)):
                raise TypeError
            total += v

        return total / len(lista)
