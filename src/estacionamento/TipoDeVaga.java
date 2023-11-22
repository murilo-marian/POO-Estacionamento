package estacionamento;

public enum TipoDeVaga {
    Carro(0), Moto(1), Caminhao(2);
    private int ordem;
    private float precoBase;

    TipoDeVaga(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public float getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(float precoBase) {
        this.precoBase = precoBase;
    }
}
