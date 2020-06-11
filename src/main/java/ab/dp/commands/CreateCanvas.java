package ab.dp.commands;

import ab.dp.Command;
import ab.dp.Canvas;
import ab.dp.canvas.ConsoleCanvas;

public class CreateCanvas implements Command {

    private final int width;
    private final int height;

    CreateCanvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Canvas run(Canvas canvas) {
        return new ConsoleCanvas(width, height);
    }
}
