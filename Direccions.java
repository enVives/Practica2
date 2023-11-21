public enum Direccions {
    ESTE(0,1,0),
    NORTE(1,0,-1),
    OESTE(2,-1,0),
    SUD(3,0,1);

    public final int valor;
    public final int movX;
    public final int movY;

    private Direccions(int num, int x, int y) {
        this.valor = num;
        this.movX = x;
        this.movY = y;
    }
}
