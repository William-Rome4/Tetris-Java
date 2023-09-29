package tetris;

import java.util.Random;
import com.jogamp.opengl.GL;
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
    public float angle, moveX, lower, num;
    // TODO private int[][] gameBoard;
    boolean rotE, rotQ;
    int[][] lblock = {{1, 0},{1, 0},{1, 1}};
    int[][] lreverse = {{0, 1},{0, 1},{1, 1}};
    int[][] tblock = {{0, 1},{1, 1},{0, 1}};
    int[][] line = {{0, 1},{0, 1},{0, 1},{0, 1}};
    int[][] square = {{1, 1},{1, 1}};
    int[][] squiggly = {{1, 0},{1, 1},{0, 1}};
    int[][] rsquiggly = {{0, 1},{1, 1},{1, 0}};
    Tetromino piece;
    Random rn = new Random();
    GLU glu;
        
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -8;
        xMax = yMax = zMax = 8;
        lower = num = angle = 0;
        piece = new Tetromino(-1, 5, line, "line");
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        GL2 gl = drawable.getGL().getGL2();       
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity(); 
        
        // Randomizador de peças
        int num = rn.nextInt(7) + 1;

        //System.out.println(piece.getY());
        if(piece.getY() <= -7.99f){
            // Inicializando a próxima peça após atingir o fundo
            switch (num) {
                case 1: piece = new Tetromino(-1, 5, lblock, "lblock");break;
                case 2: piece = new Tetromino(-1, 5, lreverse, "lreverse");break;
                case 3: piece = new Tetromino(-1, 5, line, "line");break;
                case 4: piece = new Tetromino(-1, 5, squiggly, "squiggly");break;
                case 5: piece = new Tetromino(-1, 5, rsquiggly, "rsquiggly");break;
                case 6: piece = new Tetromino(-1, 5, tblock, "tblock");break;
                default: piece = new Tetromino(-1, 5, square, "square");break;
            }
        }
        
        drawTetronimo(gl, piece);

        gl.glFlush();
    }

    private void drawTetronimo(GL2 gl, Tetromino piece){
        float x = piece.getX();
        float y = piece.getY();
        int[][] body = piece.getBody();

        gl.glPushMatrix();
            // Translação para gerenciar a movimentação das peças
            gl.glTranslatef(x+moveX, y, 0);
            if(rotE)
                piece.setBody(rotateE(piece)); rotE = false;
            if(rotQ)
                piece.setBody(rotateQ(piece)); rotQ = false;
            gl.glBegin(GL2.GL_QUADS);

            for (int row = 0; row < body.length; row++) {
                for (int col = 0; col < body[row].length; col++) {
                    if (body[row][col] == 1) {
                        float blockX = col;
                        float blockY = row;
                        // Colorindo as peças
                        switch (piece.getName()) {
                            case "lblock": gl.glColor3f(0.0f,0.0f,0.5f); break;
                            case "lreverse": gl.glColor3f(1,0.5f,0); break;
                            case "tblock": gl.glColor3f(1,0.0f,1); break;
                            case "line": gl.glColor3f(0.0f,1f,1f);break;
                            case "square": gl.glColor3f(1f,1f,0f);break;
                            case "squiggly": gl.glColor3f(1f,0f,0f);break;
                            case "rsquiggly": gl.glColor3f(0f,1f,0f);break;
                            default: break;
                        }
                        // "Imprime" a peça de acordo com a matriz definida para o valor do Tetronimo.body
                        gl.glVertex2f(blockX, blockY);
                        gl.glVertex2f(blockX + 1, blockY);
                        gl.glVertex2f(blockX + 1, blockY + 1);
                        gl.glVertex2f(blockX, blockY + 1);
                    }
                }
            }
            gl.glEnd();

            // Lógica para descer a posição das peças ao decorrer do jogo
            if(piece.getY() > -7.99f)
                piece.setY(y-=0.04f);
            else
                piece.setY(-7.99f);

            gl.glPopMatrix();
    }

    // Rotação em sentido Anti-horário
    public int[][] rotateQ(Tetromino piece){
        int[][] body = piece.getBody();
        int rows = body.length;
        int cols = body[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = body[i][j];
            }
        }
        return rotated;
    }

    // Rotação em sentido Horário
    public int[][] rotateE(Tetromino piece){
        int[][] body = piece.getBody();
        int rows = body.length;
        int cols = body[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[cols - 1 - j][i] = body[i][j];
            }
        }
        return rotated;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        GL2 gl = drawable.getGL().getGL2();  
        
        if(height == 0) height = 1;
        float aspect = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); 

        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
