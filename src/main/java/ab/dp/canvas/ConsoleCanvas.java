package ab.dp.canvas;

import ab.dp.Canvas;

import java.util.ArrayDeque;
import java.util.Queue;

public class ConsoleCanvas implements Canvas {

    final char buffer[][];

    public ConsoleCanvas(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException();
        }
        buffer = new char[height + 2][width + 2];
        for (int row = 0; row != buffer.length; ++row) {
            for (int column = 0; column != buffer[0].length; ++column) {
                if (row == 0 || row == buffer.length - 1) {
                    buffer[row][column] = '-';
                } else if (column == 0 || column == buffer[0].length - 1) {
                    buffer[row][column] = '|';
                } else {
                    buffer[row][column] = ' ';
                }
            }
        }
    }

    @Override
    public boolean addLine(int x1, int y1, int x2, int y2) {
        if (invalidRange(x1, y1) || invalidRange(x2, y2)) {
            return false;
        }
        if (x1 == x2) {
            for (int row = y1; row != y2 + 1; ++row) {
                buffer[row][x1] = 'x';
            }
            return true;
        } else if (y1 == y2) {
            for (int column = x1; column != x2 + 1; ++column) {
                buffer[y1][column] = 'x';
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addRect(int left, int top, int right, int bottom) {
        if (invalidRange(left, top) || invalidRange(right, bottom)) {
            return false;
        }
        if (left > right || top > bottom) {
            return false;
        }
        for (int column = left; column != right + 1; ++column) {
            buffer[top][column] = 'x';
            buffer[bottom][column] = 'x';
        }
        for (int row = top + 1; row < bottom; ++row) {
            buffer[row][left] = 'x';
            buffer[row][right] = 'x';
        }
        return true;
    }

    @Override
    public boolean addBucketFill(int x, int y, char color) {
        if (invalidRange(x, y)) {
            return false;
        }
        final char targetColor = buffer[y][x];
        if (targetColor == color) {
            return true;
        }
        Queue<Integer[]> cells = new ArrayDeque<Integer[]>();
        cells.add(new Integer[]{x, y});
        while (!cells.isEmpty()) {
            Integer[] cell = cells.remove();
            int w = cell[0] - 1;
            while (buffer[cell[1]][w] == targetColor) {
                --w;
            }
            int e = cell[0] + 1;
            while (buffer[cell[1]][e] == targetColor) {
                ++e;
            }
            for (int column = w + 1; column != e; ++column) {
                buffer[cell[1]][column] = color;
                if (buffer[cell[1] - 1][column] == targetColor) {
                    cells.add(new Integer[]{column, cell[1] - 1});
                }
                if (buffer[cell[1] + 1][column] == targetColor) {
                    cells.add(new Integer[]{column, cell[1] + 1});
                }
            }
        }
        return true;
    }

    @Override
    public void draw() {
        for (int row = 0; row != buffer.length; ++row) {
            for (int column = 0; column != buffer[0].length; ++column) {
                final char symbol = buffer[row][column];
                System.out.print(symbol);
            }
            System.out.println();
        }
        System.out.flush();
    }

    private boolean invalidRange(int x, int y) {
        return x < 1 || x > buffer[0].length - 2 || y < 1 || y > buffer.length - 2;
    }
}
