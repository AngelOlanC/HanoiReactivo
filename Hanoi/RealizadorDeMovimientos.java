public class RealizadorDeMovimientos {
    private Hanoi hanoi;
    private Movimiento movimiento;
    private Disco discoEnMovimiento;
    private Integer estadoActual, dp = 1, porcentajeCompletadoMovimiento = 0, d, x_original, y_original;
    private MetodosDiscos md;

    public RealizadorDeMovimientos(Hanoi vista, MetodosDiscos md) {
        this.hanoi = vista;
        this.md = md;
    }

    public void preparar(Movimiento movimiento) {
        this.movimiento = movimiento;
        discoEnMovimiento = hanoi.getDiscos()[hanoi.getPilas()[movimiento.paloOrigen()].peek()];
        estadoActual = ConstantesEstados.SUBIENDO;
    }

    public boolean movimientoFinalizado() {
        return estadoActual == null || estadoActual == ConstantesEstados.COMPLETADO;
    }

    public void realizarMovimiento() {
        if (estadoActual == ConstantesEstados.SUBIENDO && porcentajeCompletadoMovimiento == 0)
            asignarD();

        porcentajeCompletadoMovimiento = Math.min(100, porcentajeCompletadoMovimiento + dp);

        asignarCoordenadasDiscoEnMovimiento();

        boolean faseDelMovimientoCompletada = porcentajeCompletadoMovimiento == 100;
        if (estadoActual == ConstantesEstados.SUBIENDO && faseDelMovimientoCompletada) {
            estadoActual = ConstantesEstados.DESPLAZANDOSE_HORIZONTALMENTE;
            porcentajeCompletadoMovimiento = 0;
            asignarD();
        }
        else if (estadoActual == ConstantesEstados.DESPLAZANDOSE_HORIZONTALMENTE && faseDelMovimientoCompletada) {
            estadoActual = ConstantesEstados.BAJANDO;
            porcentajeCompletadoMovimiento = 0;
            asignarD();
        }
        else if (estadoActual == ConstantesEstados.BAJANDO && faseDelMovimientoCompletada) {
            estadoActual = ConstantesEstados.COMPLETADO;
            porcentajeCompletadoMovimiento = 0;
            hanoi.getPilas()[movimiento.paloDestino()].push(hanoi.getPilas()[movimiento.paloOrigen()].pop());
            hanoi.aumentarNumeroMovimientosRealizados();
            discoEnMovimiento = null;
            asignarD();
        }
        hanoi.repaint();
    }

    public void asignarCoordenadasDiscoEnMovimiento() {
        if (estadoActual == ConstantesEstados.SUBIENDO) {
            discoEnMovimiento.setY(y_original - d * porcentajeCompletadoMovimiento / 100);
            return;
        }
        if (estadoActual == ConstantesEstados.DESPLAZANDOSE_HORIZONTALMENTE) {
            discoEnMovimiento.setY(md.getAlturaAdecuadaSubiendo());
            discoEnMovimiento.setX(x_original - d * porcentajeCompletadoMovimiento / 100);
            return;
        }
        discoEnMovimiento.setY(md.getAlturaAdecuadaSubiendo() + d * porcentajeCompletadoMovimiento / 100);
    }

    public void calcularCoordenadasOriginales() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < hanoi.getPilas()[i].size(); j++) {
                if (!hanoi.getPilas()[i].elementAt(j).equals(discoEnMovimiento.getIndice()))
                    continue;
                x_original = md.getXCentroPalo(i + 1) - discoEnMovimiento.getAnchura() / 2;
                y_original = md.getSiguienteYPalo(j);
                break;
            }
        }

    }

    public void asignarD() {
        if (discoEnMovimiento == null)
            return;

        calcularCoordenadasOriginales();
        if (estadoActual == ConstantesEstados.SUBIENDO) {
            discoEnMovimiento.setX(x_original);
            d = y_original - md.getAlturaAdecuadaSubiendo();
        }
        else if (estadoActual == ConstantesEstados.DESPLAZANDOSE_HORIZONTALMENTE) {
            discoEnMovimiento.setY(md.getAlturaAdecuadaSubiendo());
            d = x_original - md.getDesplazamientoAdecuado(movimiento, discoEnMovimiento);
        }
        else if (estadoActual == ConstantesEstados.BAJANDO) {
            discoEnMovimiento.setX(md.getDesplazamientoAdecuado(movimiento, discoEnMovimiento));
            d = md.getAlturaAdecuadaBajando(movimiento) - md.getAlturaAdecuadaSubiendo();
        }
    }

    public void setDp(int D) {
        this.dp = D;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public Disco getDiscoEnMovimiento() {
        return discoEnMovimiento;
    }
}