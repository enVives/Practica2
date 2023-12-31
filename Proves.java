import java.util.Arrays;
import java.util.Stack;

public class Proves {
    private Main main;
    private boolean seguir = true;
    private Robot robot;

    private class Pair implements Comparable<Pair> {
        public int visites;
        public Direccions dir;

        public Pair(int vis, Direccions dir) {
            this.visites = vis;
            this.dir = dir;
        }

        @Override
        public int compareTo(Pair other) {
            int compareVisites = Integer.compare(this.visites, other.visites);
            if (compareVisites == 0) {
                return this.dir.compareTo(other.dir);
            }

            return compareVisites;
        }
    }

    public Proves(Main main) {
        this.robot = main.getRobot();
        this.main = main;
    }

    // PRIORITAT D'AVANCAR EST --> NORD --> OEST --> SUD
    public void prova() {
        Casella percepcions_actuals = robot.percebre();
        robot.afegirBC(percepcions_actuals);
        Direccions accio_anterior = robot.getAccioAnterior();

        boolean presentBC[] = new boolean[4];
        boolean potAvancar[] = new boolean[4];
        int presents = 0;

        // if (!percepcions_actuals.esPerillos()) {
        // CONSEGUIM INFORMACIO DE LES CASELLES ANAM A LA QUE MES ENS CONVENGUI
        // 1. SI HI HA NOMES UNA QUE NO ESTIGUI A BC ANAM AQUELLA
        // 2. SI HI HA MES D'UNA QUE NO ESTA A BC ANAM EN FUNCIO PRIORITAT AVANCAR
        // 3. SI TOTES ESTAN A BC ANAM A LA MENYS VISITADA
        // 4. SI TOTES ESTAN IGUAL DE VISITADES ANAM EN FUNCIO PRIORITAT
        Direccions[] direcciones = Direccions.values();

        for (Direccions direccion : direcciones) {
            if (robot.potAvancar(direccion)) {
                potAvancar[direccion.valor] = true;
                if (robot.estaBC(direccion)) {
                    presentBC[direccion.valor] = true;
                    presents++;
                }
            }
        }

        if (presents == 3) {
            // Ves a la que no esta present a la BC
            boolean avancat = false;
            for (int i = 0; i < presentBC.length; i++) {
                if (!presentBC[i]) {
                    if (robot.potAvancar(Direccions.values()[i])) {
                        robot.avancar(Direccions.values()[i]);
                        // System.out.println("ADVANCED!");
                        avancat = true;
                        break;
                    }
                }
            }
            if (avancat)
                return;
            // System.out.println("ADVANCEDNT");
            Pair pairs[] = new Pair[3];
            int botades = 0;
            for (int i = 0; i < 4; i++) {
                if (robot.potAvancar(Direccions.values()[i])) {
                    pairs[i - botades] = new Pair(robot.getVisitesBC(Direccions.values()[i]),
                            Direccions.values()[i]);
                } else {
                    botades++;
                }
            }
            Arrays.sort(pairs);
            for (int i = 0; i < pairs.length - botades; i++) {
                if (robot.potAvancar(pairs[i].dir)) {
                    robot.avancar(pairs[i].dir);
                    break;
                }
            }
        } else if (presents != 4 && presents != 0) { // presents == 1 || 2
            // Ves en funcio de la prioritat establerta llevant aquelles que estan BC
            boolean avancat = false;
            for (int i = 0; i < potAvancar.length; i++) {
                if (potAvancar[i] && !presentBC[i]) {
                    robot.avancar(Direccions.values()[i]);
                    avancat = true;
                    break;
                }
            }
            if (avancat)
                return;
            Pair pairs[] = new Pair[presents];
            int botades = 0;
            for (int i = 0; i < 4; i++) {
                if (robot.potAvancar(Direccions.values()[i])) {
                    pairs[i - botades] = new Pair(robot.getVisitesBC(Direccions.values()[i]),
                            Direccions.values()[i]);
                } else {
                    botades++;
                }
            }
            Arrays.sort(pairs);
            for (int i = 0; i < pairs.length; i++) {
                if (robot.potAvancar(pairs[i].dir)) {
                    robot.avancar(pairs[i].dir);
                    break;
                }
            }
        } else if (presents == 4) { // Totes caselles a BC
            // Pasa que no mira que se pugui efectuar es moviment
            Pair pairs[] = new Pair[4];
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = new Pair(robot.getVisitesBC(Direccions.values()[i]), Direccions.values()[i]);
            }
            Arrays.sort(pairs);
            for (int i = 0; i < pairs.length; i++) {
                if (robot.potAvancar(pairs[i].dir)) {
                    robot.avancar(pairs[i].dir);
                    break;
                }
            }
        } else if (presents == 0) { // Cap casella a BC
            // Ves en funcio de la prioritat establerta
            for (int i = 0; i < potAvancar.length; i++) {
                if (potAvancar[i]) {
                    robot.avancar(Direccions.values()[i]);
                    break;
                }
            }
        }
    }
}
