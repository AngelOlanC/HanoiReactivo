import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Hanoi extends JPanel {
    private Stack<Integer>[] pilas;
    private Disco discos[];
    private JSlider sliderVelocidad;
    private MiCajaDeEntradaNumericaPositiva areaDeTextoDeEntrada;
    private JButton botonEnviar;
    private JLabel labelNumeroMovimientosRealizadosTexto, fondoParteSuperior, labelSlider, labelAreaDeTextoDeEntrada;
    private Integer ANCHURA_PALO, ALTURA_PALO, numeroMovimientosRealizados = 0;
    private String colores[] = {"#FFC28B", "#174D7C", "#FF5342", "#AF7AC5", "#FBE7C6", "#795548", "#FE4365", "#83AF9B",
    "#003B46", "#E99787", "#DCEDC2", "#594F4F", "#FFAEBC"};
    private Graphics gi;
    private Image img;

    public Hanoi() {
        new Controlador(this);
        setBorder(new LineBorder(Color.black));
    }

    void inicializarInterfaz() {
        setLayout(null);

        calcularDimensionesPalo();

        img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
        gi = img.getGraphics();

        add(fondoParteSuperior = new JLabel());
        fondoParteSuperior.setOpaque(true);
        fondoParteSuperior.setBackground(Color.decode("#92c37e"));

        add(sliderVelocidad = new JSlider(1, 100, 1));
        sliderVelocidad.setEnabled(false);

        add(labelSlider = new JLabel("Rapidez de la animacion", JLabel.CENTER));

        add(areaDeTextoDeEntrada = new MiCajaDeEntradaNumericaPositiva());

        add(labelAreaDeTextoDeEntrada = new JLabel("Ingrese n"));

        add(botonEnviar = new JButton());

        add(labelNumeroMovimientosRealizadosTexto = new JLabel());

        mostrarPanelSuperior();
        //add(panelSuperior, BorderLayout.NORTH);
    }

    public void mostrarPanelSuperior() {
        fondoParteSuperior.setBounds(0, getHeight() / 6 - 5, getWidth(), 5);

        sliderVelocidad.setBounds(getWidth() * 10 / 1784, getHeight() / 14, getWidth() / 4, getHeight() / 12 - 10);
        sliderVelocidad.setVisible(true);
        sliderVelocidad.setOpaque(false);

        labelSlider.setBounds(getWidth() * 10 / 1784, getHeight() / 40, getWidth() / 4, getHeight() / 20);

        areaDeTextoDeEntrada.setBounds(getWidth() * 2 / 5, getHeight() / 13, getWidth() / 6, getHeight() / 25);

        labelAreaDeTextoDeEntrada.setBounds(getWidth() * 151 / 320, getHeight() / 42, getWidth() / 4, getHeight() / 20);

        botonEnviar.setBounds(getWidth() * 7 / 12, getHeight() / 12, getWidth() / 30, getHeight() / 40);

        labelNumeroMovimientosRealizadosTexto.setBounds(getWidth() * 3 / 4, getHeight() / 42, getWidth() / 4, getHeight() / 20);
    }

    public void calcularDimensionesPalo() {
        ANCHURA_PALO = getWidth() * 15 / 1784;
        ALTURA_PALO = getHeight() / 2;
    }

    public void dibujaSobreImagen() {
        if (ALTURA_PALO == null || pilas == null)
            return;

        gi.setColor(Color.BLACK);
        for (int i = 1; i <= 3; i++)
            gi.fillRect(getWidth() / 4 * i - ANCHURA_PALO / 2, getHeight() / 4, ANCHURA_PALO, ALTURA_PALO);
        gi.fillRect(getWidth() / 8, getHeight() * 3 / 4, getWidth() * 3 / 4, ANCHURA_PALO);

        for (Disco disco : discos) {
            gi.setColor(Color.decode(colores[disco.getIndice()]));
            gi.fillRoundRect(disco.getX(), disco.getY(), disco.getAnchura(), Disco.getAltura(), 25, 25);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (g == null || gi == null)
            return;
        img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
        gi = img.getGraphics();
        super.paint(gi);
        dibujaSobreImagen();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

    public void establecerValoresInicioJuego() {
        labelNumeroMovimientosRealizadosTexto.setText("# Movimientos: 0");
        numeroMovimientosRealizados = 0;
        getAreaDeTextoDeEntrada().setText("");
        botonEnviar.setEnabled(false);
        areaDeTextoDeEntrada.setEnabled(false);
        sliderVelocidad.setEnabled(true);
    }

    public void establecerValoresFinJuego() {
        botonEnviar.setEnabled(true);
        areaDeTextoDeEntrada.setEnabled(true);
        sliderVelocidad.setEnabled(false);
    }

    public void aumentarNumeroMovimientosRealizados() {
        labelNumeroMovimientosRealizadosTexto.setText("# Movimientos: " + (++numeroMovimientosRealizados));
    }

    public void setPilas(Stack<Integer> pilas[]) {
        this.pilas = pilas;
    }

    public void setDiscs(Disco[] discos) {
        this.discos = discos;
    }

    public JTextArea getAreaDeTextoDeEntrada() {
        return areaDeTextoDeEntrada;
    }

    public JButton getBotonEnviar() {
        return botonEnviar;
    }

    public Disco[] getDiscos() {
        return discos;
    }

    public Stack<Integer>[] getPilas() {
        return pilas;
    }

    public JSlider getSliderVelocidad() {
        return sliderVelocidad;
    }

    public int getNumeroMovimientosRealizados() {
        return Integer.parseInt(labelNumeroMovimientosRealizadosTexto.getText());
    }
}