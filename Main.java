package HanoiReactivo;

import HanoiReactivo.Hanoi.Hanoi;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setLayout(new GridLayout(2, 3));

        for (int i = 0; i < 6; i++) {
            add(new Hanoi());
        }

        setSize(1800, 1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main();
    }
}