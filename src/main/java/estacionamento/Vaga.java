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

    public int getNumero() {
        return numero;
    }

    public Veiculo getOcupante() {
        return ocupante;
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
