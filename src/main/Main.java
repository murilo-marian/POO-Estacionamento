package main;

import estacionamento.Estacionamento;
import estacionamento.TipoDeVaga;
import veiculo.Carro;
import veiculo.Veiculo;

import java.util.Scanner;

public class Main {
    static Scanner entrada = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Criando novo estacionamento");
        System.out.print("Nome do estacionamento: ");
        String nome = entrada.nextLine();/*
        TipoDeVaga tipoDeVaga = TipoDeVaga.Carro;
        for (int i = 0; i < 3; i++) {
            System.out.println("Número de vagas de " + tipoDeVaga);
        }*/
        //TODO fazer depois num loop só
        //TODO fazer um loop pra caso der merda
        //TODO salvar em json o estacionamento, criar se não houver
        //TODO adicionar historico de precos (outra classe)

        System.out.print("Número de vagas de Carro ");
        int vagasCarro = entrada.nextInt();
        System.out.print("Número de vagas de Moto ");
        int vagasMoto = entrada.nextInt();
        System.out.print("Número de vagas de Caminhão ");
        int vagasCaminhão = entrada.nextInt();


        Estacionamento estacionamento = new Estacionamento(nome, vagasCarro, 1, vagasMoto, 1, vagasCaminhão, 1);

        System.out.println("Estacionamento " + estacionamento.getNome());


        do {
            System.out.println("              Menu              ");
            System.out.println("--------------------------------");
            System.out.println(" 0 - Estacionar Veículo");
            System.out.println(" 1 - Retirar Veículo");
            System.out.println(" 2 - Relatório");
            System.out.println(" 3 - Mostrar estado atual do estacionamento");
            int escolha = entrada.nextInt();
            entrada.nextLine();

            switch (escolha) {
                case 0 -> estacionarVeiculo();
                case 1 -> retirarDoEstacionamento();
                case 2 -> imprimirRelatorio();
                case 3 -> mostrarEstacionamento();
            }
        } while (true);
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
        int ano = entrada.nextInt();
        entrada.nextLine();

        System.out.println("Cor do veículo: ");
        String cor = entrada.nextLine();

        /*Veiculo veiculo = new Veiculo(placa, marca, modelo, ano, cor);*/
    }

    public static void retirarDoEstacionamento() {

    }

    public static void imprimirRelatorio() {

    }

    public static void mostrarEstacionamento() {

    }
}