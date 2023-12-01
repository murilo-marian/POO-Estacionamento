import estacionamento.*;
import org.json.simple.parser.ParseException;
import veiculo.Ticket;
import veiculo.Veiculo;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

            System.out.print("Número de vagas de Carro ");
            int vagasCarro = entrada.nextInt();
            System.out.print("Número de vagas de Moto ");
            int vagasMoto = entrada.nextInt();
            System.out.print("Número de vagas de Caminhão ");
            int vagasCaminhao = entrada.nextInt();
            entrada.nextLine();


            estacionamento = new Estacionamento(nome, vagasCarro, 1, vagasMoto, 1, vagasCaminhao, 1);

            try {
                PersistenciaJSON.salvarJson(estacionamento);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (new File("precoDiario.json").exists()) {
            PersistenciaJSON.resgatarPrecoDiario();
        }
        if (new File("modificadorDePreco.json").exists()) {
            PersistenciaJSON.resgatarModificadoresDePreco();
        }

        System.out.println("Estacionamento " + estacionamento.getNome());

        boolean loop = true;
        do {
            System.out.println("--------------------------------");
            System.out.println("|              Menu            |");
            System.out.println("--------------------------------");
            System.out.println(" 1 - Estacionar Veículo");
            System.out.println(" 2 - Retirar Veículo");
            System.out.println(" 3 - Relatório");
            System.out.println(" 4 - Mostrar estado atual do estacionamento");
            System.out.println(" 5 - Modificar valores");
            System.out.println(" 6 - Ver valores");
            System.out.println(" 7 - Sair");
            int escolha = entrada.nextInt();
            entrada.nextLine();

            switch (escolha) {
                case 1 -> estacionarVeiculo();
                case 2 -> retirarDoEstacionamento();
                case 3 -> relatorios();
                case 4 -> mostrarEstacionamento();
                case 5 -> modificarValores();
                case 6 -> verValores();
                default -> loop = false;
            }
        } while (loop);
    }

    private static void verValores() {
        System.out.println("--------------------------------");
        System.out.println("Visualizar valores");
        for (DiaDaSemana value : DiaDaSemana.values()) {
            System.out.println("Dia: " + value.name() + "  Valor: " + value.getValorPorHora());
        }
        for (TipoDeVeiculo value : TipoDeVeiculo.values()) {
            System.out.println("Tipo: " + value.name() + "  Modificador: " + value.getModificador());
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void modificarValores() {
        System.out.println("--------------------------------");
        System.out.println(" 1 - Modificar modificador de veículo");
        System.out.println(" 2 - Modificar valores padrão por hora");
        System.out.println(" 3 - Sair");
        int escolha = entrada.nextInt();
        entrada.nextLine();

        switch (escolha) {
            case 1 -> modificarModificador();
            case 2 -> modificarValorHora();
            default -> {
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void modificarModificador() {
        System.out.println("--------------------------------");
        System.out.print("Digite o nome do veículo que deseja modificar o modificador: ");
        String veiculo = entrada.nextLine();

        TipoDeVeiculo tipoDeVeiculo = TipoDeVeiculo.valueOf(veiculo.toUpperCase());

        System.out.print("Digite o valor do modificador desejado: ");
        double valor = entrada.nextDouble();

        tipoDeVeiculo.setModificador(valor);
        try {
            PersistenciaJSON.salvarModificador();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void modificarValorHora() {
        System.out.println("--------------------------------");
        System.out.print("Digite o nome do dia da semana que deseja modificar o valor por hora: ");
        String dia = entrada.nextLine();

        DiaDaSemana diaDaSemana = DiaDaSemana.getDia(DayOfWeek.valueOf(dia.toUpperCase()));

        System.out.print("Digite o valor por hora desejado: ");
        float valor = entrada.nextFloat();

        diaDaSemana.setValorPorHora(valor);
        try {
            PersistenciaJSON.salvarPrecoBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void estacionarVeiculo() {
        System.out.println("--------------------------------");
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
        entrada.nextLine();

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void retirarDoEstacionamento() {
        System.out.println("--------------------------------");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void relatorios() {
        System.out.println("--------------------------------");
        System.out.println("Relatórios: ");
        System.out.println("--------------------------------");
        System.out.println(" 1 - Relatório de faturamento");
        System.out.println(" 2 - Relatório de volume de estacionamentos");
        System.out.println(" 3 - Sair");
        int escolha = entrada.nextInt();
        entrada.nextLine();

        switch (escolha) {
            case 1 -> relatoriosFaturamento();
            case 2 -> relatoriosVolume();
            default -> {
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void relatoriosFaturamento() {
        System.out.println("--------------------------------");
        System.out.println("Relatórios de faturamento: ");
        System.out.println("--------------------------------");
        System.out.println(" 1 - Relatório de faturamento do mês atual");
        System.out.println(" 2 - Relatório de faturamento dos últimos 30 dias");
        System.out.println(" 3 - Relatório de faturamento do ano atual");
        System.out.println(" 4 - Relatório de faturamento - Período de tempo específico");
        System.out.println(" 5 - Relatório de faturamento - Geral");
        System.out.println(" 6 - Sair");

        int escolha = entrada.nextInt();
        entrada.nextLine();

        System.out.println("--------------------------------");

        ArrayList<Ticket> tickets = PersistenciaJSON.resgatarTickets();
        double faturamento;

        switch (escolha) {
            case 1 -> {
                System.out.println("Faturamento do Mês Atual");

                faturamento = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                        faturamento += ticket.getValor();
                    }
                }
                System.out.println("Faturamento: " + faturamento);
            }
            case 2 -> {
                System.out.println("Faturamento dos últimos 30 dias");

                faturamento = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().isBefore(LocalDateTime.now().minus(30, ChronoUnit.DAYS))) {
                        faturamento += ticket.getValor();
                    }
                }
                System.out.println("Faturamento: " + faturamento);
            }
            case 3 -> {
                System.out.println("Faturamento do ano atual");

                faturamento = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().getYear() == LocalDateTime.now().getYear()) {
                        faturamento += ticket.getValor();
                    }
                }
                System.out.println("Faturamento: " + faturamento);
            }
            case 4 -> {
                System.out.println("Faturamento de período de tempo específico");

                faturamento = 0;

                System.out.print("Data de inicio: ");
                String inicio = entrada.nextLine();

                System.out.print("Data de fim: ");
                String fim = entrada.nextLine();

                LocalDateTime inicioLdt = LocalDateTime.parse(inicio);
                LocalDateTime fimLdt = LocalDateTime.parse(fim);

                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().isAfter(inicioLdt) && ticket.getSaida().isBefore(fimLdt)) {
                        faturamento += ticket.getValor();
                    }
                }
                System.out.println("Faturamento: " + faturamento);
            }
            case 5 -> {
                System.out.println("Faturamento - Geral");

                faturamento = 0;
                for (Ticket ticket : tickets) {
                    faturamento += ticket.getValor();
                }
                System.out.println("Faturamento: " + faturamento);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static void relatoriosVolume() {
        System.out.println("--------------------------------");
        System.out.println("Relatórios de volume: ");
        System.out.println("--------------------------------");
        System.out.println(" 1 - Relatório de faturamento do mês atual");
        System.out.println(" 2 - Relatório de faturamento dos últimos 30 dias");
        System.out.println(" 3 - Relatório de faturamento do ano atual");
        System.out.println(" 4 - Relatório de faturamento - Período de tempo específico");
        System.out.println(" 5 - Relatório de faturamento - Geral");
        System.out.println(" 6 - Sair");

        int escolha = entrada.nextInt();
        entrada.nextLine();

        System.out.println("--------------------------------");

        ArrayList<Ticket> tickets = PersistenciaJSON.resgatarTickets();
        double volume;

        switch (escolha) {
            case 1 -> {
                System.out.println("Volume do Mês Atual");

                volume = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                        volume ++;
                    }
                }
                System.out.println("Volume por dia: " + volume / LocalDateTime.now().getDayOfMonth());
            }
            case 2 -> {
                System.out.println("Volume dos últimos 30 dias");

                volume = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().isBefore(LocalDateTime.now().minus(30, ChronoUnit.DAYS))) {
                        volume ++;
                    }
                }
                System.out.println("Volume por dia: " + volume / 30);
            }
            case 3 -> {
                System.out.println("Volume do ano atual");

                volume = 0;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().getYear() == LocalDateTime.now().getYear()) {
                        volume ++;
                    }
                }
                System.out.println("Volume por dia: " + volume / LocalDateTime.now().getDayOfYear());
            }
            case 4 -> {
                System.out.println("Volume de período de tempo específico");

                volume = 0;

                System.out.print("Data de inicio: ");
                String inicio = entrada.nextLine();

                System.out.print("Data de fim: ");
                String fim = entrada.nextLine();

                LocalDateTime inicioLdt = LocalDateTime.parse(inicio);
                LocalDateTime fimLdt = LocalDateTime.parse(fim);

                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().isAfter(inicioLdt) && ticket.getSaida().isBefore(fimLdt)) {
                        volume ++;
                    }
                }
                System.out.println("Volume por dia: " + volume / ChronoUnit.DAYS.between(inicioLdt, fimLdt));
            }
            case 5 -> {
                System.out.println("Volume - Geral");

                volume = 0;
                LocalDateTime futuro = LocalDateTime.MAX;
                for (Ticket ticket : tickets) {
                    if (ticket.getSaida().isBefore(futuro)) {
                        futuro = ticket.getSaida();
                    }
                    volume ++;
                }
                System.out.println("Volume por dia: " + volume / ChronoUnit.DAYS.between(futuro, LocalDateTime.now()));
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarEstacionamento() {
        System.out.println("--------------------------------");

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
                if (vaga.getTipo() == TipoDeVeiculo.CARRO) {
                    layout[count] = vagaCarro;
                } else if (vaga.getTipo() == TipoDeVeiculo.MOTO) {
                    layout[count] = vagaMoto;
                } else if (vaga.getTipo() == TipoDeVeiculo.CAMINHAO) {
                    layout[count] = vagaCaminhao;
                }
            } else {
                layout[count] = vagaVazia;
            }
            count++;
        }

        List<List<String>> fragments = Stream.of(layout)
                .map(x -> Stream.of(x.split("\\r\\n?|\\n")).collect(Collectors.toList())).toList();

        int lines = fragments.stream().mapToInt(List::size).max().orElse(0);
        String result = IntStream.range(0, lines)
                .mapToObj(i -> fragments.stream().map(x -> x.get(i)).collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(result);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}