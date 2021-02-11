import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public final class InputHandler {
    private static final Scanner stdinReader = new Scanner(System.in);

    public static String readStdinData() {
        return stdinReader.nextLine();
    }

    public static String readFileData(final String inputPath) {
        try (var reader = new BufferedReader(new FileReader(inputPath))) {
            var encodedString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                encodedString.append(line);
            reader.close();
            return encodedString.toString();
        } catch (Exception e) {
            System.out.println("File not found");
            System.exit(1);
            return null;
        }
    }
}
