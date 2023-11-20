public class Robot {
    private Main main;

    private Casella[] BC;
    private Casella[] percepcio_actual; // 0:EST 1:NORD 2:OEST 3:SUD
    private Casella[] percepcio_anterior;
    private Direccions accioAnterior;

    private Direccions orientacio; // 1:EST 2:NORD 3:OEST 4:SUD
    private int tresor = 0;
    private int X;
    private int Y;

    public Robot(Main main) {
        this.main = main;
        this.orientacio = Direccions.ESTE;
        // this.X = 0;
        // this.Y = main.getMapSize() - 1;
        this.X = 3;
        this.Y = 5;
        this.BC = new Casella[main.getMapSize()*main.getMapSize()];
        // PROVES PER PERCEBRE
        actualitzarPercepcio();
    }

    public void girar() {
        if (this.orientacio == Direccions.SUD) {
            this.orientacio = Direccions.ESTE;
        } else if (this.orientacio == Direccions.ESTE){
            this.orientacio = Direccions.NORTE;
        } else if (this.orientacio == Direccions.NORTE){
            this.orientacio = Direccions.OESTE;
        } else if (this.orientacio == Direccions.OESTE){
            this.orientacio = Direccions.SUD;
        }
    }

    // Despres de cada avancar s'ha de repintar el tauler
    public void avancar(Direccions dir) {
        while (this.orientacio != dir) {
            girar();
        }
        switch (dir) {
            case ESTE:
                this.X++;
                break;
            case NORTE:
                this.Y--;
                break;
            case OESTE:
                this.X--;
                break;
            case SUD:
                this.Y++;
                break;
            default:
                System.out.println("ORIENTACIO NO POSIBLE");
        }
        // PROVES PER PERCEBRE
        this.accioAnterior = orientacio;
        actualitzarPercepcio();
        this.main.notificar("Repintar");
    }

    public void collirTresor() {
        this.tresor++;
    }

    public void percebre() {
        int vX[] = { 1, 0, -1, 0 };
        int vY[] = { 0, -1, 0, 1 };
        Casella caselles[] = new Casella[4];
        for (int i = 0; i < 4; i++) {
            if (this.X + vX[i] >= 0 && this.X + vX[i] < main.getMapSize()
                    && this.Y + vY[i] >= 0 && this.Y + vY[i] < main.getMapSize()) {
                caselles[i] = main.getMapa().getCasella(this.X + vX[i], this.Y + vY[i]);
            }
        }
        //actualitza percepcio_anterior
        if (percepcio_actual != null) percepcio_anterior = percepcio_actual;
        percepcio_actual = caselles;
    }

    public Direccions getOrientacio() {
        return this.orientacio;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public Casella[] getPercepcionsActuals() {
        return this.percepcio_actual;
    }

    public Casella[] getPercepcionsAnteriors() {
        return this.percepcio_anterior;
    }

    public Direccions getAccioAnterior() {
        return this.accioAnterior;
    }

    /*
     * Debugging imprimeix percepcions
     */
    public void actualitzarPercepcio() {
        percebre();
        printPercepcionsActuals();
    }

    public void printPercepcionsActuals() {
        for (Casella casella : percepcio_actual) {
            System.out.println(casella);
        }
    }

}
