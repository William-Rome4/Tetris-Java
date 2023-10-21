package tetris;

import java.util.ArrayList;
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
    public float angle, lower, num;
    boolean rotE, rotQ, stop;
    int[][] lblock = {{1, 0},{1, 0},{1, 1}};
    int[][] lreverse = {{0, 1},{0, 1},{1, 1}};
    int[][] tblock = {{0, 1},{1, 1},{0, 1}};
    int[][] line = {{0, 1},{0, 1},{0, 1},{0, 1}};
    int[][] square = {{1, 1},{1, 1}};
    int[][] squiggly = {{1, 0},{1, 1},{0, 1}};
    int[][] rsquiggly = {{0, 1},{1, 1},{1, 0}};
    Tetromino piece;
    ArrayList<Tetromino> pieces = new ArrayList<Tetromino>();
    ArrayList<Float> limitsX = new ArrayList<Float>();
    ArrayList<Float> limitsY = new ArrayList<Float>();
    Random rn = new Random();
    GLU glu;
        
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -8;
        xMax = yMax = zMax = 8;
        lower = num = angle = 0;
        stop = false;
        limitsX.add(8f);limitsY.add(8f);
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

        drawGameNow(gl, pieces);

        for(int i=0;i<limitsX.size();i++){
            float x = limitsX.get(i);
            float y = limitsY.get(i);
            if(piece.getName() == "line"){
                y = y + 4f;
            } else if(piece.getName() == "square"){
                y = y + 2f;
            } else {
                y = y + 3f;
            }
            if(piece.getY() <= -7.99f || (int)piece.getY() == (int)y && (int)piece.getX() == (int)x) {
                // Adicionando a peça na lista, para mapear a posição atual do jogo
                pieces.add(piece);
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
                break;
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
            gl.glTranslatef(x, y, 0);
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
                piece.setY(-8f);
            

            gl.glPopMatrix();
    }

    private void drawGameNow(GL2 gl, ArrayList<Tetromino> pieces) {
        for (Tetromino piece : pieces) {
            float x = piece.getX();
            float y = piece.getY();
            limitsX.add(x);
            limitsY.add(y);
            int[][] body = piece.getBody();
            gl.glPushMatrix();
                gl.glTranslatef(x, y, 0);
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
            gl.glPopMatrix();
        }
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
