package veiculo;

import estacionamento.TipoDeVeiculo;

import java.time.LocalDateTime;
import java.time.Year;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String ano;
    private String cor;
    private TipoDeVeiculo tipoDeVeiculo;
    private Ticket ticket;

    public Veiculo(String placa, String marca, String modelo, String ano, String cor, TipoDeVeiculo tipoDeVeiculo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.tipoDeVeiculo = tipoDeVeiculo;
        ticket = new Ticket(LocalDateTime.now());
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TipoDeVeiculo getTipoDeVeiculo() {
        return tipoDeVeiculo;
    }

    public void setTipoDeVeiculo(TipoDeVeiculo tipoDeVeiculo) {
        this.tipoDeVeiculo = tipoDeVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Veiculo{");
        sb.append("placa='").append(placa).append('\'');
        sb.append(", marca='").append(marca).append('\'');
        sb.append(", modelo='").append(modelo).append('\'');
        sb.append(", ano='").append(ano).append('\'');
        sb.append(", cor='").append(cor).append('\'');
        sb.append(", tipoDeVeiculo=").append(tipoDeVeiculo);
        sb.append(", ticket=").append(ticket);
        sb.append('}');
        return sb.toString();
    }
}
