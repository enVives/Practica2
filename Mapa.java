public class Mapa {
    private Main main;

    private Casella[][] disposicio; // false si la casella es negre
    private int size;
    private boolean robot = false;
    private Integer ntesoros;

    private int vX[] = {1,0,-1,0};
    private int vY[] = {0,-1,0,1};

    public Mapa(Main main, int size) {
        this.main = main;
        this.size = size;
        this.disposicio = new Casella[size][size];

        ntesoros =0;

        for(int i =0;i<size;i++){
            for(int j = 0; j<size;j++){
                disposicio[i][j] = new Casella(i,j);
            }
        }
    }

    public int getSize(){
        return size;
    }

    public Casella getCasella(int X, int Y){
       if (X >= this.size || X < 0 || Y >= this.size || Y < 0) //si la casella es a fora no feim res
            return null;
        return disposicio[X][Y]; 
    }

    public void put_precipicio(int X, int Y){
       if (X >= this.size || X < 0 || Y >= this.size || Y < 0) //si la casella es a fora no feim res
            return;
        
        //si a la casella hi ha el robot, no feim res    
        if(main.getRobot().getX() == X && main.getRobot().getY() == Y) return;
        for (int i = 0; i < 4; i++) {
            if(main.getRobot().getX()+vX[i] == X && main.getRobot().getY()+vY[i] == Y) return;
        } 

        if(disposicio[X][Y].isPrecipicio()){ //si ja hi ha un precipici, el llevam
            disposicio[X][Y].setPrecipicio(false);
        }else{
            if(disposicio[X][Y].isMonstruo()){ //si hi ha un monstre l'haurem de llevar a ell i les percepcions
                put_monstruo(X, Y);
            }else if(disposicio[X][Y].isResplandor()){ //si hi ha un tresor a la casella, es llevarà el tresor
                put_tesoro(X, Y);
            }
            disposicio[X][Y].setPrecipicio(true);
        }

        actualitzar_caselles();
    }


    public void actualitzar_caselles(){

        for(int i =0;i<size;i++){
            for(int j = 0; j<size;j++){
                disposicio[i][j].llevar_percepcions();;
            }
        }
        for(int i =0;i<size;i++){
            for(int j = 0; j<size;j++){
                Casella casella = disposicio[i][j];

                if(casella.isPrecipicio()){
                    put_brisa(i+1, j);
                    put_brisa(i-1, j);
                    put_brisa(i, j+1);
                    put_brisa(i, j-1);
                }else if(casella.isMonstruo()){
                    put_hedor(i+1,j);
                    put_hedor(i-1,j);
                    put_hedor(i,j+1);
                    put_hedor(i,j-1);
                }
            }
        }
        //cada pic que possam un objecte pot estar vora el robot, hem d'actualitzar
        main.getRobot().actualitzarPercepcio();
    }

    public void put_brisa(int X, int Y){
        if (X >= this.size || X < 0 || Y >= this.size || Y < 0) //si la casella es a fora no feim res
            return;
        
        disposicio[X][Y].setBrisa(true); //tenir en compte que esteim afegint la percepcio de brisa
        //a una casella que pot ser que ja tengui un monstre o un precipici.
    }

    public void put_hedor(int X, int Y){
        if (X >= this.size || X < 0 || Y >= this.size || Y < 0) //si la casella es a fora no feim res
            return;
        
        disposicio[X][Y].setHedor(true); //tenir en compte que esteim afegint la percepcio de brisa
        //a una casella que pot ser que ja tengui un monstre o un precipici.
    }

    public void put_monstruo(int X, int Y){
        if (X >= this.size || X < 0 || Y >= this.size || Y < 0) //si la casella es a fora no feim res
            return;

        //si a la casella hi ha el robot, no feim res
        if(main.getRobot().getX() == X && main.getRobot().getY() == Y) return;
        for (int i = 0; i < 4; i++) {
            if(main.getRobot().getX()+vX[i] == X && main.getRobot().getY()+vY[i] == Y) return;
        }
        if(disposicio[X][Y].isMonstruo()){ //si ja hi ha un precipici, el llevam
            disposicio[X][Y].setMonstruo(false);
        }else{
            if(disposicio[X][Y].isPrecipicio()){ //si hi ha un precipici l'haurem de llevar a ell i les percepcions
                put_precipicio(X,Y);
            }else if(disposicio[X][Y].isResplandor()){ //si hi ha un tresor a la casella, es llevarà el tresor
                put_tesoro(X, Y);
            }
            disposicio[X][Y].setMonstruo(true);
        }

        actualitzar_caselles();

    }

    public void put_tesoro(int X, int Y){
        if (X >= this.size || X < 0 || Y >= this.size || Y < 0)
            return;

        //si a la casella hi ha el robot, no feim res
        if(main.getRobot().getX() == X && main.getRobot().getY() == Y) return;
        
        if(disposicio[X][Y].isPrecipicio()){
            put_precipicio(X, Y);
        }else if(disposicio[X][Y].isMonstruo()){
            put_monstruo(X,Y);
        }

        disposicio[X][Y].setResplandor(!disposicio[X][Y].isResplandor());


        actualitzar_caselles();
    }

    public void imprimirMapa() {
        for (int i = 0; i < disposicio.length; i++) {
            for (int j = 0; j < disposicio.length; j++) {
                System.out.print(disposicio[i][j].getVisitada() + " ");
            }
            System.out.println();
        }
    }

}
