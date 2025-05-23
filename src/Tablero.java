import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Tablero extends JPanel {
    // array con los nombres de las imagenes, 8 en total para 16 pares
    private String[] band = {"chip-black", "chip-red", "chip-green", "poker", "spades", "hearts", "diamonds", "clubs"};

    private int fila = 4;
    private int col = 4;
    private int ancho_casilla = 142;

    private JuegoFrm juegoFrm;
    private int intentos = 0;

    public boolean play = false;

    //Cambiar el valor de intentosMax y tiempoMax para aumentar o disminuir la dificultad
    private int intentosMax = 10;
    private int tiempoMax = 45;

    int c = 0;
    Casilla c1;
    Casilla c2;
    int aciertos = 0;

    public int getIntentosMax() {
        return intentosMax;
    }

    public int getTiempoMax() {
        return tiempoMax;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    /** Constructor de clase */
    public Tablero(JuegoFrm frame) {
        super();
        this.juegoFrm = frame;
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new java.awt.GridLayout(fila, col));
        Dimension d = new Dimension(ancho_casilla * col, ancho_casilla * fila);
        setSize(d);
        setPreferredSize(d);

        int count = 0;
        for (int i = 1; i <= (fila * col); i++) {
            Casilla p = new Casilla(String.valueOf(i));
            p.setBandera(band[count]);
            count++;
            count = (count >= band.length) ? 0 : count++;
            p.showBandera();
            p.addMouseListener(new juegoMouseListener());
            this.add(p);
        }
        setVisible(true);
    }

    public void reiniciarIntentos() {
        intentos = 0;
    }

    /**
     * Inicia juego: llena las casillas con pares de imagenes
     */
    public void comenzarJuego() {
        JOptionPane.showMessageDialog(null, "Juego iniciado, tiene "
                + intentosMax + " intentos y "
                + tiempoMax + " segundos para ganar");
        aciertos = 0;
        play = true;
        Component[] componentes = this.getComponents();

        for (int i = 0; i < componentes.length; i++) {
            ((Casilla) componentes[i]).congelarImagen(false);
            ((Casilla) componentes[i]).ocultarBandera();
            ((Casilla) componentes[i]).setBandera("");
        }

        for (int i = 0; i < componentes.length; i++) {
            int n = (int) (Math.random() * (band.length));
            if (!existe(band[n])) {
                ((Casilla) componentes[i]).setBandera(band[n]);
            } else {
                i--;
            }
        }
    }

    /**
     * Comprueba si una imagen ya ha sido asignada dos veces
     */
    private boolean existe(String bandera) {
        int count = 0;
        Component[] componentes = this.getComponents();
        for (int i = 0; i < componentes.length; i++) {
            if (componentes[i] instanceof Casilla) {
                if (((Casilla) componentes[i]).getNameBandera().equals(bandera)) {
                    count++;
                }
            }
        }
        return (count == 2);
    }

    /**
     * MouseListener para manejar los eventos del juego
     */
    class juegoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (play) {
                c++;
                if (c == 1) {
                    c1 = (Casilla) e.getSource();
                    if (!c1.isCongelado()) {
                        c1.showBandera();
                        System.out.println("Primera Imagen: " + c1.getNameBandera());
                    } else {
                        c = 0;
                    }
                } else if (c == 2 && !c1.getName().equals(((Casilla) e.getSource()).getName())) {
                    c2 = (Casilla) e.getSource();
                    if (!c2.isCongelado()) {
                        c2.showBandera();
                        System.out.println("Segunda Imagen: " + c2.getNameBandera());
                        Animacion ani = new Animacion(c1, c2);
                        ani.execute();
                    }
                    c = 0;
                } else {
                    c = 0;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Comience el juego primero");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /**
     * Clase para animar y comprobar aciertos
     */
    class Animacion extends SwingWorker<Void, Void> {
        private Casilla casilla1;
        private Casilla casilla2;

        public Animacion(Casilla value1, Casilla value2) {
            this.casilla1 = value1;
            this.casilla2 = value2;
        }

        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("doInBackground: procesando imagenes...");
            Thread.sleep(1000);
            if (casilla1.getNameBandera().equals(casilla2.getNameBandera())) {
                casilla1.congelarImagen(true);
                casilla2.congelarImagen(true);
                System.out.println("doInBackground: imagenes son iguales");
                aciertos++;
                if (aciertos == (fila * col)/2) {
                    System.out.println("doInBackground: Usted es un ganador!");
                    JOptionPane.showMessageDialog(null, "Usted es un ganador!");
                    juegoFrm.timer.stop();
                }
            } else {
                casilla1.ocultarBandera();
                casilla2.ocultarBandera();
                System.out.println("doInBackground: imagenes no son iguales");
                intentos++;
                juegoFrm.actualizarIntentos(intentos);
            }
            return null;
        }
    }
}
