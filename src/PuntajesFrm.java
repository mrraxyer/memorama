import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PuntajesFrm extends JFrame {

    public PuntajesFrm(List<Resultado> resultados) {
        super("Historial de Partidas");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Resultado r : resultados) {
            listModel.addElement(r.toString());
        }

        JList<String> lista = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(lista);

        add(scroll, BorderLayout.CENTER);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
