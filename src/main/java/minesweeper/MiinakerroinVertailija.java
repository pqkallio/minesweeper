package minesweeper;


import java.util.Comparator;


public class MiinakerroinVertailija implements Comparator<Ruutu> {

    @Override
    public int compare(Ruutu ruutu1, Ruutu ruutu2) {
        if (ruutu1.getKerroin() > ruutu2.getKerroin()) {
            return -1;
        } else if (ruutu1.getKerroin() > ruutu2.getKerroin()) {
            return 1;
        } else {
            return 0;
        }
    }
}
