import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Controlador implements ActionListener, ChangeListener, ComponentListener {
    private SimuladorJuego simuladorJuego;
    private Hanoi hanoi;

    public Controlador(Hanoi vista) {
        simuladorJuego = new SimuladorJuego(this);
        this.hanoi = vista;
        vista.addComponentListener(this);
    }

    public Hanoi getHanoi() {
        return hanoi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hanoi.getBotonEnviar()) {
            int n = Integer.parseInt(hanoi.getAreaDeTextoDeEntrada().getText());

            if (n < 3 || n > 13) {
                JOptionPane.showMessageDialog(hanoi, "Numero de discos no valido, intentelo de nuevo con un numero entre 3 y 13", "Error de lectura", JOptionPane.ERROR_MESSAGE);
                hanoi.getAreaDeTextoDeEntrada().setText("");
                return;
            }

            hanoi.establecerValoresInicioJuego();
            simuladorJuego.simular(n);
            asignarValoresVista();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        simuladorJuego.actualizarVelocidadAnimacion();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        hanoi.repaint();

        boolean noSeHaInicializadoLaInterfaz = hanoi.getSliderVelocidad() == null;
        if (noSeHaInicializadoLaInterfaz) {
            hanoi.inicializarInterfaz();
            hanoi.getBotonEnviar().addActionListener(this);
            hanoi.getSliderVelocidad().addChangeListener(this);
            hanoi.addComponentListener(this);
            return;
        }
        hanoi.mostrarPanelSuperior();

        asignarValoresVista();
    }

    public void asignarValoresVista() {
        hanoi.calcularDimensionesPalo();
        Disco.setAnchuraDefault(hanoi.getWidth() / 17);
        Disco.setAlturaDefault(hanoi.getHeight() * 30 / 961);
        if (hanoi.getPilas() == null || hanoi.getDiscos() == null)
            return;
        simuladorJuego.getMd().asignarPosicionDiscos(hanoi.getPilas(), hanoi.getDiscos(), simuladorJuego.getRealizadorMovimientos().getDiscoEnMovimiento());

        if (simuladorJuego.getRealizadorMovimientos() == null || simuladorJuego.getRealizadorMovimientos().getMovimiento() == null)
            return;

        simuladorJuego.getRealizadorMovimientos().asignarD();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}
