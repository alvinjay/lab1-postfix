import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Gabriel Lagmay on 2/7/15.
 * Decription
 *  -include methods for evaluating postfix expressions
 */
public class Computer implements Generator{

    /* Pre output line label */
    private final String preOutputLine = "Evaluation: ";

    /* Acts as symbol table in the compilation process :) */
    private HashMap<String, Integer> variables;

    /* Arraylist of output lines for each input line */
    private ArrayList<String> outputLines;

    private Object popped;
    private Integer first, second, tempAnswer;

    private Stack operand = new Stack();

    public Computer(HashMap<String, Integer> variables, ArrayList<String> outputLines) {
        this.variables = variables;
        this.outputLines = outputLines;
    }

    public void computeValue (Stack a, int index) {

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
                switch (popped.toString().charAt(0)) {
                    case '+':
                        first = Integer.parseInt(operand.pop().toString());
                        second = Integer.parseInt(operand.pop().toString());
                        tempAnswer = second + first;
                        operand.push(tempAnswer.toString());
                        break;
                    case '-':
                        first = Integer.parseInt(operand.pop().toString());
                        second = Integer.parseInt(operand.pop().toString());
                        tempAnswer = second - first;
                        operand.push(tempAnswer.toString());
                        break;
                    case '*':
                        first = Integer.parseInt(operand.pop().toString());
                        second = Integer.parseInt(operand.pop().toString());
                        tempAnswer = second * first;
                        operand.push(tempAnswer.toString());
                        break;
                    case '/':
                        first = Integer.parseInt(operand.pop().toString());
                        second = Integer.parseInt(operand.pop().toString());
                        tempAnswer = second / first;
                        operand.push(tempAnswer.toString());
                        break;
                    case '%':
                        first = Integer.parseInt(operand.pop().toString());
                        second = Integer.parseInt(operand.pop().toString());
                        tempAnswer = second % first;
                        operand.push(tempAnswer.toString());
                        break;
                    case '=':
                        int answer = Integer.parseInt(operand.pop().toString());
                        variables.put(operand.peek().toString(), answer);
                        break;
                    default:
                        operand.push(popped);
                        break;
                }
            }
        }

        // generate output line based if expression or statement
        if (expression)
            generateOutputLine(operand.peek() + " = " + variables.get(operand.pop()), index);
        else
            generateOutputLine(operand.pop().toString(), index);
    }


    @Override
    public void generateOutputLine(String evaluation, int index) {
        // retrieve the existing output line for the index
        String indexOutputLine = outputLines.get(index);
        // append the postfix string
        indexOutputLine += preOutputLine  + ": " + evaluation + "\n";
        // set the new value to the index of the output line
        outputLines.set(index, indexOutputLine);
    }
}
