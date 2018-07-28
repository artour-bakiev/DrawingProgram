package ab.dp.canvas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleCanvasTest {

    @Test
    void invalidArgumentsShouldThrowAnException() {
        try {
            new ConsoleCanvas(-1, 1);
            fail("ConsoleCanvas() should thrown IllegalArgumentException");
        } catch (IllegalArgumentException ignore) {
        }

        try {
            new ConsoleCanvas(1, -1);
            fail("ConsoleCanvas() should thrown IllegalArgumentException");
        } catch (IllegalArgumentException ignore) {
        }
    }

    @Test
    void freshlyCreatedShouldBeEmpty() {
        int width = 20;
        int height = 10;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertEquals(height + 2, canvas.buffer.length);
        assertEquals(width + 2, canvas.buffer[0].length);
        bufferShouldBeEmpty(canvas);
    }

    @Test
    void shouldAddVerticalLine() {
        int width = 29;
        int height = 48;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);
        int x1 = 1;
        int y1 = 1;

        assertTrue(canvas.addLine(x1, y1, x1, height));
        for (int row = y1; row != height + 1; ++row) {
            assertEquals('x', canvas.buffer[row][x1], "buffer[" + row + "][" + x1 + ']');
        }
    }

    @Test
    void shouldAddHorizontalLine() {
        int width = 33;
        int height = 12;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);
        int x1 = 1;
        int y1 = 12;

        assertTrue(canvas.addLine(x1, y1, width, y1));
        for (int column = x1; column != width + 1; ++column) {
            assertEquals('x', canvas.buffer[y1][column], "buffer[" + y1 + "][" + column + ']');
        }
    }

    @Test
    void shouldNotAddLine() {
        int width = 13;
        int height = 4;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertFalse(canvas.addLine(1, 1, 10, 10));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addLine(0, 1, 0, 10));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addLine(1, 0, 10, 0));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addLine(1, 1, 1, height + 1));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addLine(1, 1, width + 1, 1));
        bufferShouldBeEmpty(canvas);
    }

    @Test
    void shouldAddRectangle() {
        int left = 1;
        int top = 1;
        int width = 91;
        int height = 8;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertTrue(canvas.addRect(left, top, width, height));
        for (int column = left; column != width + 1; ++column) {
            assertEquals('x', canvas.buffer[top][column], "buffer[" + top + "][" + column + ']');
            assertEquals('x', canvas.buffer[height][column], "buffer[" + height + "][" + column + ']');
        }
        for (int row = top + 1; row != height; ++row) {
            assertEquals('x', canvas.buffer[row][left], "buffer[" + row + "][" + left + ']');
            assertEquals('x', canvas.buffer[row][width], "buffer[" + row + "][" + width + ']');
        }
    }

    @Test
    void shouldNotAddRectangle() {
        int width = 14;
        int height = 99;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertFalse(canvas.addRect(3, 3, 1, 1));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addRect(3, 3, width + 1, height));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addRect(3, 3, width, height + 1));
        bufferShouldBeEmpty(canvas);
    }

    @Test
    void shouldFillWholeBuffer() {
        int width = 91;
        int height = 8;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        char color = 'c';
        assertTrue(canvas.addBucketFill(5, 5, color));
        for (int row = 1; row != canvas.buffer.length - 1; ++row) {
            for (int column = 1; column != canvas.buffer[0].length - 1; ++column) {
                assertEquals(color, canvas.buffer[row][column], "buffer[" + row + "][" + column + ']');
            }
        }
    }

    @Test
    void shouldKeepEmptyColor() {
        int width = 21;
        int height = 83;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertTrue(canvas.addBucketFill(width, height, ' '));
        bufferShouldBeEmpty(canvas);
    }

    @Test
    void shouldKeepTargetColor() {
        int width = 52;
        int height = 13;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);
        canvas.addRect(1, 1, 1, 1);

        assertTrue(canvas.addBucketFill(1, 1, 'x'));
        assertEquals(' ', canvas.buffer[1][2]);
        assertEquals(' ', canvas.buffer[2][1]);
    }

    @Test
    void shouldNotAddBucketFill() {
        int width = 14;
        int height = 33;
        ConsoleCanvas canvas = new ConsoleCanvas(width, height);

        assertFalse(canvas.addBucketFill(0, 5, 'Z'));
        bufferShouldBeEmpty(canvas);
        assertFalse(canvas.addBucketFill(1, height + 1, 'Z'));
        bufferShouldBeEmpty(canvas);
    }

    private void bufferShouldBeEmpty(ConsoleCanvas canvas) {
        for (int row = 1; row != canvas.buffer.length - 1; ++row) {
            for (int column = 1; column != canvas.buffer[0].length - 1; ++column) {
                assertEquals(' ', canvas.buffer[row][column], "buffer[" + row + "][" + column + ']');
            }
        }
    }
}
