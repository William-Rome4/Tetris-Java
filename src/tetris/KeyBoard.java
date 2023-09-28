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
        float position = 0;    
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
        //if(e.getKeyChar() == 'e' || e.getKeyChar() == 'q'){
        if(e.getKeyChar() == 'e'){
            boolean reverse = false;
            if(cena.angle > 0){
                cena.angle = (cena.angle * -1);
                reverse = true;
            }
            cena.angle -= 90;
            position = cena.angle/90;
            // Valores de rotação horária
            int e1 = -1, e2 = -2, e3 = -3, e4 = -4;
            // ADICIONAR SYSOUT PRINT PARA ENTENDER TROCAS DE POSIÇÃO
            while(position != 0){
                if(position == e1){
                    cena.moveX-=7;cena.moveY+=8;position=0;
                }else if (position == e2) {
                    if (reverse){
                        cena.angle += 180;
                    };
                    cena.moveX+=8;cena.moveY+=6;position=0;
                }else if (position == e3) {
                    if (reverse){
                        cena.angle += 180;
                    };
                    cena.moveX+=7;cena.moveY-=7;position=0;
                }else if (position == e4) {
                    cena.moveX-=8;cena.moveY-=7;position=0;
                }else{
                    e1-=4; e2-=4; e3-=4; e4-=4;
                }
            };
        };
        if(e.getKeyChar() == 'q'){
            boolean reverse = false;
            if(cena.angle < 0){
                cena.angle = (cena.angle * -1);
                reverse = true;
            }
            cena.angle += 90;
            position = cena.angle/90;
            // Valores de rotação anti-horária
            int q1 = 1, q2 = 2, q3 = 3, q4 = 4;
            while(position != 0){
                if(position == q1){
                    if (reverse){
                        cena.angle -= 180;
                    };
                    cena.moveX+=8;cena.moveY+=7;position=0;
                }else if (position == q2) {
                    cena.moveX-=7;cena.moveY+=7;position=0;
                }else if (position == q3) {
                    if (reverse){
                        cena.angle -= 180;
                    };
                    cena.moveX-=8;cena.moveY-=6;position=0;
                }else if (position == q4) {
                    cena.moveX+=7;cena.moveY-=8;position=0;
                }else{
                    q1+=4; q2+=4; q3+=4; q4+=4;
                }
            };
        };
        System.out.println(cena.angle);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
