package estacionamento;

import java.time.DayOfWeek;

public enum DiaDaSemana {
    SEGUNDA(DayOfWeek.MONDAY, 10), TERCA(DayOfWeek.TUESDAY, 10), QUARTA(DayOfWeek.WEDNESDAY, 10), QUINTA(DayOfWeek.THURSDAY, 10), SEXTA(DayOfWeek.FRIDAY, 10), SABADO(DayOfWeek.SATURDAY, 10), DOMINGO(DayOfWeek.SUNDAY, 10);

    private final DayOfWeek DIA;
    private double valorPorHora;

    DiaDaSemana(DayOfWeek dia, float valorPorHora) {
        this.DIA = dia;
        this.valorPorHora = valorPorHora;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public static DiaDaSemana getDia(DayOfWeek diaSemana) {
        for (DiaDaSemana value : DiaDaSemana.values()) {
            if (value.DIA.equals(diaSemana)) {
                return value;
            }
        }
        return null;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }
}
