package minesweeper;

import javax.swing.SwingUtilities;

public class App 
{
    public static void main( String[] args )
    {
        Peli peli = new Peli(40, 25, 160);
        peli.tulostaKentta();
        Kayttoliittyma kayttis = new Kayttoliittyma(peli, 20);
        
        SwingUtilities.invokeLater(kayttis);
    }
}
