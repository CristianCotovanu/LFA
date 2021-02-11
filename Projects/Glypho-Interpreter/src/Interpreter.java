import java.math.BigInteger;
import java.util.*;

public final class Interpreter {
    private int instructionIndex;
    private final int numericalBase;
    private final Decoder decoder;
    private final Deque<BigInteger> automateStack;
    private final List<String> instructions;
    private final Map<Integer, Integer> paranthesesMapping;

    public Interpreter(InputData inputData) {
        this.decoder = new Decoder();
        this.numericalBase = inputData.getNumericalBase();
        this.instructionIndex = 0;
        this.paranthesesMapping = new HashMap<>();
        this.instructions = decoder.decodeGlyphoString(inputData.getEncodedString(), paranthesesMapping);
        this.automateStack = new LinkedList<>();
    }

    public void interpretGlypho() {
        for (; instructionIndex < instructions.size(); instructionIndex++) {
            applyInstruction(instructions.get(instructionIndex));
        }
    }

    private void applyInstruction(String instruction) {
        int instructionBase10 = decoder.toBase10(instruction);

        switch (instructionBase10) {
            case 1: {
                try {
                    automateStack.push(new BigInteger(InputHandler.readStdinData(), numericalBase));
                } catch (Exception e) {
                    OutputHandler.writeException(instructionIndex);
                }
                break;
            }
            case 3:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                automateStack.addLast(automateStack.pop());
                break;
            case 4: {
                if (automateStack.size() < 2)
                    OutputHandler.writeException(instructionIndex);

                var first = automateStack.pop();
                var second = automateStack.pop();
                automateStack.push(first);
                automateStack.push(second);
                break;
            }
            case 5:
                automateStack.push(BigInteger.ONE);
                break;
            case 9:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                automateStack.push(automateStack.pollLast());
                break;
            case 10:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                automateStack.push(automateStack.peek());
                break;
            case 11: {
                if (automateStack.size() < 2)
                    OutputHandler.writeException(instructionIndex);

                var first = automateStack.pop();
                var second = automateStack.pop();
                automateStack.push(first.add(second));
                break;
            }
            case 12: {
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);
                else if (automateStack.peek().compareTo(BigInteger.ZERO) == 0)
                    instructionIndex = paranthesesMapping.get(instructionIndex);

                break;
            }
            case 13:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                OutputHandler.writeResult(automateStack.pop(), numericalBase);
                break;
            case 14: {
                if (automateStack.size() < 2)
                    OutputHandler.writeException(instructionIndex);

                var first = automateStack.pop();
                var second = automateStack.pop();
                automateStack.push(first.multiply(second));
                break;
            }
            case 15:
                if (automateStack.size() < 4)
                    OutputHandler.writeException(instructionIndex);

                var instructionToExecute = new LinkedList<BigInteger>();
                for (int i = 0; i < 4; i++)
                    instructionToExecute.add(automateStack.pop());
                var decodedInstruction = decoder.decodeInstruction(instructionToExecute);

                if (!decodedInstruction.equals(Constants.L_BRACE) && !decodedInstruction.equals(Constants.R_BRACE))
                    applyInstruction(decodedInstruction);
                else
                    OutputHandler.writeException(instructionIndex);

                break;
            case 16:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                automateStack.push(automateStack.pop().negate());
                break;
            case 17:
                if (automateStack.isEmpty())
                    OutputHandler.writeException(instructionIndex);

                automateStack.pop();
                break;
            case 18:
                instructionIndex = paranthesesMapping.get(instructionIndex) - 1;
                break;
        }
    }
}
