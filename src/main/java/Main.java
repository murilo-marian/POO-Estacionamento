import estacionamento.Estacionamento;
import estacionamento.PersistenciaJSON;
import estacionamento.TipoDeVeiculo;
import estacionamento.Vaga;
import org.json.simple.parser.ParseException;
import veiculo.Ticket;
import veiculo.Veiculo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    static Scanner entrada = new Scanner(System.in);
    static Estacionamento estacionamento;

    public static void main(String[] args) {
        estacionamento = PersistenciaJSON.resgatarEstacionamento();

        if (estacionamento == null) {
            System.out.println("Criando novo estacionamento");
            System.out.print("Nome do estacionamento: ");
            String nome = entrada.nextLine();

            //TODO fazer um loop pra caso der merda
            //TODO calcular valor retorna -> setvalor()


            System.out.print("Número de vagas de Carro ");
            int vagasCarro = entrada.nextInt();
            System.out.print("Número de vagas de Moto ");
            int vagasMoto = entrada.nextInt();
            System.out.print("Número de vagas de Caminhão ");
            int vagasCaminhao = entrada.nextInt();


            estacionamento = new Estacionamento(nome, vagasCarro, 1, vagasMoto, 1, vagasCaminhao, 1);

            try {
                PersistenciaJSON.salvarJson(estacionamento);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Estacionamento " + estacionamento.getNome());

        boolean loop = true;
        do {
            System.out.println("              Menu              ");
            System.out.println("--------------------------------");
            System.out.println(" 0 - Estacionar Veículo");
            System.out.println(" 1 - Retirar Veículo");
            System.out.println(" 2 - Relatório");
            System.out.println(" 3 - Mostrar estado atual do estacionamento");
            System.out.println(" 4 - Sair");
            int escolha = entrada.nextInt();
            entrada.nextLine();

            switch (escolha) {
                case 0 -> estacionarVeiculo();
                case 1 -> retirarDoEstacionamento();
                case 2 -> imprimirRelatorio();
                case 3 -> mostrarEstacionamento();
                default -> loop = false;
            }
        } while (loop);
    }

    public static void estacionarVeiculo() {
        //String placa, String marca, String modelo, int ano, String cor
        System.out.println("Digite as especificações do veículo");

        System.out.print("Placa do veículo: ");
        String placa = entrada.nextLine();

        System.out.print("Fabricante do veículo: ");
        String marca = entrada.nextLine();

        System.out.print("Modelo do veículo: ");
        String modelo = entrada.nextLine();

        System.out.print("Ano do veículo: ");
        String ano = entrada.nextLine();

        System.out.print("Cor do veículo: ");
        String cor = entrada.nextLine();

        System.out.println("Tipo do veículo: ");
        System.out.println(" 0 - Carro");
        System.out.println(" 1 - Moto");
        System.out.println(" 2 - Caminhão");
        TipoDeVeiculo tipoDeVeiculo = TipoDeVeiculo.forInt(entrada.nextInt());

        Veiculo veiculo = new Veiculo(placa, marca, modelo, ano, cor, tipoDeVeiculo);
        try {
            estacionamento.estacionar(veiculo);
            try {
                PersistenciaJSON.salvarJson(estacionamento);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void retirarDoEstacionamento() {
        System.out.print("Digite a placa do veículo a ser retirada: ");
        String placa = entrada.nextLine();

        Veiculo retirado = estacionamento.encontrarVeiculo(placa);

        if (retirado != null) {
            System.out.println("--------------------------------");
            System.out.println("Veículo encontrado");
            System.out.println("Dados do veículo:");
            System.out.println("Placa: " + retirado.getPlaca());
            System.out.println("Categoria do veículo: " + retirado.getTipoDeVeiculo());
            System.out.println("Fabricante: " + retirado.getMarca());
            System.out.println("Modelo: " + retirado.getModelo());
            System.out.println("Ano: " + retirado.getAno());
            System.out.println("Cor: " + retirado.getCor());

            System.out.println("--------------------------------");
            System.out.println("Esses dados estão corretos? (S/N)");
            String resposta = entrada.nextLine().toUpperCase();

            if (resposta.equals("S")) {
                estacionamento.setarSaida(retirado);
                System.out.println("--------------------------------");
                System.out.println("Informações do Ticket:");
                System.out.println("Horario de Entrada: " + retirado.getTicket().getEntrada());
                System.out.println("Horario de Saída: " + retirado.getTicket().getSaida());
                System.out.println("Vaga estacionada: " + retirado.getTicket().getVaga().getNumero());
                System.out.println("Valor a ser pago: " + retirado.getTicket().getValor());
                System.out.println("--------------------------------");
                System.out.println("Pagamento confirmado");

                estacionamento.retirarVeiculo(retirado);

                try {
                    PersistenciaJSON.salvarJson(estacionamento);
                    PersistenciaJSON.salvarTicket(retirado.getTicket());
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Veículo retirado do estacionamento");
                System.out.println("--------------------------------");
            }
        } else {
            System.out.println("Veículo não encontrado");
        }
    }


    public static void imprimirRelatorio() {
        ArrayList<Ticket> tickets = PersistenciaJSON.resgatarTickets();
        System.out.println(tickets);
    }

    public static void mostrarEstacionamento() {

        String vagaVazia = """
                |---|
                |   |
                |   |""";
        String vagaMoto = """
                |---|
                | | |
                | | |""";
        String vagaCarro = """
                |---|
                | O |
                | O |""";
        String vagaCaminhao = """
                |---|
                |&O&|
                |&O&|""";

        System.out.println("Mostrando estado atual do estacionamento: ");



        int vagasTotais = estacionamento.getVagasTotais();
        String[] layout = new String[vagasTotais];

        int count = 0;
        for (Vaga vaga : estacionamento.getVagas()) {
            if (vaga.getOcupante() != null) {
                if (vaga.getTipo() == TipoDeVeiculo.Carro) {
                    layout[count] = vagaCarro;
                } else if (vaga.getTipo() == TipoDeVeiculo.Moto) {
                    layout[count] = vagaMoto;
                } else if (vaga.getTipo() == TipoDeVeiculo.Caminhao) {
                    layout[count] = vagaCaminhao;
                }
            } else {
                layout[count] = vagaVazia;
            }
            count++;
        }

        List<List<String>> fragments = Stream.of(layout)
                .map(x -> Stream.of(x.split("\\r\\n?|\\n")).collect(Collectors.toList())).toList();

        // join corresponding fragments to result lines, and join result lines
        int lines = fragments.stream().mapToInt(List::size).max().orElse(0);
        String result = IntStream.range(0, lines)
                .mapToObj(i -> fragments.stream().map(x -> x.get(i)).collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(result);
    }
}