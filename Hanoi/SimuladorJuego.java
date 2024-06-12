import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SimuladorJuego implements ActionListener {
    private RealizadorDeMovimientos realizadorMovimientos;
    private LinkedList<Movimiento> movimientos;
    private Controlador controlador;
    private Timer t;
    private MetodosDiscos md;

    private boolean enJuego = false;

    public SimuladorJuego(Controlador controlador) {
        this.controlador = controlador;
        movimientos = new LinkedList<>();
    }

    public void simular(int n) {
        md = new MetodosDiscos();
        md.inicializarDiscos(n, controlador.getHanoi());
        controlador.getHanoi().repaint();
        precalcularMovimientos(n, 0, 1, 2);
        
        realizadorMovimientos = new RealizadorDeMovimientos(controlador.getHanoi(), md);
        enJuego = true;
        t = new Timer(1, this);
        t.setRepeats(true);
        actualizarVelocidadAnimacion();
        t.start();
    }

    public void actualizarVelocidadAnimacion() {
        realizadorMovimientos.setDp(controlador.getHanoi().getSliderVelocidad().getValue());
    }

    private void precalcularMovimientos(int n, int start, int aux, int end) {
        if (n == 1)
            movimientos.add(new Movimiento(start, end));
        if (n <= 1)
            return;
        precalcularMovimientos(n - 1, start, end, aux);
        precalcularMovimientos(1, start, aux, end);
        precalcularMovimientos(n - 1, aux, start, end);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (realizadorMovimientos.movimientoFinalizado() && !movimientos.isEmpty()) {
            realizadorMovimientos.preparar(movimientos.poll());
            return;
        }

        if (realizadorMovimientos.movimientoFinalizado() && movimientos.isEmpty()) {
            t.stop();
            enJuego = false;
            controlador.getHanoi().establecerValoresFinJuego();
            return;
        }

        realizadorMovimientos.realizarMovimiento();
    }

    public RealizadorDeMovimientos getRealizadorMovimientos() {
        return realizadorMovimientos;
    }

    public MetodosDiscos getMd() {
        return md;
    }
}