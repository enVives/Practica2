public class Casella {

    private boolean hedor,brisa,resplandor,monstruo,precipicio;
    private boolean robot;

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public boolean isPrecipicio() {
        return precipicio;
    }

    public void setPrecipicio(boolean precipicio) {
        this.precipicio = precipicio;
    }

    public boolean isMonstruo() {
        return monstruo;
    }

    public void setMonstruo(boolean monstruo) {
        this.monstruo = monstruo;
    }

    public Casella(){
        hedor = false;
        brisa = false;
        resplandor = false;
        monstruo = false;
        precipicio = false;
        robot = false;
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
}
