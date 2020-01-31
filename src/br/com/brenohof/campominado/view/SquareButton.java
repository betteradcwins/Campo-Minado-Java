package br.com.brenohof.campominado.view;

import br.com.brenohof.campominado.models.Square;
import br.com.brenohof.campominado.models.SquareEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

public class SquareButton extends JButton implements BiConsumer<Square, SquareEvent>, MouseListener {

    private static final Color BG_DEFAULT = new Color(144, 144, 144);
    private static final Color BG_MARKED = new Color(40, 179, 255);
    private static final Color BG_EXPLODE = new Color(200, 68, 68);

    private Square square;

    public SquareButton(Square square) {
        this.square = square;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        square.addObserver(this);
    }

    @Override
    public void accept(Square square, SquareEvent event) {
        switch (event) {
            case OPEN:
                applyOpenStyle();
                break;
            case MARK:
                applyMarkStyle();
                break;
            case EXPLODE:
                applyExplodeStyle();
                break;
            default:
                applyDefaultStyle();
                break;
        }
    }

    private void applyDefaultStyle() {
        // !TODO
    }

    private void applyExplodeStyle() {
        // !TODO
    }

    private void applyMarkStyle() {
        // !TODO
    }

    private void applyOpenStyle() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch ((int) square.countMinesAround()) {
            case 1:
                setForeground(Color.GREEN);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.RED);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.MAGENTA);
                break;
            default:
                setForeground(Color.PINK);
                break;
        }

        String value = !square.isNeighborhoodSafe() ? square.countMinesAround() + "" : "";
        setText(value);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {
            square.open();
        } else {
            square.switchTag();
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) { }
    public void mouseReleased(MouseEvent mouseEvent) { }
    public void mouseEntered(MouseEvent mouseEvent) { }
    public void mouseExited(MouseEvent mouseEvent) { }
}
