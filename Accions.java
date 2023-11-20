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
            Direccions accio_anterior = robot.getAccioAnterior();

            // Donam per supossat que sempre mira dreta i inicialment no hi ha objectes
            // al voltant
            // MIRAR OPCIO SEPARAR GIRAR I AVANCAR
            if (percepcions_anteriors == null) {
                robot.avancar(Direccions.OESTE);
                main.getMapa().getCasella(robot.getX(), robot.getY()).setVisitada();
                seguir = false;
            } else {
                if (accio_anterior == Direccions.OESTE) {
                    if (!(percepcions_anteriors[Direccions.OESTE.valor].isHedor())
                            && !(percepcions_anteriors[Direccions.OESTE.valor].isBrisa())) {
                        robot.avancar(Direccions.OESTE);
                    } else if (!(percepcions_anteriors[Direccions.NORTE.valor].isHedor())
                            && !(percepcions_anteriors[Direccions.NORTE.valor].isBrisa())) {
                        robot.avancar(Direccions.NORTE);
                    } else if (!(percepcions_anteriors[Direccions.ESTE.valor].isHedor())
                            && !(percepcions_anteriors[Direccions.ESTE.valor].isBrisa())) {
                        robot.avancar(Direccions.ESTE);
                    } else if (!(percepcions_anteriors[Direccions.SUD.valor].isHedor())
                            && !(percepcions_anteriors[Direccions.SUD.valor].isBrisa())) {
                        robot.avancar(Direccions.SUD);
                    }
                    main.getMapa().getCasella(robot.getX(), robot.getY()).setVisitada();
                }
                else if (accio_anterior == Direccions.NORTE) {

                }
                else if (accio_anterior == Direccions.OESTE) {

                }
                else if (accio_anterior == Direccions.SUD) {

                }
            }
            robot.actualitzarPercepcio();
        }
    }
}
