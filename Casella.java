public class Casella {

    private boolean hedor,brisa,resplandor;
    private boolean monstruo,precipicio;
    public int x,y; //per debugging
    private int visites = 0;

    public boolean isPrecipicio() {
        return precipicio;
    }

    public void setPrecipicio(boolean precipicio) {
        this.precipicio = precipicio;
    }

    public void augmentaVisites() {
        this.visites++;
    }

    public int getVisitada() {
        return this.visites;
    }

    public boolean isMonstruo() {
        return monstruo;
    }

    public void setMonstruo(boolean monstruo) {
        this.monstruo = monstruo;
    }

    public Casella(int X, int Y) {
        this.x = X;
        this.y = Y;
    }

    public Casella(char lletra){
        if (lletra == 'M') {
            this.monstruo = true;
        } else if (lletra == 'P') {
            this.precipicio = true;
        } else if (lletra == 'T') {
            this.resplandor = true;
        }
    }

    public void llevar_percepcions(){
        hedor = false;
        brisa = false;
    }

    public boolean isResplandor() {
        return resplandor;
    }

    public void setResplandor(boolean resplandor) {
        this.resplandor = resplandor;
    }

    public boolean isBrisa() {
        return brisa;
    }

    public void setBrisa(boolean brisa) {
        this.brisa = brisa;
    }

    public boolean isHedor() {
        return hedor;
    }

    public void setHedor(boolean hedor) {
        this.hedor = hedor;
    }

    public boolean esPerillos() {
        return this.brisa || this.hedor;
    }

    @Override
    public String toString() {
        return String.format("Hedor: %b, Brisa: %b, Resplandor:%b, Monstruo:%b, Preci.:%b, Visites:%d"
        ,hedor,brisa,resplandor,monstruo,precipicio,visites);
    }
}
