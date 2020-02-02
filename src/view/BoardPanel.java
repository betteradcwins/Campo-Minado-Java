package view;

import models.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {
        setLayout(new GridLayout(board.getRows(), board.getColumns()));

        board.forEachSquare(s -> add(new SquareButton(s)));
        board.addObserver(e -> SwingUtilities.invokeLater(() -> {
            if (e) {
                JOptionPane.showMessageDialog(this,"Ganhou :)");
            } else {
                JOptionPane.showMessageDialog(this, "Perdeu :C");
            }

            board.restart();
        }));
    }
}
