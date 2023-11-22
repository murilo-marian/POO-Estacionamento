package veiculo;

import estacionamento.Vaga;

import java.time.LocalDateTime;

public class Ticket {
    LocalDateTime entrada;
    LocalDateTime saida;
    double valor;
    Vaga vaga;

    public Ticket(LocalDateTime entrada, LocalDateTime saida, double valor, Vaga vaga) {
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
        this.vaga = vaga;
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
        sb.append(", vaga=").append(vaga);
        sb.append('}');
        return sb.toString();
    }
}
