package minesweeper;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class Laskuri extends Timer implements ActionListener {
    private int ticks;
    
    public Laskuri() {
        super(1000, null);
        this.ticks = 0;
        super.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.ticks++;
        System.out.println(this.ticks);
    }

    public int getTicks() {
        return ticks;
    }
}
