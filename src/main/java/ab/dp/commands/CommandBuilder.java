package ab.dp.commands;

import ab.dp.Command;

import java.util.StringTokenizer;

public class CommandBuilder {

    private static final String createCanvasCommandHint = "C w h           Creates a new canvas of width w and height h.";
    private static final String createLineCommandHint = "L x1 y1 x2 y2   Creates a new line from (x1,y1) to (x2,y2). Currently only\n" +
            "                horizontal or vertical lines are supported. Horizontal and vertical lines\n" +
            "                will be drawn using the 'x' character. (1, 1) is top left";
    private static final String createRectangleCommandHint = "R x1 y1 x2 y2   Creates a new rectangle, whose upper left corner is (x1,y1) and\n" +
            "                lower right corner is (x2,y2). Horizontal and vertical lines will be drawn\n" +
            "                using the 'x' character. (1, 1) is top left";
    private static final String bucketFillCommandHint = "B x y c         Fills the entire area connected to (x,y) with \"colour\" c. The\n" +
            "                behaviour of this is the same as that of the \"bucket fill\" tool in paint\n" +
            "                programs. (1, 1) is top left";
    private static final String genericHint = "Available commands:" +
            "C               Creates a new canvas\n" +
            "L               Creates a new line\n" +
            "R               Creates a new rectangle\n" +
            "B               Fills the entire area\n" +
            "Q               Quits the program";

    private char lastCommandKey;

    public Command build(String userInput) {
        StringTokenizer tokenizer = new StringTokenizer(userInput, " ");
        if (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            if (token.length() > 1) {
                return null;
            }
            lastCommandKey = token.charAt(0);
            switch (lastCommandKey) {
                case 'C':
                    return buildCreateCanvasCommand(tokenizer);
                case 'L':
                    return buildCreateLineCommand(tokenizer);
                case 'R':
                    return buildCreateRectangleCommand(tokenizer);
                case 'B':
                    return buildBucketFillCommand(tokenizer);
                default:
                    return null;
            }
        }
        return null;
    }

    public String hint() {
        switch (lastCommandKey) {
            case 'C':
                return createCanvasCommandHint;
            case 'L':
                return createLineCommandHint;
            case 'R':
                return createRectangleCommandHint;
            case 'B':
                return bucketFillCommandHint;
            default:
                return genericHint;
        }
    }

    private static int[] readParameters(StringTokenizer tokenizer, int expected) {
        try {
            final int result[] = new int[expected];
            for (int n = 0; n != expected; ++n) {
                if (!tokenizer.hasMoreTokens()) {
                    return null;
                }
                final String token = tokenizer.nextToken();
                final int parameter = Integer.valueOf(token);
                result[n] = parameter;
            }
            return result;
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

    private static Command buildCreateCanvasCommand(StringTokenizer tokenizer) {
        int parameters[] = readParameters(tokenizer, 2);
        if (parameters == null) {
            return null;
        }
        if (parameters[0] < 0 || parameters[1] < 0) {
            return null;
        }
        return new CreateCanvas(parameters[0], parameters[1]);
    }

    private static Command buildCreateLineCommand(StringTokenizer tokenizer) {
        int parameters[] = readParameters(tokenizer, 4);
        if (parameters == null) {
            return null;
        }
        if (parameters[0] < 0 || parameters[1] < 0 || parameters[2] < 0 || parameters[3] < 0) {
            return null;
        }
        return new CreateLine(parameters[0], parameters[1], parameters[2], parameters[3]);
    }

    private static Command buildCreateRectangleCommand(StringTokenizer tokenizer) {
        int parameters[] = readParameters(tokenizer, 4);
        if (parameters == null) {
            return null;
        }
        if (parameters[0] < 0 || parameters[1] < 0 || parameters[2] < 0 || parameters[3] < 0) {
            return null;
        }
        return new CreateRectangle(parameters[0], parameters[1], parameters[2], parameters[3]);
    }

    private static Command buildBucketFillCommand(StringTokenizer tokenizer) {
        int parameters[] = readParameters(tokenizer, 2);
        if (parameters == null) {
            return null;
        }
        if (parameters[0] < 0 || parameters[1] < 0) {
            return null;
        }
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }
        String color = tokenizer.nextToken();
        if (color.length() != 1) {
            return null;
        }
        return new BucketFill(parameters[0], parameters[1], color.charAt(0));
    }
}
