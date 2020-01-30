package br.com.brenohof.campominado.view;

import br.com.brenohof.campominado.models.Board;
import com.sun.tools.javac.Main;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        Board board = new Board(14, 18, 40);

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
