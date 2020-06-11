package ab.dp.commands;

import ab.dp.Canvas;
import ab.dp.Command;

public class CreateLine implements Command {

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    CreateLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public Canvas run(Canvas canvas) {
        if (!canvas.addLine(x1, y1, x2, y2)) {
            return null;
        }
        return canvas;
    }
}
