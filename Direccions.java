public enum Direccions {
    ESTE(0),
    NORTE(1),
    OESTE(2),
    SUD(3);

    public final int valor;

    private Direccions(int num) {
        this.valor = num;
    }
}
