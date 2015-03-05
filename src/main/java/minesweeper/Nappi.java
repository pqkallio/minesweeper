package minesweeper;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Nappi extends JPanel {
    private Peli peli;
    private int x;
    private int y;
    private int ruudunKoko;
    private boolean korostettu;
    private boolean peliLoppu;
    private final Color KOROSTAMATON_POHJAVARI = new Color(200,200,200);
    private final Color KOROSTAMATON_VARJOSTUS = new Color(230,230,230);
    private final Color KOROSTAMATON_KEVENNYS = new Color(150,150,150);
    private final Color BOOM_POHJAVARI = new Color(200, 100, 100);
    private final Color BOOM_VARJOSTUS = new Color(230, 100, 100);
    private final Color BOOM_KEVENNYS = new Color(150, 100, 100);
    private final Color[] KOROSTAMATTOMAT_VARIT = {KOROSTAMATON_POHJAVARI, 
        KOROSTAMATON_VARJOSTUS, KOROSTAMATON_KEVENNYS};
    private final Color[] AVATUN_VARIT = {KOROSTAMATON_POHJAVARI, 
        KOROSTAMATON_KEVENNYS, KOROSTAMATON_VARJOSTUS};
    private final Color[] BOOM_VARIT = {BOOM_POHJAVARI, 
        BOOM_KEVENNYS, BOOM_VARJOSTUS};
    private final Color[] MIINALUKUVARIT = {new Color(255,0,0), 
        new Color(200,0,0), new Color(150,0,0), new Color(0,255,0),
        new Color(0,200,0), new Color(0,150,0), new Color(0,0,255),
        new Color(0,0,200)};
    private final Font NUMEROFONTTI = new Font(Font.MONOSPACED, Font.BOLD, 16);
    private Ruutu ruutu;
    private Graphics2D graphics;
    
    public Nappi(Peli peli, int x, int y) {
        this.peli = peli;
        this.ruudunKoko = this.peli.getKayttoliittyma().getRuudunKoko();
        this.x = x;
        this.y = y;
        this.korostettu = false;
        this.peliLoppu = false;
        
        for (Ruutu pelinRuutu : this.peli.getRuudut()) {
            if (this.x == pelinRuutu.getX() && this.y == pelinRuutu.getY()) {
                this.ruutu = pelinRuutu;
            }
        }
        
        if (this.ruutu == null) {
            throw new IllegalStateException("Ruutua " + this.x + "/" + this.y 
                    + " ei löytynyt pelistä");
        }
        
        this.ruutu.setNappi(this);
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        this.graphics = (Graphics2D)graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(this.graphics);
        
        if (!this.ruutu.onAvattu()) {
            piirraNappi(KOROSTAMATTOMAT_VARIT);
            if (korostettu) {
                this.graphics.setColor(new Color(0,255,0,40));
                this.graphics.fillRect(0, 0, this.ruudunKoko, this.ruudunKoko);
            }
            
            if (this.ruutu.onLiputettu()) {
                piirraLippu();
            }
            
        } else if (this.ruutu.onMiinoitettu()){
            piirraNappi(BOOM_VARIT);
            piirraMiina();
        } else {
            piirraNappi(AVATUN_VARIT);
            if (this.ruutu.getViereisetMiinat() > 0 ) {
                piirraLuku(this.ruutu.getViereisetMiinat());
            }
        }
    }

    public Ruutu getRuutu() {
        return this.ruutu;
    }
    
    private void piirraNappi(Color[] varit) {
        this.graphics.setColor(varit[0]);
        this.graphics.fillRect(0, 0, this.ruudunKoko, this.ruudunKoko);
        
        this.graphics.setColor(varit[1]);
        this.graphics.fillRect(0, 0, this.ruudunKoko, 2);
        this.graphics.fillRect(0, 0, 2, this.ruudunKoko);
        
        this.graphics.setColor(varit[2]);
        this.graphics.fillRect(0, this.ruudunKoko - 2, this.ruudunKoko, 2);
        this.graphics.fillRect(this.ruudunKoko - 2, 0, 2, this.ruudunKoko);
    }
    
    public void korosta() {
        this.korostettu = true;
        super.repaint();
    }
    
    public void poistaKorostus() {
        this.korostettu = false;
        super.repaint();
    }
    
    public void avaa() {
        List<Ruutu> vierestaAvattavat = new ArrayList<Ruutu>();
        this.ruutu.avaa();
        
        if (this.ruutu.getViereisetMiinat() == 0 && !this.ruutu.onMiinoitettu()) {
            for (Ruutu toinenRuutu : this.peli.getRuudut()) {
                if (toinenRuutu.getX() == this.x - 1 
                        || toinenRuutu.getX() == this.x + 1
                        || toinenRuutu.getX() == this.x) {
                    if (toinenRuutu.getY() == this.y 
                            || toinenRuutu.getY() == this.y - 1 
                            || toinenRuutu.getY() == this.y + 1) {
                        if (!toinenRuutu.onAvattu() && !toinenRuutu.onLiputettu()) {
                            vierestaAvattavat.add(toinenRuutu);
                        }
                    }
                }
            }
            
            for (Ruutu avaamatonRuutu : vierestaAvattavat) {
                avaamatonRuutu.getNappi().avaa();
            }
        }
        
        super.repaint();
    }
    
    public void liputa() {
        this.ruutu.liputa();
        System.out.println(this.ruutu.onLiputettu());
        super.repaint();
    }

    private void piirraMiina() {
        this.graphics.setColor(new Color(50,50,50));
        this.graphics.fillOval((int)(Math.floor(this.ruudunKoko / 4 + 1)), this.ruudunKoko / 4 + 1, 
                this.ruudunKoko / 2 - 2, this.ruudunKoko / 2 - 2);
        this.graphics.drawLine(this.ruudunKoko / 2, this.ruudunKoko / 4, 
                this.ruudunKoko / 2, this.ruudunKoko / 2 + this.ruudunKoko / 4);
        this.graphics.drawLine(this.ruudunKoko / 4, this.ruudunKoko / 2, 
                this.ruudunKoko / 2 + this.ruudunKoko / 4, this.ruudunKoko / 2);
        this.graphics.drawLine(this.ruudunKoko / 3, this.ruudunKoko / 3, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3 * 2 + 2);
        this.graphics.drawLine(this.ruudunKoko / 3, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3);
    }

    private void piirraLuku(int viereisetMiinat) {
        this.graphics.setColor(MIINALUKUVARIT[viereisetMiinat - 1]);
        this.graphics.setFont(NUMEROFONTTI);
        this.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        this.graphics.drawString("" + viereisetMiinat, 
                (int)(this.ruudunKoko * 0.25), (int)(this.ruudunKoko * 0.75));
        
    }

    private void piirraLippu() {
        this.graphics.setColor(new Color(200,0,0));
        this.graphics.drawLine(this.ruudunKoko / 3, this.ruudunKoko / 3, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3 * 2 + 2);
        this.graphics.drawLine(this.ruudunKoko / 3, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3 * 2 + 2, 
                this.ruudunKoko / 3);
    }

    public void paataPeli() {
        this.peliLoppu = true;
    }
    
}
