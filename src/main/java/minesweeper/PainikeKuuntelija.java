package minesweeper;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PainikeKuuntelija implements ActionListener {
    private Peli peli;
    private int x;
    private int y;
    private Ruutu ruutu;
    
    public PainikeKuuntelija(Peli peli, int x, int y) {
        this.peli = peli;
        this.x = x;
        this.y = y;
        
        for (Ruutu pelinRuutu : this.peli.getRuudut()) {
            if (pelinRuutu.getX() == this.x && pelinRuutu.getY() == this.y) {
                this.ruutu = pelinRuutu;
            }
        }
        
        if (this.ruutu == null) {
            throw new IllegalArgumentException("Pelistä ei löytynyt ruutua "
                    + "koordinaateilla " + this.x + " " + this.y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.ruutu.onMiinoitettu()) {
            this.peli.getKayttoliittyma().lopetaPeli();
        } else {
            
        }
    }
    
}
