package minesweeper;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;


public class Kayttoliittyma implements Runnable {
    private Peli peli;
    private JFrame frame;
    private int ruudunKoko;
    
    public Kayttoliittyma(Peli peli, int ruudunKoko) {
        this.peli = peli;
        this.ruudunKoko = ruudunKoko;
        this.peli.setKayttoliittyma(this);
    }
    
    @Override
    public void run() {
        this.frame = new JFrame("Miinaharava");
        
        this.frame.setPreferredSize(new Dimension(this.peli.getLeveys() * this.ruudunKoko + 50,
                this.peli.getKorkeus() * this.ruudunKoko + 50));
//        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(this.frame.getContentPane());
        
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void luoKomponentit(Container container) {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        container.setLayout(layout);
        
        int[] leveydet = new int[this.peli.getLeveys()];
        int[] korkeudet = new int[this.peli.getKorkeus()];
        
        for (int i = 0; i < leveydet.length; i++) {
            leveydet[i] = this.ruudunKoko;
        }
        
        for (int i = 0; i < korkeudet.length; i++) {
            korkeudet[i] = this.ruudunKoko;
        }
        
        layout.columnWidths = leveydet;
        layout.rowHeights = korkeudet;
        gbc.fill = GridBagConstraints.BOTH;
        
        for (int i = 0; i < korkeudet.length; i++) {
            for (int j = 0; j < leveydet.length; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                Nappi nappi = new Nappi(this.peli, j, i);
                nappi.addMouseListener(new Hiirenkuuntelija(nappi, this));
                layout.setConstraints(nappi, gbc);
                container.add(nappi);
            }
        }
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public int getRuudunKoko() {
        return ruudunKoko;
    }

    public void lopetaPeli() {
        JOptionPane.showMessageDialog(this.frame, "Hävisit!");
    }

    public Peli getPeli() {
        return peli;
    }
}
