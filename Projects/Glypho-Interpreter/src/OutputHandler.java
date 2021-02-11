import java.math.BigInteger;

public final class OutputHandler {
    public static void writeResult(BigInteger number, int numericalBase) {
        if (numericalBase != 10)
            System.out.println(number.toString(numericalBase).toUpperCase());
        else
            System.out.println(number);
    }

    public static void writeError(int instructionNumber) {
        System.err.println("Error:" + instructionNumber);
        System.exit(-1);
    }

    public static void writeException(int instructionNumber) {
        System.err.println("Exception:" + instructionNumber);
        System.exit(-2);
    }
}
