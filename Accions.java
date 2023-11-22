import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Accions extends Thread {
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

    public Accions(Main main) {
        this.main = main;
        this.robot = main.getRobot();
    }

    // PRIORITAT D'AVANCAR EST --> NORD --> OEST --> SUD
    @Override
    public void run() {
        while (seguir) {
            Casella percepcions_actuals = robot.percebre();
            robot.afegirBC(percepcions_actuals);
            Direccions accio_anterior = robot.getAccioAnterior();

            boolean presentBC[] = new boolean[4];
            boolean potAvancar[] = new boolean[4];
            int presents = 0;

            try {
                sleep(125);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!percepcions_actuals.esPerillos()) {
                // CONSEGUIM INFORMACIO DE LES CASELLES ANAM A LA QUE MES ENS CONVENGUI
                // 1. SI HI HA NOMES UNA QUE NO ESTIGUI A BC ANAM AQUELLA
                // 2. SI HI HA MES D'UNA QUE NO ESTA A BC ANAM EN FUNCIO PRIORITAT AVANCAR
                // 3. SI TOTES ESTAN A BC ANAM A LA MENYS VISITADA
                // 4. SI TOTES ESTAN IGUAL DE VISITADES ANAM EN FUNCIO PRIORITAT
                if (robot.potAvancar(Direccions.ESTE)) {
                    potAvancar[Direccions.ESTE.valor] = true;
                    if (robot.estaBC(Direccions.ESTE)) {
                        presentBC[Direccions.ESTE.valor] = true;
                        presents++;
                    }
                }
                if (robot.potAvancar(Direccions.NORTE)) {
                    potAvancar[Direccions.NORTE.valor] = true;
                    if (robot.estaBC(Direccions.NORTE)) {
                        presentBC[Direccions.NORTE.valor] = true;
                        presents++;
                    }
                }
                if (robot.potAvancar(Direccions.OESTE)) {
                    potAvancar[Direccions.OESTE.valor] = true;
                    if (robot.estaBC(Direccions.OESTE)) {
                        presentBC[Direccions.OESTE.valor] = true;
                        presents++;
                    }
                }
                if (robot.potAvancar(Direccions.SUD)) {
                    potAvancar[Direccions.SUD.valor] = true;
                    if (robot.estaBC(Direccions.SUD)) {
                        presentBC[Direccions.SUD.valor] = true;
                        presents++;
                    }
                }
                if (presents == 3) {
                    // Ves a la que no esta present a la BC
                    // System.out.println("CAS 1");
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
                        continue;
                    // System.out.println("ADVANCEDNT");
                    Pair pairs[] = new Pair[3];
                    int botades = 0;
                    for (int i = 0; i < 4; i++) {
                        if (robot.potAvancar(Direccions.values()[i])) {
                            pairs[i-botades] = new Pair(robot.getVisitesBC(Direccions.values()[i]), Direccions.values()[i]);
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
                    System.out.println("CAS 2");
                    boolean avancat = false;
                    for (int i = 0; i < potAvancar.length; i++) {
                        if (potAvancar[i] && !presentBC[i]) {
                            robot.avancar(Direccions.values()[i]);
                            avancat = true;
                            break;
                        }
                    }
                    if (avancat)
                        continue;
                    Pair pairs[] = new Pair[presents];
                    int botades = 0;
                    for (int i = 0; i < 4; i++) {
                        if (robot.potAvancar(Direccions.values()[i])) {
                            pairs[i-botades] = new Pair(robot.getVisitesBC(Direccions.values()[i]), Direccions.values()[i]);
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
                    System.out.println("CAS 3");
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
                    System.out.println("CAS 4 ");
                    for (int i = 0; i < potAvancar.length; i++) {
                        if (potAvancar[i]) {
                            robot.avancar(Direccions.values()[i]);
                            break;
                        }
                    }
                }
            } else {
                // PER IMPLEMENTAR PERILLOSITAT
            }
        }
    }
}
