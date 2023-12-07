import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.AclFileAttributeView;

public class Interficie extends JFrame {
    private Main main;
    private final static int mapDimension = 600;
    private int lineSize;
    private panellMapa panellPintar;
    private Integer torn;
    private BufferedImage tresor;
    private BufferedImage monstre;
    private boolean bloqueig;
    private panellBotons botons;
    private int fletxes;

    public Interficie(Main main) {
        super("Robot");
        this.main = main;
        fletxes = 0;
        torn = null;
        bloqueig = false;
        try {
            tresor = ImageIO.read(new File("tresor.png"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        try {
            // Load the image from a file (change the file path accordingly)
            monstre = ImageIO.read(new File("monstre.png"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        this.setLayout(new BorderLayout());
        panellPintar = new panellMapa(this);
        this.add(panellPintar, BorderLayout.CENTER);
        botons = new panellBotons(main, this);
        this.add(botons, BorderLayout.WEST);
        this.pack();
    }

    public void mostrar() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public Integer fletxes() {
        return fletxes;
    }

    public void afegeix_fletxes() {
        fletxes++;
    }

    public void lleva_fletxes() {
        fletxes--;
    }

    public void repintar() {
        panellPintar.repaint();
    }

    public void acabat() {
        JOptionPane.showMessageDialog(Interficie.this, "Has Guanyat");
        main.notificar("Reiniciar");
    }

    public void reiniciar_boto() {
        botons.reiniciar_boto_aturat();
    }

    private class panellBotons extends JPanel implements ActionListener {

        JButton tempsAccio;
        JButton llargMapa;
        JButton activarRobot;
        JButton posarPrecipici;
        JButton posarMonstruo;
        JButton posarTresor;
        JButton avancar;
        JButton girar;
        JButton fletxa;
        Opcions opcions;

        private class Opcions extends JFrame implements ActionListener {
            JButton nord;
            JButton sud;
            JButton est;
            JButton oest;
            JPanel panell;
            Main main;
            Interficie interficie;

            public Opcions(Main main, Interficie interficie) {
                super("Direcció de la Fletxa");
                this.interficie = interficie;
                this.setBounds(250, 250, 300, 200);
                this.setLayout(new BorderLayout());
                panell = new JPanel();
                this.main = main;

                nord = new JButton("Nord");
                sud = new JButton("Sud");
                est = new JButton("Est");
                oest = new JButton("Oest");

                panell.setLayout(new GridLayout(1, 4));

                nord.addActionListener(this);
                sud.addActionListener(this);
                est.addActionListener(this);
                oest.addActionListener(this);

                panell.add(nord);
                panell.add(sud);
                panell.add(est);
                panell.add(oest);

                this.add(panell);
            }

            public void mostrar() {
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nord) {
                    System.out.println("Dispara cap al nord");
                    interficie.lleva_fletxes();
                    System.out.println("Numero de fletxes que queden: " + interficie.fletxes());
                    main.getMapa().mata_monstruo(1);
                    this.setVisible(false);
                } else if (e.getSource() == sud) {
                    System.out.println("Dispara cap al sud");
                    interficie.lleva_fletxes();
                    System.out.println("Numero de fletxes que queden: " + interficie.fletxes());
                    main.getMapa().mata_monstruo(2);
                    this.setVisible(false);
                } else if (e.getSource() == est) {
                    System.out.println("Dispara cap al est");
                    interficie.lleva_fletxes();
                    System.out.println("Numero de fletxes que queden: " + interficie.fletxes());
                    main.getMapa().mata_monstruo(3);
                    this.setVisible(false);
                } else if (e.getSource() == oest) {
                    System.out.println("Dispara cap al oest");
                    interficie.lleva_fletxes();
                    System.out.println("Numero de fletxes que queden: " + interficie.fletxes());
                    main.getMapa().mata_monstruo(4);
                    this.setVisible(false);
                }
            }
        }

        private Main main;
        private Interficie interficie;

        public panellBotons(Main main, Interficie interficie) {
            this.main = main;
            this.interficie = interficie;
            setLayout(new GridLayout(7, 1));
            opcions = new Opcions(main, interficie);
            posarPrecipici = new JButton("Precipici");
            posarMonstruo = new JButton("Monstre");
            posarTresor = new JButton("Tresor"); // Pensar a veure si s'ha de posar un botó per el robot.
            fletxa = new JButton("Fletxa");

            tempsAccio = new JButton("Canviar Temps d'Acció");
            llargMapa = new JButton("Canviar LLargària Mapa");
            activarRobot = new JButton("ATURAT");

            tempsAccio.addActionListener(this);
            llargMapa.addActionListener(this);
            activarRobot.addActionListener(this);
            posarMonstruo.addActionListener(this);
            posarPrecipici.addActionListener(this);
            posarTresor.addActionListener(this);
            fletxa.addActionListener(this);

            this.add(activarRobot);
            this.add(tempsAccio);
            this.add(llargMapa);
            this.add(posarPrecipici);
            this.add(posarMonstruo);
            this.add(posarTresor);
            this.add(fletxa);
        }

        public void reiniciar_boto_aturat() {
            activarRobot.setText("ATURAT");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == activarRobot) {
                JButton boto = (JButton) arg0.getSource();
                if (boto.getText().equals("ATURAT")) {
                    if (main.getMapa().getTresors() == 0) {
                        System.out.println("No podem arrancar si no hi ha cap tresor");
                    } else {
                        bloqueig = true;
                        torn = null;
                        boto.setText("ACTIVAT");
                        main.notificar("Comencar"); // de un en un
                    }

                } else {
                    bloqueig = false;
                    boto.setText("ATURAT");
                    main.notificar("Comencar"); // de un en un
                }
            } else if (arg0.getSource() == tempsAccio) {
                String entrada = JOptionPane.showInputDialog(this.getParent(), "Nou temps entre acció (ms)");
                if (entrada != null) {
                    try {
                        int cifra = Integer.parseInt(entrada);
                        this.main.setAccioDelay(cifra);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this.getParent(), "Introdueix només una xifra", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (arg0.getSource() == llargMapa) {
                if (!bloqueig) {
                    main.getAccions().changeSeguir();
                    String entrada = JOptionPane.showInputDialog(this.getParent(), "Introdueix la llargaria desitjada");
                    if (entrada != null) {
                        try {
                            int cifra = Integer.parseInt(entrada);
                            this.main.setSize(cifra);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this.getParent(), "Introdueix només una xifra", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    main.notificar("Canviarll");
                }

            } else if (arg0.getSource() == posarPrecipici) {
                if (!bloqueig) {
                    torn = 1;
                } else {
                    torn = null;
                }

            } else if (arg0.getSource() == posarMonstruo) {
                if (!bloqueig) {
                    torn = 2;
                } else {
                    torn = null;
                }

            } else if (arg0.getSource() == posarTresor) {
                if (!bloqueig) {
                    torn = 3;
                } else {
                    torn = null;
                }

            } else if (arg0.getSource() == fletxa) {
                if (!bloqueig) {
                    if (interficie.fletxes() <= 0) {
                        System.out.println("No te queden fletxes per disparar!!!");
                    } else {
                        opcions.mostrar();
                    }
                } else {
                    System.out.println("Hem d'aturar el robot si volem disparar cap una direcció");
                }

            }
        }
    }

    private class panellMapa extends JPanel implements MouseListener {
        private BufferedImage bima;

        public panellMapa(JFrame interficie) {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            addMouseListener(this);
            bima = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int dimCasella = mapDimension / main.getMapa().getSize();
            int casX = e.getX() / (dimCasella);
            int casY = e.getY() / (dimCasella);
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (torn != null) {
                    switch (torn) {
                        case 1:
                            main.getMapa().put_precipicio(casX, casY);
                            break;
                        case 2:
                            main.getMapa().put_monstruo(casX, casY);
                            break;
                        case 3:
                            main.getMapa().put_tesoro(casX, casY);
                            break;
                    }
                    main.notificar("Repintar");
                }

            } /*
               * else if (SwingUtilities.isRightMouseButton(e)) {
               * if (main.getMapa().getRobot()) {
               * return;
               * }
               * if (main.getMapa().getCasella(casX, casY)) {
               * return;
               * }
               * main.nouAgent(casX, casY);
               * }
               */
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void repaint() {
            if (this.getGraphics() != null)
                paint(this.getGraphics());
        }

        public Dimension getPreferredSize() {
            int panelDimension = (mapDimension / main.getMapa().getSize()) * main.getMapa().getSize();
            return new Dimension(panelDimension, panelDimension);
        }

        @Override
        public void paint(Graphics gr) {
            Graphics2D pinta = (Graphics2D) gr;

            bima = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            bima.getGraphics().setColor(Color.white);
            bima.getGraphics().fillRect(0, 0, bima.getWidth(), bima.getHeight());

            Graphics2D g2 = (Graphics2D) bima.getGraphics();
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
            lineSize = mapDimension / main.getMapa().getSize();
            // es [2] es es que apunta
            Point trianglePoints[] = {
                    new Point(lineSize / 5, (lineSize / 6) * 1),
                    new Point(lineSize / 5, (lineSize / 6) * 5),
                    new Point((lineSize / 5) * 4, (lineSize / 6) * 3)
            };
            for (int i = 1; i <= main.getMapa().getSize(); i++) {
                g2.drawLine(0, lineSize * i, mapDimension, lineSize * i);
                g2.drawLine(lineSize * i, 0, lineSize * i, mapDimension);
            }
            for (int i = 0; i < main.getMapa().getSize(); i++) {
                for (int j = 0; j < main.getMapa().getSize(); j++) {
                    /*
                     * if (main.getMapa().getCasella(i, j)) {
                     * g2.fillRect(lineSize * i, lineSize * j, lineSize, lineSize);
                     * }
                     */
                    Casella casella = main.getMapa().getCasella(i, j);
                    Integer desplx = 0;

                    if (casella.isMonstruo()) {
                        g2.drawImage(monstre, (lineSize * i), (lineSize * j), this);
                    } else if (casella.isPrecipicio()) {
                        g2.fillRect(lineSize * i, lineSize * j, lineSize, lineSize);
                    } else {
                        if (casella.isResplandor()) { // pintar primer resplandor i després percepcions
                            g2.drawImage(tresor, (lineSize * i), (lineSize * j),
                                    this);
                            desplx += (lineSize / 2);
                        }

                        g2.setFont(new Font("Arial", Font.BOLD, 20));
                        g2.setColor(Color.BLACK);

                        if (casella.isBrisa()) {
                            g2.drawString("Brisa", (lineSize * i) + 5, (lineSize * j) + 20 + desplx);
                            desplx += (lineSize / 4);
                        }

                        if (casella.isHedor()) {
                            g2.drawString("Hedor", (lineSize * i) + 5, (lineSize * j) + 20 + desplx);
                        }
                        // if(casella.isRobot()){
                        // g2.setColor(Color.RED); // pinta bolla
                        // int radio = lineSize / 4;
                        // g2.fillOval(((lineSize * i)+ lineSize / 2) - radio, ((lineSize * j)+ lineSize
                        // / 2) - radio, radio * 2, radio * 2);

                        // }

                    }
                }
            }
            // Pintam robot
            Polygon triangle = new Polygon();
            int xRobot = main.getRobot().getX();
            int yRobot = main.getRobot().getY();
            switch (main.getRobot().getOrientacio()) {
                case ESTE:
                    triangle.addPoint(trianglePoints[0].x + lineSize * xRobot, trianglePoints[0].y + lineSize * yRobot);
                    triangle.addPoint(trianglePoints[1].x + lineSize * xRobot, trianglePoints[1].y + lineSize * yRobot);
                    triangle.addPoint(trianglePoints[2].x + lineSize * xRobot, trianglePoints[2].y + lineSize * yRobot);
                    break;
                case NORTE:
                    triangle.addPoint(trianglePoints[1].x + lineSize * xRobot, trianglePoints[1].y + lineSize * yRobot);
                    triangle.addPoint((lineSize - trianglePoints[1].x) + lineSize * xRobot,
                            trianglePoints[1].y + lineSize * yRobot);
                    triangle.addPoint(lineSize / 2 + lineSize * xRobot,
                            (lineSize - trianglePoints[2].x) + lineSize * yRobot);
                    break;
                case OESTE:
                    triangle.addPoint((lineSize - trianglePoints[0].x) + lineSize * xRobot,
                            (trianglePoints[0].y) + lineSize * yRobot);
                    triangle.addPoint((lineSize - trianglePoints[1].x) + lineSize * xRobot,
                            (trianglePoints[1].y) + lineSize * yRobot);
                    triangle.addPoint((lineSize - trianglePoints[2].x) + lineSize * xRobot,
                            (trianglePoints[2].y) + lineSize * yRobot);
                    break;
                case SUD:
                    triangle.addPoint(trianglePoints[1].x + lineSize * xRobot,
                            lineSize - trianglePoints[1].y + lineSize * yRobot);
                    triangle.addPoint((lineSize - trianglePoints[1].x) + lineSize * xRobot,
                            lineSize - trianglePoints[1].y + lineSize * yRobot);
                    triangle.addPoint(lineSize / 2 + lineSize * xRobot, trianglePoints[2].x + lineSize * yRobot);
                    break;
                default:
            }
            g2.setColor(Color.GREEN);
            g2.fillPolygon(triangle);

            pinta.drawImage(bima, 0, 0, null);
        }
    }
}
