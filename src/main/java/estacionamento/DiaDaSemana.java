package estacionamento;

import java.time.DayOfWeek;

public enum DiaDaSemana {
    SEGUNDA(DayOfWeek.MONDAY, 0), TERCA(DayOfWeek.TUESDAY, 0), QUARTA(DayOfWeek.WEDNESDAY, 0), QUINTA(DayOfWeek.THURSDAY, 0), SEXTA(DayOfWeek.FRIDAY, 0), SABADO(DayOfWeek.SATURDAY, 0), DOMINGO(DayOfWeek.SUNDAY, 0);

    private final DayOfWeek DIA;
    private float valorPorHora;

    DiaDaSemana(DayOfWeek dia, float valorPorHora) {
        this.DIA = dia;
        this.valorPorHora = valorPorHora;
    }

    public DayOfWeek getDIA() {
        return DIA;
    }

    public float getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(float valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    public static DiaDaSemana getDia(DayOfWeek diaSemana) {
        for (DiaDaSemana value : DiaDaSemana.values()) {
            if (value.DIA.equals(diaSemana)) {
                return value;
            }
        }
        return null;
    }
}
