package ab.dp;

public interface Canvas {

    boolean addLine(int x1, int y1, int x2, int y2);

    boolean addRect(int left, int top, int right, int bottom);

    boolean addBucketFill(int x, int y, char color);

    void draw();
}
