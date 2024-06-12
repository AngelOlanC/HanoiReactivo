import java.util.Stack;

public class MetodosDiscos {
    private Hanoi hanoi;

    protected void inicializarDiscos(int n, Hanoi hanoi) {
        this.hanoi = hanoi;
        Stack<Integer>[] pilas = new Stack[3];
        for (int i = 0; i < 3; i++)
            pilas[i] = new Stack<>();
        for (int i = n - 1; i >= 0; i--)
            pilas[0].add(i);
        Disco[] discos = new Disco[n];
        for (int i = 0; i < n; i++) {
            discos[i] = new Disco(i);
        }
        asignarPosicionDiscos(pilas, discos, null);
        hanoi.setPilas(pilas);
        hanoi.setDiscs(discos);
    }


    protected void asignarPosicionDiscos(Stack<Integer>[] pilas, Disco discos[], Disco discoEnMovimiento) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < pilas[i].size(); j++) {
                if (discoEnMovimiento != null && discoEnMovimiento.getIndice() == pilas[i].elementAt(j))
                    continue;
                discos[pilas[i].elementAt(j)].setX(getXCentroPalo(i + 1) - discos[pilas[i].elementAt(j)].getAnchura() / 2);
                discos[pilas[i].elementAt(j)].setY(getSiguienteYPalo(j));
            }
        }
    }

    protected int getXCentroPalo(int numeroDePalo) {
        return hanoi.getWidth() * numeroDePalo / 4;
    }

    protected int getSiguienteYPalo(int discosEnPalo) {
        return hanoi.getHeight() * 3 / 4 - Disco.getAltura() * (discosEnPalo + 1);
    }

    protected int getAlturaAdecuadaSubiendo() {
        return hanoi.getHeight() / 4 - Disco.getAltura();
    }

    protected int getDesplazamientoAdecuado(Movimiento movimiento, Disco discoEnMovimiento) {
        return getXCentroPalo(movimiento.paloDestino() + 1) - discoEnMovimiento.getAnchura() / 2;
    }

    protected int getAlturaAdecuadaBajando(Movimiento movimiento) {
        return getSiguienteYPalo(hanoi.getPilas()[movimiento.paloDestino()].size());
    }
}
