public class Main {

    public static void main(String[] args) {
        var inputData = new InputData(
                InputHandler.readFileData(args[0]),
                args.length > 1 ? Integer.parseInt(args[1]) : 10);
        var interpreter = new Interpreter(inputData);
        interpreter.interpretGlypho();
    }
}
