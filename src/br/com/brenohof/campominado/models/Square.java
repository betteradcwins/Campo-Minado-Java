package br.com.brenohof.campominado.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
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

    boolean open() {
        if (!opened && !tagged) {
            opened = true;

            if (mined) {
                // TODO to implement the logic.
            }

            if(isNeighborhoodSafe()) {
                neighborhood.forEach(Square::open);
            }
            return true;
        }
        return false;
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
}
