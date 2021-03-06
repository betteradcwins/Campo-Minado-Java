package view;

import models.Board;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        Board board = new Board(14, 18, 40);

        add(new BoardPanel(board));
        setTitle("Campo Minado");
        setSize(630, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

}
