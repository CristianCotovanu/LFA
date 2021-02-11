public class InputData {
    private final String encodedString;
    private final int numericalBase;

    public InputData(final String encoded, final int numericalBase) {
        this.encodedString = encoded;
        this.numericalBase = numericalBase;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public int getNumericalBase() {
        return numericalBase;
    }
}
