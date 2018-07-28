package ab.dp.commands;

import ab.dp.Canvas;
import ab.dp.Command;

public class BucketFill implements Command {

    private final int x;
    private final int y;
    private final char color;

    BucketFill(int x, int y, char color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public Canvas run(Canvas canvas) {
        if (!canvas.addBucketFill(x, y, color)) {
            return null;
        }
        return canvas;
    }
}
