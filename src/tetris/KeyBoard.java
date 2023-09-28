package tetris;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
/**
 *
 * @author William Franz
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        //System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        if(e.getKeyChar() == 'w')
            cena.moveY++;
        if(e.getKeyChar() == 'a')
            cena.moveX--;
        if(e.getKeyChar() == 's')
            cena.moveY--;
        if(e.getKeyChar() == 'd')
            cena.moveX++;
        if(e.getKeyChar() == 'e')
            cena.angle = cena.angle + 90;
        if(e.getKeyChar() == 'q')
            cena.angle = cena.angle - 90;
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
