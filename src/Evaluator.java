/*
    Programmers: Alvin Jay Cosare, Gabriel Lagmay, Raphael Tugasan
    Exercise 1: Arithmetic Evaluation, Date Due: February 20, 2015
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Gabriel Lagmay on 2/7/15.
 * Decription
 *  -include methods for evaluating postfix expressions
 */
public class Evaluator implements Generator{

    /* Class Instantiation */
    private Operations op = new Operations();

    /* Pre output line label */
    private final String preOutputLine = "Result: ";

    /* Acts as symbol table in the compilation process :) */
    private HashMap<String, Long> variables;

    /* Arraylist of output lines for each input line */
    private ArrayList<String> outputLines;
    /* Arraylist of the errors encountered during process file */
    private ArrayList<String> errors = new ArrayList<String>();

    /* Temporary variables */
    private Object popped;
    private Long first, second, tempAnswer;

    private Stack operand = new Stack();

    public Evaluator(HashMap<String, Long> variables, ArrayList<String> outputLines, ArrayList<String> errors) {
        this.variables = variables;
        this.outputLines = outputLines;
        this.errors = errors;
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
                            operand.push(op.additionOp(operand).toString());
                            break;
                        case '-':
                            operand.push(op.subtractionOp(operand).toString());
                            break;
                        case '*':
                            operand.push(op.multiplicationOp(operand).toString());
                            break;
                        case '/':
                            operand.push(op.divisionOp(operand).toString());
                            break;
                        case '%':
                            operand.push(op.moduloOp(operand).toString());
                            break;
                        case '=':
                            Long answer = op.assignmentOp(operand);
                            variables.put(operand.peek().toString(), answer);
                            break;
                        default:
                            operand.push(popped);
                            break;
                    }
                } catch(NumberFormatException e) {
                    String errorMessage = "Undefined " + e.getMessage().replaceAll("For input string", "variable");
                    generateOutputLine(errorMessage, index);
                    errors.add("-Line " + (index + 1) + ": " + errorMessage);
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
