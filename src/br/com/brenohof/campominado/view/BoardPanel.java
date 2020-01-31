package br.com.brenohof.campominado.view;

import br.com.brenohof.campominado.models.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {
        setLayout(new GridLayout(board.getRows(), board.getColumns()));

        board.forEachSquare(s -> add(new SquareButton(s)));
    }
}
