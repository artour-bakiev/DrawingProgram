package ab.dp.commands;

import ab.dp.Canvas;
import ab.dp.Command;

public class CreateRectangle implements Command {

    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    CreateRectangle(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public Canvas run(Canvas canvas) {
        if (!canvas.addRect(left, top, right, bottom)) {
            return null;
        }
        return canvas;
    }
}
