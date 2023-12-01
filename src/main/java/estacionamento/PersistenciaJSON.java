package estacionamento;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import veiculo.Ticket;
import veiculo.Veiculo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PersistenciaJSON {

    public static void salvarJson(Estacionamento estacionamento) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("estacionamento.json"));
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
                veiculo.put("marca", atual.getMarca());
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
                veiculo.put("marca", null);
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

    public static Estacionamento resgatarEstacionamento() {
        JSONParser parser = new JSONParser();

        try {
            File teste = new File("estacionamento.json");
            if (!teste.exists() || teste.length() == 0) {
                return null;
            }
            FileReader fileReader = new FileReader("estacionamento.json");

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
                    entrada = LocalDateTime.parse(ticketJSON.get("entrada").toString());
                    if (ticketJSON.get("saida") != null) {
                        saida = LocalDateTime.parse(ticketJSON.get("saida").toString());
                    } else {
                        saida = null;
                    }
                    Double valor = (Double) ticketJSON.get("valor");
                    ticket = new Ticket(entrada);
                    ticket.setSaida(saida);
                    ticket.setValor(valor);
                }
                Vaga vaga = new Vaga(tipo, numero, veiculo);

                if (veiculoJson.get("placa") != null) {
                    ticket.setVaga(vaga);
                    veiculo.setTicket(ticket);
                }

                vagas.add(vaga);
                fileReader.close();
            }
            return new Estacionamento(nome, vagas, vagasTotais);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void salvarTicket(Ticket ticket) throws IOException, ParseException {
        File teste = new File("tickets.json");
        JSONArray ticketsJSON = new JSONArray();

        if (teste.exists()) {
            JSONParser parser = new JSONParser();
            JSONArray arrayJSON = (JSONArray) parser.parse(new FileReader("tickets.json"));
            ticketsJSON = arrayJSON;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("tickets.json"));

        System.out.println("tamanho do tickets.json" + teste.length());
        if (!teste.exists()) {
            System.out.println("doesn't exist");
            bw.write("");
        }

        JSONObject ticketJSON = new JSONObject();
        ticketJSON.put("entrada", ticket.getEntrada().toString());
        if (ticket.getSaida() != null) {
            ticketJSON.put("saida", ticket.getSaida().toString());
        } else {
            ticketJSON.put("saida", null);
        }
        ticketJSON.put("valor", ticket.getValor());


        if (teste.length() != 0) {
            System.out.println("appending");

            ticketsJSON.add(ticketJSON);
            bw.write(ticketJSON.toJSONString());
        } else {
            System.out.println("writing");
            ticketsJSON.add(ticketJSON);
            bw.write(ticketsJSON.toJSONString());
        }
        bw.close();
    }

    public static ArrayList<Ticket> resgatarTickets() {
        JSONParser parser = new JSONParser();

        try {
            File teste = new File("tickets.json");
            if (!teste.exists() || teste.length() == 0) {
                return null;
            }
            FileReader fileReader = new FileReader("tickets.json");
            ArrayList<Ticket> tickets = new ArrayList<>();

            JSONArray ticketJSON = (JSONArray) parser.parse(fileReader);

            for (Object o : ticketJSON) {
                Ticket ticket;
                JSONObject obj = (JSONObject) o;
                ticket = new Ticket(LocalDateTime.parse(obj.get("entrada").toString()));
                ticket.setValor((Double) obj.get("valor"));
                ticket.setSaida(LocalDateTime.parse(obj.get("saida").toString()));
                tickets.add(ticket);
            }

            return tickets;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
