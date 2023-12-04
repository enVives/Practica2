public class Main implements InterficiePrincipal {
    private Mapa mapa;
    private Robot robot;
    private Interficie interficie;
    private Accions accions;
    private Proves proves; //d'un en un

    private static int mapSize = 7;
    private static int waitTime = 500;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    private Main() {
        this.mapa = new Mapa(this, mapSize);
        this.robot = new Robot(this);
        this.interficie = new Interficie(this);
        this.accions = new Accions(this);
        this.proves = new Proves(this);
        interficie.mostrar();
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public Robot getRobot() {
        return this.robot;
    }

    public void reiniciar_Mapa(){
        this.robot = new Robot(this);
        this.accions = new Accions(this);
        this.mapa = new Mapa(this, mapSize);
    }

    public void setSize(Integer size){
        mapSize = size;
    }

    public Interficie getInterficie() {
        return this.interficie;
    }


    public int getWaitTime() {
        return waitTime;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setAccioDelay(int delay) {
        waitTime = delay;
    }

    public Accions getAccions() {
        return this.accions;
    }

    @Override
    public void notificar(String msg) {
        if(msg.contentEquals("Canviarll")){
            reiniciar_Mapa();
            this.interficie.repintar();
        } else if (msg.contentEquals("Repintar")) {
            this.interficie.repintar();
        } else if (msg.contentEquals("Comencar")) {
            if (accions.changeSeguir()) {
                accions.start();
            } else {
                try {
                    accions.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accions = new Accions(this);
            }
        } else if(msg.contentEquals("Proves")) {
            proves.prova();
        }
    }

}
