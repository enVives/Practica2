public class Robot {
    private Main main;

    private int orientacio; //1:EST 2:NORD 3:OEST 4:SUD
    private int tresor = 0;
    private int X;
    private int Y;

    public Robot(Main main) {
        this.main = main;
        this.orientacio = 1;
        this.X = 0;
        this.Y = main.getMapSize() - 1;
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
        this.main.notificar("Repintar");
    }

    public void collirTresor() {
        this.tresor++;
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

    public void PROVA_GIRAR(){
        this.orientacio = (this.orientacio == 1) ? 3 : 1;
    }
}
