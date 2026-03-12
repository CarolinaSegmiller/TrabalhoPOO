package controle;

import modelo.Pais;

public class ComparadorSuperTrunfo {

    public String comparar(Pais primeiro, Pais segundo, String atributo) {
        atributo = atributo.toLowerCase();

        switch (atributo) {
            case "populacao":
                return compararNumerico(primeiro, segundo, primeiro.getPopulacao(), segundo.getPopulacao(), "População");
            case "area":
                return compararNumerico(primeiro, segundo, primeiro.getArea(), segundo.getArea(), "Área");
            case "pib":
                return compararNumerico(primeiro, segundo, primeiro.getPib(), segundo.getPib(), "PIB");
            case "pontos":
            case "pontosturisticos":
                return compararNumerico(primeiro, segundo, primeiro.getPontosTuristicos(), segundo.getPontosTuristicos(), "Pontos Turísticos");
            default:
                return "Atributo inválido. Use: populacao, area, pib ou pontos.";
        }
    }

    private String compararNumerico(Pais primeiro, Pais segundo, double valor1, double valor2, String nomeAtributo) {
        if (valor1 > valor2) {
            return String.format("%s venceu em %s! (%.2f x %.2f)", primeiro.getNome(), nomeAtributo, valor1, valor2);
        } else if (valor2 > valor1) {
            return String.format("%s venceu em %s! (%.2f x %.2f)", segundo.getNome(), nomeAtributo, valor2, valor1);
        }
        return String.format("Empate em %s! (%.2f x %.2f)", nomeAtributo, valor1, valor2);
    }
}
