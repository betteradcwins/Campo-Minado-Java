package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;


@Data
public class Square {
    private final int row;
    private final int column;

    private boolean opened = false;
    private boolean mined = false;
    private boolean tagged = false;

    private List<Square> neighborhood = new ArrayList<>();
    private Set<BiConsumer<Square, SquareEvent>> observers = new HashSet<>();

    Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void addObserver(BiConsumer<Square, SquareEvent> observer) {
        observers.add(observer);
    }

    public void notifyObservers(SquareEvent event) {
        observers.forEach(o -> o.accept(this, event));
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

    public boolean isNeighborhoodSafe() {
        return neighborhood.stream().noneMatch(n -> n.mined);
    }

    public boolean open() {
        if (!opened && !tagged) {
            if (mined) {
                notifyObservers(SquareEvent.EXPLODE);
                return true;
            }

            setOpened(true);

            if(isNeighborhoodSafe()) {
                neighborhood.forEach(Square::open);
            }
            return true;
        }
        return false;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
        if (opened)
            notifyObservers(SquareEvent.OPEN);
    }

    public void switchTag() {
        if (!opened){
            tagged = !tagged;

            if (tagged) {
                notifyObservers(SquareEvent.MARK);
            } else {
                notifyObservers(SquareEvent.MARK_OFF);
            }
        }
    }

    boolean wasObjectiveAchieved() {
        boolean protect = mined && tagged;
        boolean uncovered = opened && !mined;
        return protect || uncovered;
    }

    public long countMinesAround() {
        return neighborhood.stream().filter(n -> n.mined).count();
    }

    void restart() {
        this.mined = false;
        this.opened = false;
        this.tagged = false;
        notifyObservers(SquareEvent.RESTART);
    }
}
