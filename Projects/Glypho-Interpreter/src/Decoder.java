import java.math.BigInteger;
import java.util.*;

public class Decoder {
    private final Map<String, Integer> instructionMapping;

    public Decoder() {
        instructionMapping = buildInstructionDecodingMap();
    }

    public int toBase10(String instruction) {
        return instructionMapping.get(instruction);
    }

    public List<String> decodeGlyphoString(String encodedString, Map<Integer, Integer> paranthesesCorrespondence) {
        if (encodedString.length() % 4 != 0)
            OutputHandler.writeError(encodedString.length() / 4);

        var instructions = new ArrayList<String>();
        Stack<Pair<Boolean, Integer>> paranthesesBalance = new Stack<>();
        for (int i = 0; i < encodedString.length() - 3; i += 4) {
            var instruction = decodeInstruction(encodedString.substring(i, i + 4));

            if (instruction.equals(Constants.L_BRACE))
                paranthesesBalance.push(new Pair<>(true, i / 4));
            else if (instruction.equals(Constants.R_BRACE)) {
                if (!paranthesesBalance.isEmpty() && paranthesesBalance.peek().getKey()) {
                    paranthesesCorrespondence.put(i / 4, paranthesesBalance.peek().getValue());
                    paranthesesCorrespondence.put(paranthesesBalance.peek().getValue(), i / 4);
                    paranthesesBalance.pop();
                } else {
                    paranthesesBalance.push(new Pair<>(false, i / 4));
                }
            }

            instructions.add(instruction);
        }

        if (!paranthesesBalance.isEmpty()) {
            if (paranthesesBalance.peek().getKey()) {
                OutputHandler.writeError(instructions.size());
            } else {
                OutputHandler.writeError(paranthesesBalance.peek().getValue());
            }
        }

        return instructions;
    }

    public String decodeInstruction(String instruction) {
        var decoded = new StringBuilder();
        var letterMapping = new HashMap<Character, Integer>();
        int uniqueLetters = 0;

        for (var c : instruction.toCharArray()) {
            if (!letterMapping.containsKey(c)) {
                letterMapping.put(c, uniqueLetters);
                uniqueLetters++;
            }
            decoded.append(letterMapping.get(c));
        }

        return decoded.toString();
    }

    public String decodeInstruction(List<BigInteger> instruction) {
        var decoded = new StringBuilder();
        var letterMapping = new HashMap<BigInteger, Integer>();
        int uniqueLetters = 0;

        for (var c : instruction) {
            if (!letterMapping.containsKey(c)) {
                letterMapping.put(c, uniqueLetters);
                uniqueLetters++;
            }
            decoded.append(letterMapping.get(c));
        }

        return decoded.toString();
    }

    private HashMap<String, Integer> buildInstructionDecodingMap() {
        var instructionMapping = new HashMap<String, Integer>();
        instructionMapping.put(Constants.NOP, 0);
        instructionMapping.put(Constants.INPUT, 1);
        instructionMapping.put(Constants.ROT, 3);
        instructionMapping.put(Constants.SWAP, 4);
        instructionMapping.put(Constants.PUSH, 5);
        instructionMapping.put(Constants.RROT, 9);
        instructionMapping.put(Constants.DUP, 10);
        instructionMapping.put(Constants.ADD, 11);
        instructionMapping.put(Constants.L_BRACE, 12);
        instructionMapping.put(Constants.OUTPUT, 13);
        instructionMapping.put(Constants.MULTIPLY, 14);
        instructionMapping.put(Constants.EXECUTE, 15);
        instructionMapping.put(Constants.NEGATE, 16);
        instructionMapping.put(Constants.POP, 17);
        instructionMapping.put(Constants.R_BRACE, 18);
        return instructionMapping;
    }
}
