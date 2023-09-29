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
        if(e.getKeyChar() == 'a' || e.getKeyCode() == 149)
            cena.moveX--;
        if(e.getKeyChar() == 's' || e.getKeyCode() == 152)
            cena.piece.setY(cena.piece.getY()-0.5f);;
        if(e.getKeyChar() == 'd' || e.getKeyCode() == 151)
            cena.moveX++;
        if(e.getKeyChar() == 'e')
            cena.rotE = true;
        if(e.getKeyChar() == 'q')
            cena.rotQ = true;
        if(e.getKeyChar() == ' ' || e.getKeyCode() == 150)
            cena.piece.setY(-7.99f);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
