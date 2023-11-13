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

public class Interficie extends JFrame {
    private Main main;
    private final static int mapDimension = 600;
    private int lineSize;
    private panellMapa panellPintar;
    private Integer torn;
    private BufferedImage tresor;
    private BufferedImage monstre;

    public Interficie(Main main) {
        super("Robot");
        this.main = main;
        torn = null;
        try {
            // Load the image from a file (change the file path accordingly)
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
        panellPintar = new panellMapa();
        this.add(panellPintar, BorderLayout.CENTER);
        this.add(new panellBotons(main), BorderLayout.WEST);
        this.pack();
    }

    public void mostrar() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void repintar() {
        panellPintar.repaint();
    }

    private class panellBotons extends JPanel implements ActionListener {

        JButton tempsAccio;
        JButton llargMapa;
        JButton activarRobot;
        JButton posarPrecipici;
        JButton posarMonstruo;
        JButton posarTresor;

        private Main main;

        public panellBotons(Main main) {
            this.main = main;
            setLayout(new GridLayout(6, 1));

            posarPrecipici = new JButton("Precipici");
            posarMonstruo = new JButton("Monstre");
            posarTresor = new JButton("Tresor"); // Pensar a veure si s'ha de posar un botó per el robot.

            tempsAccio = new JButton("Canviar Temps d'Acció");
            llargMapa = new JButton("Canviar LLargària Mapa");
            activarRobot = new JButton("ATURAT");

            tempsAccio.addActionListener(this);
            llargMapa.addActionListener(this);
            activarRobot.addActionListener(this);
            posarMonstruo.addActionListener(this);
            posarPrecipici.addActionListener(this);
            posarTresor.addActionListener(this);

            this.add(activarRobot);
            this.add(tempsAccio);
            this.add(llargMapa);
            this.add(posarPrecipici);
            this.add(posarMonstruo);
            this.add(posarTresor);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == activarRobot) {
                JButton boto = (JButton) arg0.getSource();
                main.notificar("Començar");
                if (boto.getText().equals("ATURAT")) {
                    boto.setText("ACTIVAT");
                } else {
                    boto.setText("ATURAT");
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
                main.notificar("Canviardelay");
            } else if (arg0.getSource() == llargMapa) {
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
            } else if (arg0.getSource() == posarPrecipici) {
                torn = 1;
            } else if (arg0.getSource() == posarMonstruo) {
                torn = 2;
            } else if (arg0.getSource() == posarTresor) {
                torn = 3;
            }
        }
    }

    private class panellMapa extends JPanel implements MouseListener {
        private BufferedImage bima;

        public panellMapa() {
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

                System.out.println("casX: "+casX+" casY:" +casY);
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
                            g2.drawString("Brisa", (lineSize * i)+5, (lineSize * j)+20 + desplx);
                            desplx += (lineSize / 4);
                        }

                        if (casella.isHedor()) {
                            g2.drawString("Hedor", (lineSize * i)+5, (lineSize * j)+20 + desplx);
                        }
                        if(casella.isRobot()){
                            g2.setColor(Color.RED); // pinta bolla
                            int radio = lineSize / 4;
                            g2.fillOval(((lineSize * i)+ lineSize / 2) - radio, ((lineSize * j)+ lineSize / 2) - radio, radio * 2, radio * 2);

                        }

                    }
                }
            }

            pinta.drawImage(bima, 0, 0, null);
        }
    }
}
