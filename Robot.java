public class Robot {
    private Main main;

    private Casella[] BC;
    private Casella percepcio_actual;
    private Direccions accioAnterior;

    private Direccions orientacio; // 1:EST 2:NORD 3:OEST 4:SUD
    private int tresor = 0;
    private int X;
    private int Y;

    public Robot(Main main) {
        this.main = main;
        this.orientacio = Direccions.ESTE;
        this.X = 0;
        this.Y = main.getMapSize() - 1;
        this.BC = new Casella[main.getMapSize() * main.getMapSize()];
    }

    public void girar() {
        if (this.orientacio == Direccions.SUD) {
            this.orientacio = Direccions.ESTE;
        } else if (this.orientacio == Direccions.ESTE) {
            this.orientacio = Direccions.NORTE;
        } else if (this.orientacio == Direccions.NORTE) {
            this.orientacio = Direccions.OESTE;
        } else if (this.orientacio == Direccions.OESTE) {
            this.orientacio = Direccions.SUD;
        }
    }

    public boolean potAvancar(Direccions dir) {
        int mapSize = this.main.getMapSize();
        return (this.X + dir.movX < mapSize && this.X + dir.movX >= 0
                && this.Y + dir.movY < mapSize && this.Y + dir.movY >= 0);
    }

    // Despres de cada avancar s'ha de repintar el tauler
    public void avancar(Direccions dir) {
        // aumentam visites
        this.BC[this.Y * this.main.getMapSize() + this.X].augmentaVisites();
        while (this.orientacio != dir) {
            girar();
        }
        System.out.printf("X: %d --> %d ; Y: %d --> %d\n", this.X, this.X + dir.movX, this.Y, this.Y + dir.movY);
        this.X += dir.movX;
        this.Y += dir.movY;
        this.accioAnterior = orientacio;
        this.main.notificar("Repintar");
    }

    public void collirTresor() {
        this.tresor++;
        // TODO: gestio llevar des mapa
    }

    public Casella percebre() {
        // Nomes podem utilitzar hedor brisa i resplandor!
        Casella cas = this.main.getMapa().getCasella(this.X, this.Y);
        this.percepcio_actual = cas;
        return cas;
    }

    public boolean estaBC(Direccions dir) {
        return (this.BC[(this.Y + dir.movY) * this.main.getMapSize() + this.X + dir.movX] != null);
    }

    public boolean estaBC(int X, int Y) {
        return (this.BC[Y * this.main.getMapSize() + X] == null);
    }

    public boolean estaBC(Direccions dir, int X, int Y) {
        return (this.BC[(Y + dir.movY) * this.main.getMapSize() + X + dir.movX] == null);
    }

    // Actualitzam BC desde la casella a la que ens trobam
    public void afegirBC(Casella cas) {
        if (this.X >= 0 && this.X < this.main.getMapSize() && this.Y >= 0 && this.Y < this.main.getMapSize()) {
            int posArray = this.Y * this.main.getMapSize() + this.X;
            if (this.BC[posArray] == null) {
                this.BC[posArray] = cas;
            }
        }
    }

    public int getVisitesBC(Direccions dir) {
        return this.BC[(this.Y + dir.movY) * this.main.getMapSize() + this.X + dir.movX].getVisitada();
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

    public Casella getPercepcionsActuals() {
        return this.percepcio_actual;
    }

    public Direccions getAccioAnterior() {
        return this.accioAnterior;
    }

    public Casella[] getBC() {
        return this.BC;
    }

    /*
     * Debugging imprimeix percepcions
     */
    public void actualitzarPercepcio() {
        percebre();
        // printPercepcionsActuals();
    }

}
