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
        if(e.getKeyChar() == 'a' || e.getKeyCode() == 149){
            if(cena.piece.getName() == "line" && cena.piece.getX() > -8.99f){
                cena.piece.setX(cena.piece.getX()-0.5f);
            }
            else if(cena.piece.getX() > -7.99f){
                cena.piece.setX(cena.piece.getX()-0.5f);
            }
        }
        if(e.getKeyChar() == 'd' || e.getKeyCode() == 151){
            if(cena.piece.getX() < 5.99f){
                cena.piece.setX(cena.piece.getX()+0.5f);
            }
        }
        if(e.getKeyChar() == 's' || e.getKeyCode() == 152)
            cena.piece.setY(cena.piece.getY()-0.5f);
        if(e.getKeyChar() == 'e'){
            if(cena.piece.getX() < 5.99f){
                cena.rotE = true;
            }
        }
        if(e.getKeyChar() == 'q'){
            if(cena.piece.getName() != "square" && cena.piece.getX() == 6.0f){
                cena.piece.setX(cena.piece.getX()-1f);
            }
            if(cena.piece.getX() > -7.99f){
                cena.rotQ = true;
            }
        }
        if(e.getKeyChar() == ' ' || e.getKeyCode() == 150)
            cena.piece.setY(-7.99f);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
