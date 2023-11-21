import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Accions extends Thread {
    private Main main;
    private boolean seguir = true;
    private Robot robot;

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
                    System.out.println("CAS 1");
                    for (int i = 0; i < presentBC.length; i++) {
                        if (!presentBC[i]) {
                            robot.avancar(Direccions.values()[i]);
                            break;
                        }
                    }
                } else if (presents != 4 && presents != 0) { // presents == 1 || 2
                    // Ves en funcio de la prioritat establerta llevant aquelles que estan BC
                    System.out.println("CAS 2");
                    for (int i = 0; i < potAvancar.length; i++) {
                        if (potAvancar[i] && !presentBC[i]) {
                            robot.avancar(Direccions.values()[i]);
                            break;
                        }
                    }
                } else if (presents == 4) { // Totes caselles a BC
                    //Pasa que no mira que se pugui efectuar es moviment
                    //TODO: implementar aquest
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
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
