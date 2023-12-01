package estacionamento;

public enum TipoDeVeiculo {
    Carro(0), Moto(1), Caminhao(2);
    private final int ORDEM;
    private float modificador = 1;

    TipoDeVeiculo(int ordem) {
        this.ORDEM = ordem;
    }

    public static TipoDeVeiculo forInt(int id) {
        return values()[id];
    }

    public int getORDEM() {
        return ORDEM;
    }

    public float getModificador() {
        return modificador;
    }

    public void setModificador(float modificador) {
        this.modificador = modificador;
    }
}
