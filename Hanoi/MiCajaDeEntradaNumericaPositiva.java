import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MiCajaDeEntradaNumericaPositiva extends JTextArea implements KeyListener {
    public MiCajaDeEntradaNumericaPositiva() {
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char caracterPresionado = e.getKeyChar();
        if (!Character.isDigit(caracterPresionado) || (getText().isEmpty() && caracterPresionado == '0'))
            e.consume();
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
