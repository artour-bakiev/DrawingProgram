package ab.dp.commands;

import ab.dp.Command;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandBuilderTest {

    @Test
    public void shouldBuildCreateCanvasCommand() {
        shouldBuildCommand(new String[]{
                        "C 40 30",
                        "C 30 30 x",
                        "C 0 0"
                },
                CreateCanvas.class);
    }

    @Test
    public void shouldFailToBuildCreateCanvasCommand() {
        shouldFailToBuildCommand(new String[]{
                "C 40x 30",
                "C 40 30x",
                "C -1 20",
                "C 90 -14",
                "C 1 a",
        });
    }

    @Test
    public void shouldBuildCreateLineCommand() {
        shouldBuildCommand(new String[]{
                        "L 2 3 5 12",
                        "L 3 2 10 2 x"
                },
                CreateLine.class);
    }

    @Test
    public void shouldFailToBuildCreateLineCommand() {
        shouldFailToBuildCommand(new String[]{
                "L 2 3 10 12d",
                "L 2 2 10 -1",
                "L 2 2 10",
                "L 2x 2 10 1",
                "L 2 2w 10 1",
                "L 2 2 10f 1",
        });
    }

    @Test
    public void shouldBuildCreateRectangleCommand() {
        shouldBuildCommand(new String[]{
                        "R 2 3 10 12",
                        "R 2 2 10 12 x"
                },
                CreateRectangle.class);
    }

    @Test
    public void shouldFailToBuildCreateRectangleCommand() {
        shouldFailToBuildCommand(new String[]{
                "R 2 3 10 12d",
                "R 2 2 10 -1",
                "R 2 2 10",
                "R 2x 2 10 1",
                "R 2 2w 10 1",
                "R 2 2 10f 1",
        });
    }

    @Test
    public void shouldBuildBucketFillCommand() {
        shouldBuildCommand(new String[]{
                        "B 2 3 c",
                        "B 2 2 !",
                        "B 2 2 1"
                },
                BucketFill.class);
    }

    @Test
    public void shouldFailToBuildBucketFillCommand() {
        shouldFailToBuildCommand(new String[]{
                "B 2 3 10",
                "B 2 2 -1",
                "B 2 2 xx",
                "B 2x 2 d",
                "B 2 2w f",
                "B 2 2 10f",
        });
    }

    private void shouldBuildCommand(String[] userInput, Class<?> commandClass) {
        final CommandBuilder commandBuilder = new CommandBuilder();
        for (String s : userInput) {
            final Command command = commandBuilder.build(s);

            assertNotNull(command);
            assertTrue(commandClass.isInstance(command));
        }
    }

    private void shouldFailToBuildCommand(String[] userInput) {
        final CommandBuilder commandBuilder = new CommandBuilder();
        for (String s : userInput) {
            final Command command = commandBuilder.build(s);
            assertNull(command);
            assertNotNull(commandBuilder.hint());
            assertTrue(commandBuilder.hint().length() > 0);
        }
    }
}