import java.util.Arrays;

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

    private Casella obtenirCasella(Direccions dir) {
        return this.BC[(this.Y + dir.movY) * this.main.getMapSize() + this.X + dir.movX];
    }

    private Casella obtenirCasella(int casX, int casY) {
        return this.BC[casY * this.main.getMapSize() + casX];
    }

    public boolean potAvancar(Direccions dir) {
        int mapSize = this.main.getMapSize();
        if (!(this.X + dir.movX < mapSize && this.X + dir.movX >= 0
                && this.Y + dir.movY < mapSize && this.Y + dir.movY >= 0))
            return false;

        // Si esta tauler i no hi ha perill avança
        if (!this.percepcio_actual.esPerillos()) {
            return true;
        }
        // Si hi ha perill mira i sabem que es monstre o precipici no avanca
        if (estaBC(dir)) {
            if (obtenirCasella(dir).isMonstruo() || obtenirCasella(dir).isPrecipicio()) {
                return false;
            } else {
                return true;
            }
        }

        // Si hi ha perill i no sabem si es monstre o precipici intentam esbrinar
        // Si no podem esbrinar no avancam
        Casella[] colindants = getColindats(dir);
        if (!estaBC(dir)) {
            if (isPrecipici(colindants)) {
                this.BC[(this.Y + dir.movY) * this.main.getMapSize() + this.X + dir.movX] = new Casella('P');
                return false;
            }
            if (isMonstre(colindants)) {
                // Accio matar fletxes ?
                this.BC[(this.Y + dir.movY) * this.main.getMapSize() + this.X + dir.movX] = new Casella('M');
                return false;
            }
            if (totesABC(colindants)) {
                return false;
            }
        }
        return true;
    }

    private boolean totesABC(Casella[] casellas) {
        boolean monstre = false;
        boolean precipici = false;
        if (percepcio_actual.isHedor()) {
            monstre = true;
            if (Arrays.stream(casellas).filter(casilla -> casilla != null).anyMatch(casilla -> !casilla.isHedor())) {
                monstre = false;
            }
        }
        if (percepcio_actual.isBrisa()) {
            precipici = true;
            if (Arrays.stream(casellas).filter(casilla -> casilla != null).anyMatch(casilla -> !casilla.isBrisa())) {
                precipici = false;
            }
        }
        return monstre || precipici;

    }

    private boolean isMonstre(Casella[] colindants) {
        for (int i = 0; i < colindants.length; i++) {
            if (colindants[i] == null)
                return false;
            if (!colindants[i].isHedor())
                return false;
        }
        return true;
    }

    private boolean isPrecipici(Casella[] colindants) {
        for (int i = 0; i < colindants.length; i++) {
            if (colindants[i] == null)
                return false;
            if (!colindants[i].isBrisa())
                return false;
        }
        return true;
    }

    // Despres de cada avancar s'ha de repintar el tauler
    public void avancar(Direccions dir) {
        // aumentam visites
        this.BC[this.Y * this.main.getMapSize() + this.X].augmentaVisites();
        while (this.orientacio != dir) {
            girar();
        }
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

    public Casella[] getColindats(Direccions dir) {
        // esquina 2 , canto 3, normal 4
        Casella caselles[];
        int colindants, casX, casY;
        int mapSize = this.main.getMapSize() - 1;
        casX = this.X + dir.movX;
        casY = this.Y + dir.movY;
        if ((casX == 0 || casX == mapSize) && (casY == 0 || casY == mapSize)) {
            colindants = 2;
        } else if ((casX == 0 || casX == mapSize) || (casY == 0 || casY == mapSize)) {
            colindants = 3;
        } else {
            colindants = 4;
        }
        caselles = new Casella[colindants];
        int ficats = 0;
        for (Direccions direccion : Direccions.values()) {
            int xCas = casX + direccion.movX;
            int yCas = casY + direccion.movY;
            if (xCas >= 0 && xCas <= mapSize && yCas >= 0 && yCas <= mapSize) {
                caselles[ficats] = this.obtenirCasella(xCas, yCas);
                ficats++;
            }
        }
        return caselles;
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
