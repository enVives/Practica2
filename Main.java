public class Main implements InterficiePrincipal {
    private Mapa mapa;
    private Interficie interficie;

    private static int mapSize = 7;
    private static int waitTime = 500;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    private Main() {
        this.mapa = new Mapa(this, mapSize);
        this.interficie = new Interficie(this);
        interficie.mostrar();
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void reiniciar_Mapa(){
        this.mapa = new Mapa(this, mapSize);
    }

    public void setSize(Integer size){
        this.mapSize = size;
    }

    public Interficie getInterficie() {
        return this.interficie;
    }


    public int getWaitTime() {
        return waitTime;
    }

    public void setAccioDelay(int delay) {
        waitTime = delay;
    }


    @Override
    public void notificar(String msg) {
        if(msg.contentEquals("Canviarll")){
            reiniciar_Mapa();
            this.interficie.repintar();
        }else if(msg.contentEquals("Repintar")){
            this.interficie.repintar();
        }
    }

}
