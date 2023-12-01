package estacionamento;

public enum TipoDeVeiculo {
    Carro(), Moto(), Caminhao();
    private float modificador = 1;

    TipoDeVeiculo() {
    }

    public static TipoDeVeiculo forInt(int id) {
        return values()[id];
    }

    public float getModificador() {
        return modificador;
    }

    public void setModificador(float modificador) {
        this.modificador = modificador;
    }
}
