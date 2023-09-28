package tetris;

import java.lang.Thread;
import java.util.Random;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
/**
 *
 * @author William Franz
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    public float angle, moveX, moveY, lower, num;
    GLU glu;
        
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -15;
        xMax = yMax = zMax = 15;
        num = 3;
        lower = angle = 0;
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 0);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity(); //lê a matriz identidade
        
        /*
            desenho da cena
        */
        //Random rn = new Random();
        //int num = rn.nextInt(7) + 1;
        //System.out.println(num);

        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(-8,-7.99f);
            gl.glVertex2f(8,-7.99f);
        gl.glEnd();

        gl.glTranslatef(0,lower,0);
        gl.glTranslatef(moveX,moveY,0);
        gl.glRotatef(angle,0.0f,0.0f,1.0f);
        if(num == 1){
            //Tetris Stick
            gl.glPushMatrix();
                gl.glColor3f(0,1,1);
                gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex2f(0f, 0f);
                    gl.glVertex2f(4f, 0f);
                    gl.glVertex2f(4f, 1f);
                    gl.glVertex2f(0f, 1f);
                gl.glEnd();
            gl.glPopMatrix();
        }else if(num == 2){
            //Tetris Reverse 'L'
            gl.glPushMatrix();
                gl.glColor3f(0,0,1);
                gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex2f(1f, 1f);
                    gl.glVertex2f(1f, 2f);
                    gl.glVertex2f(0f, 2f);
                    gl.glVertex2f(0f, 0f);
                    gl.glVertex2f(3f, 0f);
                    gl.glVertex2f(3f, 1f);
                gl.glEnd();
            gl.glPopMatrix();
        }else if(num == 3){
            //Tetris 'L'
            gl.glPushMatrix();
                gl.glColor3f(1,0.5f,0);
                gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex2f(0f, 7f);
                    gl.glVertex2f(0f, 6f);
                    gl.glVertex2f(-1f, 6f);
                    gl.glVertex2f(-1f, 8f);
                    gl.glVertex2f(2f, 8f);
                    gl.glVertex2f(2f, 7f);
                gl.glEnd();
            gl.glPopMatrix();
        }else if(num == 4){
            //Tetris 'L'
            gl.glColor3f(1,0.5f,0);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(1f, 0f);
                gl.glVertex2f(1f, -1f);
                gl.glVertex2f(0f, -1f);
                gl.glVertex2f(0f, 1f);
                gl.glVertex2f(4f, 1f);
                gl.glVertex2f(4f, 0f);
            gl.glEnd();
        };

        //lower-=0.03f;

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisão por zero
        if(height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade
        
        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
