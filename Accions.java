public class Accions extends Thread {
    private Main main;
    private boolean seguir = true;

    public Accions(Main main) {
        this.main = main;
    }

    // PRIORITAT D'AVANCAR OEST --> NORD --> EST --> SUD
    @Override
    public void run() {
        while (seguir) {
            Robot robot = main.getRobot();
            Casella[] percepcions_actuals = robot.getPercepcionsActuals();
            Casella[] percepcions_anteriors = robot.getPercepcionsAnteriors();
            int accio_anterior = robot.getAccioAnterior();

            if (percepcions_anteriors == null) {
                if (!(robot.getX() + 1 >= this.main.getMapSize())) {
                    while (robot.getOrientacio() != 1) {
                        robot.girar();
                        this.main.notificar("Repintar");
                        // ALGUNA ESPERA
                    }
                    robot.avancar();
                }
                if (!(robot.getY() - 1 >= this.main.getMapSize())) {
                    while (robot.getOrientacio() != 2) {
                        robot.girar();
                        this.main.notificar("Repintar");
                        // ALGUNA ESPERA
                    }
                    robot.avancar();
                }
                if (!(robot.getX() - 1 < 0)) {
                    while (robot.getOrientacio() != 3) {
                        robot.girar();
                        this.main.notificar("Repintar");
                        // ALGUNA ESPERA
                    }
                    robot.avancar();
                }
                if (!(robot.getY() + 1 < 0)) {
                    while (robot.getOrientacio() != 4) {
                        robot.girar();
                        this.main.notificar("Repintar");
                        // ALGUNA ESPERA
                    }
                    robot.avancar();
                }
                this.main.notificar("Repintar");
            }
        }
    }

}
