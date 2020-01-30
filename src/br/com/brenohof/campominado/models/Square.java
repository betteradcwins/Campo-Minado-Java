package br.com.brenohof.campominado.models;

import br.com.brenohof.campominado.exceptions.ExplosionRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private final int row;
    private final int column;

    private boolean opened = false;
    private boolean mined = false;
    private boolean tagged = false;

    List<Square> neighborhood = new ArrayList<>();

    Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    boolean addNeighbor(Square neighbor) {
        boolean differentRow = row != neighbor.row;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentColumn && differentRow;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int finalDelta = deltaColumn + deltaRow;

        if (finalDelta == 1 || (diagonal && finalDelta == 2)) {
            neighborhood.add(neighbor);
            return true;
        }

        return false;
    }

    private boolean isNeighborhoodSafe() {
        return neighborhood.stream().noneMatch(n -> n.mined);
    }

    @Override
    public String toString() {
        if (tagged) {
            return "x";
        } else if (!opened) {
            return "?";
        } else if (mined) {
            return "*";
        } else if (countMinesAround() > 0) {
            return Long.toString(countMinesAround());
        } else {
            return " ";
        }
    }

    boolean open() {
        if (!opened && !tagged) {
            opened = true;

            if (mined) {
                throw new ExplosionRuntimeException();
            }

            if(isNeighborhoodSafe()) {
                neighborhood.forEach(Square::open);
            }
            return true;
        }
        return false;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    void switchTag() {
        if (!opened)
            tagged = !tagged;
    }

    boolean wasObjectiveAchieved() {
        boolean protect = mined && tagged;
        boolean uncovered = opened && !mined;
        return protect || uncovered;
    }

    long countMinesAround() {
        return neighborhood.stream().filter(n -> n.mined).count();
    }

    void restart() {
        this.mined = false;
        this.opened = false;
        this.tagged = false;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined(boolean mined) {
        this.mined = mined;
    }
}
