package estacionamento;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import veiculo.Ticket;
import veiculo.Veiculo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PersistenciaJSON {

    public static void salvarJson(Estacionamento estacionamento) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("teste.json"));
        JSONObject estacionamentoJson = new JSONObject();
        estacionamentoJson.put("nome", estacionamento.getNome());
        estacionamentoJson.put("vagasTotais", estacionamento.getVagasTotais());

        JSONArray vagas = new JSONArray();
        JSONObject vaga;
        JSONObject veiculo;
        JSONObject ticket;

        for (Vaga estacionamentoVagas : estacionamento.getVagas()) {
            vaga = new JSONObject();
            vaga.put("numero", estacionamentoVagas.getNumero());
            vaga.put("tipo", estacionamentoVagas.getTipo().name());

            Veiculo atual = estacionamentoVagas.getOcupante();
            veiculo = new JSONObject();
            if (atual != null) {
                veiculo.put("placa", atual.getPlaca());
                veiculo.put("modelo", atual.getModelo());
                veiculo.put("ano", atual.getAno());
                veiculo.put("cor", atual.getCor());
                veiculo.put("tipo", atual.getTipoDeVeiculo().name());

                Ticket ticketAtual = atual.getTicket();
                ticket = new JSONObject();
                ticket.put("entrada", ticketAtual.getEntrada().toString());
                if (ticketAtual.getSaida() != null) {
                    ticket.put("saida", ticketAtual.getSaida().toString());
                } else {
                    ticket.put("saida", null);
                }
                ticket.put("valor", ticketAtual.getValor());
            } else {
                veiculo.put("placa", null);
                veiculo.put("modelo", null);
                veiculo.put("ano", null);
                veiculo.put("cor", null);
                veiculo.put("tipo", null);

                ticket = new JSONObject();
                ticket.put("entrada", null);
                ticket.put("saida", null);
                ticket.put("valor", null);
            }

            veiculo.put("ticket", ticket);

            vaga.put("informacoes do veiculo", veiculo);

            vagas.add(vaga);
        }
        estacionamentoJson.put("vagas", vagas);

        bw.write(estacionamentoJson.toJSONString());
        bw.flush();
    }

    public static Estacionamento resgatarTodos() {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader("teste.json");

            JSONObject estacionamentoJson = (JSONObject) parser.parse(fileReader);
            String nome;
            ArrayList<Vaga> vagas;
            int vagasTotais;
            nome = estacionamentoJson.get("nome").toString();
            vagasTotais = Integer.parseInt(estacionamentoJson.get("vagasTotais").toString());
            vagas = new ArrayList<>();
            for (Object item : (JSONArray) estacionamentoJson.get("vagas")) {
                JSONObject vagaJSON = (JSONObject) item;
                int numero = Integer.parseInt(vagaJSON.get("numero").toString());
                TipoDeVeiculo tipo = TipoDeVeiculo.valueOf(vagaJSON.get("tipo").toString());
                JSONObject veiculoJson = (JSONObject) vagaJSON.get("informacoes do veiculo");

                Veiculo veiculo = null;
                Ticket ticket = null;
                if (veiculoJson.get("placa") != null) {
                    String placa = veiculoJson.get("placa").toString();
                    String marca = veiculoJson.get("marca").toString();
                    String modelo = veiculoJson.get("modelo").toString();
                    String ano = veiculoJson.get("ano").toString();
                    String cor = veiculoJson.get("cor").toString();
                    veiculo = new Veiculo(placa, marca, modelo, ano, cor, tipo);

                    JSONObject ticketJSON = (JSONObject) veiculoJson.get("ticket");
                    LocalDateTime entrada = null;
                    LocalDateTime saida;
                    if (ticketJSON.get("entrada") == null) {
                        entrada = null;
                        saida = null;
                    } else if (ticketJSON.get("saida") == null) {
                        saida = null;
                    } else {
                        entrada = LocalDateTime.parse(ticketJSON.get("entrada").toString());
                        saida = LocalDateTime.parse(ticketJSON.get("saida").toString());
                    }
                    float valor = (float) ticketJSON.get("valor");
                    ticket = new Ticket(entrada);
                    ticket.setSaida(saida);
                    ticket.setValor(valor);
                }
                Vaga vaga = new Vaga(tipo, numero, veiculo);

                if (veiculoJson.get("placa") != null) {
                    ticket.setVaga(vaga);
                }

                vagas.add(vaga);
            }
            return new Estacionamento(nome, vagas, vagasTotais);
        } catch(IOException | ParseException e)
    {
        throw new RuntimeException(e);
    }
}

}
