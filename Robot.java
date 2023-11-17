public class Robot {
    private Main main;

    private Casella[] percepcio_actual; //0:EST 1:NORD 2:OEST 3:SUD
    private Casella[] percepcio_anterior;

    private int orientacio; //1:EST 2:NORD 3:OEST 4:SUD
    private int tresor = 0;
    private int X;
    private int Y;

    public Robot(Main main) {
        this.main = main;
        this.orientacio = 1;
        this.X = 0;
        this.Y = main.getMapSize() - 1;
        //PROVES PER PERCEBRE
        actualitzarPercepcio();
    }

    public void girar(int direccio) {
        if (direccio == 0) {
            //Gira esquerra
            if(this.orientacio == 4) {
                this.orientacio = 1;
            } else {
                this.orientacio++;
            }
        } else if (direccio == 1) {
            //Gira dreta
            if(this.orientacio == 1) {
                this.orientacio = 4;
            } else {
                this.orientacio--;
            }
        } else {
            //error
            System.out.println("ERROR, DIRECCIO NO RECONEGUDA");
        }
        //PROVES PER PERCEBRE
        actualitzarPercepcio();
    }

    //Despres de cada avancar s'ha de repintar el tauler
    public void avancar() {
        switch(this.orientacio){
            case 1:
                if(!(this.X + 1 >= this.main.getMapSize())) this.X++;
                break;
            case 2:
                if(!(this.Y - 1 >= this.main.getMapSize())) this.Y--;
                break;
            case 3:
                if(!(this.X - 1 < 0)) this.X--;
                break;
            case 4:
                if(!(this.Y + 1 < 0)) this.Y++;
                break;
            default:
                System.out.println("ORIENTACIO NO POSIBLE");
        }
        //PROVES PER PERCEBRE
        actualitzarPercepcio();
        this.main.notificar("Repintar");
    }

    public void collirTresor() {
        this.tresor++;
    }

    public void percebre() {
        int vX[] = {1,0,-1,0};
        int vY[] = {0,-1,0,1};
        Casella caselles[] = new Casella[4];
        for (int i = 0; i < 4; i++) {
            if(this.X + vX[i] >= 0 && this.X +vX[i] < main.getMapSize()
                && this.Y + vY[i] >= 0 && this.Y +vY[i] < main.getMapSize()) {
                    caselles[i] = main.getMapa().getCasella(this.X + vX[i], this.Y + vY[i]);
            }
        }
        percepcio_actual = caselles;
    }

    public int getOrientacio() {
        return this.orientacio;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

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
