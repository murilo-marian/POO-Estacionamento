package estacionamento;

import veiculo.Veiculo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Estacionamento {
    String nome;
    private ArrayList<Vaga> vagas = new ArrayList<>();
    private int vagasTotais;

    public Estacionamento(String nome, int vagasCarro, float modificadorCarro, int vagasMoto, float modificadorMoto, int vagasCaminhao, float modificadorCaminhao) {
        if (vagasCarro < 0 || vagasCaminhao < 0 || vagasMoto < 0) {
            throw new IllegalArgumentException("Vagas < 0");
        }

        this.setNome(nome);
        vagasTotais = vagasCarro + vagasMoto + vagasCaminhao;
        for (int i = 1; i <= vagasTotais; i++) {
            Vaga vaga;
            if (i <= vagasCarro) {
                vaga = new Vaga(TipoDeVeiculo.Carro, i);
            } else if (i <= vagasCarro + vagasMoto) {
                vaga = new Vaga(TipoDeVeiculo.Moto, i);
            } else {
                vaga = new Vaga(TipoDeVeiculo.Caminhao, i);
            }
            vagas.add(vaga);
        }
        TipoDeVeiculo.Carro.setModificador(modificadorCarro);
        TipoDeVeiculo.Moto.setModificador(modificadorMoto);
        TipoDeVeiculo.Caminhao.setModificador(modificadorCaminhao);
    }

    public Estacionamento(String nome, ArrayList<Vaga> vagas, int vagasTotais) {
        this.nome = nome;
        this.vagas = vagas;
        this.vagasTotais = vagasTotais;
    }

    public void estacionar(Veiculo veiculo) throws Exception {
        boolean encontrado = false;
        for (Vaga vaga : vagas) {
            if (vaga.tipo == veiculo.getTipoDeVeiculo() && vaga.ocupante == null) {
                vaga.ocupante = veiculo;
                vaga.ocupante.getTicket().setVaga(vaga);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("Não há vagas");
        }
    }

    public Veiculo encontrarVeiculo(String placa) {
        for (Vaga vaga : vagas) {
            if (vaga.ocupante != null) {
                if (vaga.ocupante.getPlaca().equals(placa)) {
                    return vaga.ocupante;
                }
            }
        }
        return null;
    }

    public void setarSaida(Veiculo veiculo) {
        veiculo.getTicket().setSaida(LocalDateTime.now());
        System.out.println(veiculo.getTicket());
        double valor = veiculo.getTicket().calcularValor();
        veiculo.getTicket().setValor(valor);
    }

    public void retirarVeiculo(Veiculo retirado) {
        for (Vaga vaga : vagas) {
            if (retirado.equals(vaga.ocupante)) {
                vaga.ocupante = null;
            }
        }
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

    public int getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(int vagasTotais) {
        this.vagasTotais = vagasTotais;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estacionamento{");
        sb.append("vagas=").append(vagas);
        sb.append('}');
        return sb.toString();
    }
}
