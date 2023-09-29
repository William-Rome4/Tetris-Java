package tetris;

public class Tetromino {
    private float x, y;
    private String name;
    private int[][] body;

    public Tetromino(float x, float y, int[][] body, String name) {
        this.x = x;
        this.y = y;
        this.body = body;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int[][] getBody() {
        return body;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setBody(int[][] body) {
        this.body = body;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
