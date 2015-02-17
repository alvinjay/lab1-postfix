import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Gabriel Lagmay on 2/7/15.
 * Decription
 *  -include methods for evaluating postfix expressions
 */
public class Evaluator implements Generator{

    /* Pre output line label */
    private final String preOutputLine = "Evaluation: ";

    /* Acts as symbol table in the compilation process :) */
    private HashMap<String, Long> variables;

    /* Arraylist of output lines for each input line */
    private ArrayList<String> outputLines;

    /* Temporary variables */
    private Object popped;
    private Long first, second, tempAnswer;

    private Stack operand = new Stack();

    public Evaluator(HashMap<String, Long> variables, ArrayList<String> outputLines) {
        this.variables = variables;
        this.outputLines = outputLines;
    }

    /**
     * Evaluates the corresponding value for postfix stack
     * @param a - postfix stack
     * @param index - (line number - 1)
     * @return
     */
    public Boolean computeValue (Stack a, int index) {

        // flag for expression or statement
        boolean expression = false;

        // check whether expression or statement
        if (a.contains("=")) {
            operand.push(a.pop().toString());
            expression = true;
        }

        while(!a.isEmpty()) {
            popped = a.pop();

            // if variable is declared and has a value then push the value instead
            if(variables.containsKey(popped)) {
                operand.push(variables.get(popped));
            }
            else {
                try {
                    switch (popped.toString().charAt(0)) {
                        case '+':
                            first = Long.parseLong(operand.pop().toString());
                            second = Long.parseLong(operand.pop().toString());
                            tempAnswer = second + first;
                            operand.push(tempAnswer.toString());
                            break;
                        case '-':
                            first = Long.parseLong(operand.pop().toString());
                            second = Long.parseLong(operand.pop().toString());
                            tempAnswer = second - first;
                            operand.push(tempAnswer.toString());
                            break;
                        case '*':
                            first = Long.parseLong(operand.pop().toString());
                            second = Long.parseLong(operand.pop().toString());
                            tempAnswer = second * first;
                            operand.push(tempAnswer.toString());
                            break;
                        case '/':
                            first = Long.parseLong(operand.pop().toString());
                            second = Long.parseLong(operand.pop().toString());
                            tempAnswer = second / first;
                            operand.push(tempAnswer.toString());
                            break;
                        case '%':
                            first = Long.parseLong(operand.pop().toString());
                            second = Long.parseLong(operand.pop().toString());
                            tempAnswer = second % first;
                            operand.push(tempAnswer.toString());
                            break;
                        case '=':
                            Long answer = Long.parseLong(operand.pop().toString());
                            variables.put(operand.peek().toString(), answer);
                            break;
                        default:
                            operand.push(popped);
                            break;
                    }
                } catch(NumberFormatException e) {
                    generateOutputLine("Undefined " + e.getMessage().replaceAll("For input string", "variable"), index);
                    return false;
                }
            }
        }

        // generate output line based if expression or statement
        if (expression)
            generateOutputLine(operand.peek() + " = " + variables.get(operand.pop()), index);
        else
            generateOutputLine(operand.pop().toString(), index);

        return true;
    }

    @Override
    public void generateOutputLine(String evaluation, int index) {
        // retrieve the existing output line for the index
        String indexOutputLine = outputLines.get(index);
        // append the postfix string
        indexOutputLine += preOutputLine + evaluation + "\n";
        // set the new value to the index of the output line
        outputLines.set(index, indexOutputLine);
    }
}
