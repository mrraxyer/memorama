import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JuegoFrm extends JFrame {

    private JLabel lblIntentos;
    private JLabel lblTiempo;
    private JLabel lblScore;
    static Timer timer;
    private int segundos = 0;
    private int score = 0;
    private Tablero tablero;

    private List<Resultado> historial = new ArrayList<>();

    public JuegoFrm() {
        super("Memorama - Juego de Baraja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        tablero = new Tablero(this);

        JButton btnComenzar = new JButton("Comenzar Juego");
        btnComenzar.addActionListener(e -> iniciarJuego());

        JButton btnVerPuntajes = new JButton("Ver Puntajes");
        btnVerPuntajes.addActionListener(e -> new PuntajesFrm(historial));

        lblIntentos = new JLabel("Intentos: 0");
        lblTiempo = new JLabel("Tiempo: 0 s");
        lblScore = new JLabel("Puntaje: 0");

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelInferior.add(btnComenzar);
        panelInferior.add(btnVerPuntajes);
        panelInferior.add(lblIntentos);
        panelInferior.add(lblTiempo);
        panelInferior.add(lblScore);

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
                    historial.add(new Resultado("Perdió", segundos, tablero.getIntentos()));
                    JOptionPane.showMessageDialog(JuegoFrm.this, "¡Has perdido! Has alcanzado el tiempo máximo.");
                    tablero.setPlay(false);
                }
            }
        });
    }

    private void iniciarJuego() {
        segundos = 0;
        lblTiempo.setText("Tiempo: 0 s");
        reiniciarScore();
        tablero.reiniciarIntentos();
        lblIntentos.setText("Intentos: " + tablero.getIntentos());
        tablero.comenzarJuego();
        timer.restart();
    }

    public void actualizarIntentos(int intentos) {
        lblIntentos.setText("Intentos: " + intentos);
        if (intentos >= tablero.getIntentosMax()) {
            timer.stop();
            historial.add(new Resultado("Perdió", segundos, intentos));
            JOptionPane.showMessageDialog(this, "¡Has perdido! Has alcanzado el número máximo de intentos.");
            tablero.setPlay(false);
        }
    }

    public void actualizarScore(int puntos) {
        score += puntos;
        lblScore.setText("Puntaje: " + score);
    }

    public void reiniciarScore() {
        score = 0;
        lblScore.setText("Puntaje: 0");
    }

    public int getSegundos() {
        return segundos;
    }

    public void registrarGanador(int tiempo, int intentos) {
        historial.add(new Resultado("Ganó", tiempo, intentos));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoFrm());
    }
}
