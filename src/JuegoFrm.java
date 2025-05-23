import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegoFrm extends JFrame {

    private JLabel lblIntentos;
    private JLabel lblTiempo;
    static Timer timer;
    private int segundos = 0;
    private Tablero tablero;

    public JuegoFrm() {
        super("Memorama - Juego de Baraja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        tablero = new Tablero(this);

        JButton btnComenzar = new JButton("Comenzar Juego");
        btnComenzar.addActionListener(e -> iniciarJuego());

        lblIntentos = new JLabel("Intentos: 0");
        lblTiempo = new JLabel("Tiempo: 0 s");

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.add(btnComenzar);
        panelInferior.add(lblIntentos);
        panelInferior.add(lblTiempo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(tablero, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                segundos++;
                lblTiempo.setText("Tiempo: " + segundos + " s");
                if (segundos >= tablero.getTiempoMax()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(JuegoFrm.this, "¡Has perdido! Has alcanzado el tiempo máximo.");
                    tablero.setPlay(false);
                }
            }
        });
    }

    private void iniciarJuego() {
        segundos = 0;
        lblTiempo.setText("Tiempo: 0 s");
        lblIntentos.setText("Intentos: " + tablero.getIntentos());
        tablero.reiniciarIntentos();
        lblIntentos.setText("Intentos: " + tablero.getIntentos());
        tablero.comenzarJuego();
        timer.restart();
    }

    public void actualizarIntentos(int intentos) {
        lblIntentos.setText("Intentos: " + intentos);
        if (intentos >= tablero.getIntentosMax()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "¡Has perdido! Has alcanzado el número máximo de intentos.");
            tablero.setPlay(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoFrm());
    }
}
