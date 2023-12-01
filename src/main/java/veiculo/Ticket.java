package veiculo;

import estacionamento.DiaDaSemana;
import estacionamento.Vaga;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket {
    LocalDateTime entrada;
    LocalDateTime saida;
    double valor;
    Vaga vaga;

    public Ticket(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public double calcularValor() {
        float modificador = vaga.getOcupante().getTipoDeVeiculo().getModificador();
        float horas = ChronoUnit.HOURS.between(entrada, saida);
        if (horas == 0) {
            horas++;
        }
        double valorPorHoraDoDia = DiaDaSemana.getDia(entrada.getDayOfWeek()).getValorPorHora();

        return valorPorHoraDoDia * horas * modificador;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("entrada=").append(entrada);
        sb.append(", saida=").append(saida);
        sb.append(", valor=").append(valor);
        sb.append('}');
        return sb.toString();
    }
}
