package veiculo;

import java.time.Year;

public abstract class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;

    public Veiculo(String placa, String marca, String modelo, int ano, String cor) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
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
        sb.append(", ano=").append(ano);
        sb.append(", cor='").append(cor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
