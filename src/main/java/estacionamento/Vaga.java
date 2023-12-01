package estacionamento;

import veiculo.Veiculo;

public class Vaga {
    TipoDeVeiculo tipo;
    int numero;
    Veiculo ocupante = null;

    public Vaga(TipoDeVeiculo tipo, int numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public Vaga(TipoDeVeiculo tipo, int numero, Veiculo ocupante) {
        this.tipo = tipo;
        this.numero = numero;
        this.ocupante = ocupante;
    }

    public TipoDeVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVeiculo tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Veiculo getOcupante() {
        return ocupante;
    }

    public void setOcupante(Veiculo ocupante) {
        this.ocupante = ocupante;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vaga{");
        sb.append("vaga=").append(tipo);
        sb.append(", numero=").append(numero);
        sb.append(", ocupante=").append(ocupante);
        sb.append('}');
        return sb.toString();
    }
}
