package estacionamento;

import java.util.ArrayList;

public class Estacionamento {
    String nome;
    private ArrayList<Vaga> vagas = new ArrayList();

    public Estacionamento(String nome, int vagasCarro, float precoBaseCarro, int vagasMoto, float precoBaseMoto, int vagasCaminhao, float precoBaseCaminhao) {
        this.setNome(nome);
        int vagasTotais = vagasCarro + vagasMoto + vagasCaminhao;
        for (int i = 1; i <= vagasTotais; i++) {
            Vaga vaga = null;
            if (i <= vagasCarro) {
                vaga = new Vaga(TipoDeVaga.Carro, i);
            } else if (i <= vagasCarro + vagasMoto) {
                vaga = new Vaga(TipoDeVaga.Moto, i);
            } else {
                vaga = new Vaga(TipoDeVaga.Caminhao, i);
            }
            vagas.add(vaga);
        }
        TipoDeVaga.Carro.setPrecoBase(precoBaseCarro);
        TipoDeVaga.Moto.setPrecoBase(precoBaseMoto);
        TipoDeVaga.Caminhao.setPrecoBase(precoBaseCaminhao);
    }

    public ArrayList<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(ArrayList<Vaga> vagas) {
        this.vagas = vagas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estacionamento{");
        sb.append("vagas=").append(vagas);
        sb.append('}');
        return sb.toString();
    }
}
