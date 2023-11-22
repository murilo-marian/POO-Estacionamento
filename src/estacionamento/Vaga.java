package estacionamento;

import veiculo.Veiculo;

public class Vaga {
    TipoDeVaga vaga;
    int numero;
    Veiculo ocupante = null;

    public Vaga(TipoDeVaga vaga, int numero) {
        this.vaga = vaga;
        this.numero = numero;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vaga{");
        sb.append("vaga=").append(vaga);
        sb.append(", numero=").append(numero);
        sb.append(", ocupante=").append(ocupante);
        sb.append('}');
        return sb.toString();
    }
}
