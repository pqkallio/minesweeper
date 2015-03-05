package minesweeper;

import java.util.*;

public class Peli {
    private int leveys;
    private int korkeus;
    private int miinojenMaara;
    private List<Ruutu> ruudut;
    private Kayttoliittyma kayttis;
    private Laskuri laskuri;
    
    public Peli(int leveys, int korkeus, int miinoja) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.miinojenMaara = miinoja;
        this.ruudut = new ArrayList<Ruutu>();
        this.laskuri = new Laskuri();
        
        luoKentta();
        miinoitaKentta();
        laskeMiinat();
        
        this.laskuri.start();
    }
    
    private void luoKentta() {
        for (int i = 0; i < this.korkeus; i++) {
            for (int j = 0; j < this.leveys; j++) {
                this.ruudut.add(new Ruutu(this, j, i));
            }
        }
    }

    private void miinoitaKentta() {
        Collections.shuffle(this.ruudut);
        
//        for (Ruutu ruutu : this.ruudut) {
//            ruutu.setKerroin(new Random().nextDouble());
//        }
//        
//        Collections.sort(this.ruudut, new MiinakerroinVertailija());
        
        for (int i = 0; i < this.miinojenMaara; i++) {
            this.ruudut.get(i).miinoita();
        }
    }
    
    public void tulostaKentta() {
        Collections.sort(this.ruudut);
        int ruudunIndeksi = 0;
        
        for (int i = 0; i < this.korkeus; i++) {
            for (int j = 0; j < this.leveys; j++) {
                if (this.ruudut.get(ruudunIndeksi).onMiinoitettu()) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
                
                ruudunIndeksi++;
            }
            
            System.out.println();
        }
    }

    public int getLeveys() {
        return leveys;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public List<Ruutu> getRuudut() {
        return ruudut;
    }

    public Kayttoliittyma getKayttoliittyma() {
        return kayttis;
    }

    public void setKayttoliittyma(Kayttoliittyma kayttis) {
        this.kayttis = kayttis;
    }

    private void laskeMiinat() {
        for (Ruutu ruutu : this.ruudut) {
            ruutu.laskeViereisetMiinat();
        }
    }
    
    public void tarkastaKentta(boolean peliJatkuu) {
        if (peliJatkuu) {
            List<Ruutu> avatut = new ArrayList<Ruutu>();

            for (Ruutu ruutu : this.ruudut) {
                if (!ruutu.onMiinoitettu()) {
                    if (ruutu.onAvattu()) {
                        avatut.add(ruutu);
                    }
                }
            }

            if (avatut.size() == this.ruudut.size() - this.miinojenMaara) {
                this.laskuri.stop();
                System.out.println("Voitit!");
            }
        } else {
            for (Ruutu ruutu : this.ruudut) {
                if (ruutu.onMiinoitettu() && !ruutu.onAvattu() && !ruutu.onLiputettu()) {
                    ruutu.getNappi().avaa();
                    this.laskuri.stop();
                }
            }
        }
    }
}