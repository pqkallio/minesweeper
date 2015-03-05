package minesweeper;


import java.util.List;


public class Ruutu implements Comparable<Ruutu> {
    private Peli peli;
    private int x;
    private int y;
    private Nappi nappi;
    private boolean miinoitettu;
    private boolean avattu;
    private boolean liputettu;
    private double kerroin;
    private int viereisetMiinat;
    
    public Ruutu(Peli peli, int x, int y) {
        this.peli = peli;
        this.x = x;
        this.y = y;
        this.miinoitettu = false;
        this.avattu = false;
        this.liputettu = false;
        this.viereisetMiinat = 0;
    }
    
    public void miinoita() {
        this.miinoitettu = true;
    }
    
    public void setKerroin(double kerroin) {
        this.kerroin = kerroin;
    }

    public double getKerroin() {
        return this.kerroin;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setNappi(Nappi nappi) {
        this.nappi = nappi;
    }

    public Nappi getNappi() {
        return nappi;
    }
    
    public boolean onMiinoitettu() {
        return this.miinoitettu;
    }

    @Override
    public int compareTo(Ruutu toinenRuutu) {
        if (this.y < toinenRuutu.getY()) {
            return -1;
        } else if (this.y > toinenRuutu.getY()) {
            return 1;
        } else {
            if (this.x < toinenRuutu.getX()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public void laskeViereisetMiinat() {
        List<Ruutu> pelinRuudut = this.peli.getRuudut();
        
        for (Ruutu ruutu : pelinRuudut) {
            if (ruutu.getX() == this.x) {
                if (ruutu.getY() == this.y - 1 || ruutu.getY() == this.y + 1) {
                    if (ruutu.onMiinoitettu()) {
                        this.viereisetMiinat++;
                    }
                }
            } else if (ruutu.getY() == this.y) {
                if (ruutu.getX() == this.x - 1 || ruutu.getX() == this.x + 1) {
                    if (ruutu.onMiinoitettu()) {
                        this.viereisetMiinat++;
                    }
                }
            } else if (ruutu.getX() == this.x - 1 || ruutu.getX() == this.x + 1) {
                if (ruutu.getY() == this.y - 1 || ruutu.getY() == this.y + 1) {
                    if (ruutu.onMiinoitettu()) {
                        this.viereisetMiinat++;
                    }
                }
            }
        }
    }

    public int getViereisetMiinat() {
        return this.viereisetMiinat;
    }
    
    public void avaa() {
        this.avattu = true;
    }

    public boolean onAvattu() {
        return avattu;
    }

    public boolean onLiputettu() {
        return liputettu;
    }

    public void liputa() {
        if (!this.liputettu) {
            this.liputettu = true;
        } else {
            this.liputettu = false;
        }
    }
    
}
