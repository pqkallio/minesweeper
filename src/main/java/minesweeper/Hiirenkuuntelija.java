package minesweeper;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Hiirenkuuntelija implements MouseListener {
    private Nappi nappi;
    private Kayttoliittyma kayttis;
    
    public Hiirenkuuntelija(Nappi nappi, Kayttoliittyma kayttis) {
        this.nappi = nappi;
        this.kayttis = kayttis;
    }

    @Override
    public void mouseClicked(MouseEvent ae) {
        if (ae.getButton() == MouseEvent.BUTTON1) {
            this.nappi.avaa();
            if (this.nappi.getRuutu().onMiinoitettu()) {
                this.kayttis.getPeli().tarkastaKentta(false);
            } else {
                this.kayttis.getPeli().tarkastaKentta(true);
            }
        } else if (ae.getButton() == MouseEvent.BUTTON3) {
            this.nappi.liputa();
            System.out.println("liputin");
        }
    }

    @Override
    public void mousePressed(MouseEvent ae) {
    }

    @Override
    public void mouseReleased(MouseEvent ae) {
    }

    @Override
    public void mouseEntered(MouseEvent ae) {
        this.nappi.korosta();
    }

    @Override
    public void mouseExited(MouseEvent ae) {
        this.nappi.poistaKorostus();
    }
    
}
