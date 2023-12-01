package estacionamento;

public enum TipoDeVeiculo {
    CARRO(), MOTO(), CAMINHAO();
    private double modificador = 1;

    TipoDeVeiculo() {
    }

    public static TipoDeVeiculo forInt(int id) {
        return values()[id];
    }

    public double getModificador() {
        return modificador;
    }

    public void setModificador(double modificador) {
        this.modificador = modificador;
    }
}
