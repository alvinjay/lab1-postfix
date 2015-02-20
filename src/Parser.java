/*
    Programmers: Alvin Jay Cosare, Gabriel Lagmay, Raphael Tugasan
    Exercise 1: Arithmetic Evaluation, Date Due: February 20, 2015
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alvinjay on 2/5/15.
 * Description:
 *  - include methods for parsing input files
 *  - includes conversion from infix to postfix
 */
public class Parser implements Generator {

    /* Class Instantiation */
    Constants constants = new Constants();

    /* Contains the postfixArrayStack lines for each input line */
    private ArrayList<String> outputLines;

    /* Operator weights */
    private HashMap<Character, Integer> OPERATOR_WEIGHTS;

    /* Stack for operators from inputs */
    private Stack ops = new Stack();
    /* Stack of input in stack form */
    private Stack postfixStack = new Stack();
    /* Arraylist of input in reverse stack form (for easier parsing in computing phase) */
    private ArrayList<String> postfixArray = new ArrayList<String>();

    /* Pre output line label */
    private final String preOutputLine = "Postfix: ";

    public Parser(ArrayList<String> outputLines) {
        this.outputLines = outputLines;
        this.OPERATOR_WEIGHTS = constants.getOperatorWeights();
    }

    /**
     * Returns the reversed postfix string in stack form (for easier parsing in computation phase)
     * @param line - input line currently being processed
     * @return
     */
    public Stack convertToPostfix(String line, int index) {
        // reset for new input line
        postfixArray.clear();

        /**
         * holds operands ONLY
         * NOTE: of type String because of possibility of 2 or more digit numbers
         */
        String operand = "";

        // process each character in the input line
        for (int i = 0; i < line.length(); i++) {
            // will hold the current character being processed for the input line
            char current = line.charAt(i);
            // check if current character is an operator or not
            if (!isOperator(current)) {
                // accumulates current character IN CASE of 2 or more digit integers
                operand += line.charAt(i);
            } else {
                // push accumulated number characters
                postfixStack.push(operand);
                // reset operand string to empty string
                operand = "";

                // check if stack holding input operations (ops) is empty or not
                if (!ops.empty()) {
                    // copy the topmost item in ops for comparison to current op
                    char temp = ops.peek().toString().charAt(0);
                    // compare current op with one in ops stack in terms of weight
                    // and then manipulate according to result
                    processOperators(temp, current);
                } else {
                    // push current op to ops stack
                    ops.push(current);
                }
            }
        }

        // push the last operand recorded
        postfixStack.push(operand);

        // pop out remaining operations left in ops stack
        // and push them to ouput stack
        while(!ops.empty()) {
            postfixStack.push(ops.pop());
        }

        // make reverse form of postfixArray string
        while(!postfixStack.empty()) {
            postfixArray.add(postfixStack.pop().toString());
        }

        generateOutputLine(convertArrayListToString(postfixArray), index);

        return convertArrayListToStack(postfixArray);
    }

    /**
     * Returns true if parameter is one of the pre determined operators
     * @param item
     * @return
     */
    private boolean isOperator(char item) {
        return OPERATOR_WEIGHTS.containsKey(item);
    }

    /**
     * Returns which operator has greater value
     * @param a - operator from stack
     * @param b - current operator from input line
     * @return
     */
    private void processOperators(char a, char b) {
//        System.out.println(a + " " + b);
        if (OPERATOR_WEIGHTS.get(((Object) b)) <= OPERATOR_WEIGHTS.get(((Object) a))) {
            postfixStack.push(a);
            ops.pop();
            ops.push(b);
        } else {
            ops.push(b);
        }
    }

    /**
     * Converts an array list to string form
     * NOTE: will give a reversed string as result
     * @param a
     * @return
     */
    private String convertArrayListToString(ArrayList a) {
        String postfixArrayString = "";

        for (int i = a.size() - 1; i >= 0; i--) {
            postfixArrayString += a.get(i) + " ";
        }

        return postfixArrayString;
    }

    /**
     * Converts the given array list to stack form
     * @param a
     * @return
     */
    private Stack convertArrayListToStack (ArrayList<String> a) {
        Stack temp = new Stack();
        for (int i = 0; i < a.size(); i++) {
            temp.push(a.get(i));
        }
        return temp;
    }

    @Override
    public void generateOutputLine(String postfixArray, int index) {
        // retrieve the existing postfixArrayStack line for the index
        String indexOutputLine = outputLines.get(index);
        // append the postfixArray string
        indexOutputLine += preOutputLine + postfixArray + "\n";
        // set the new value to the index of the postfixArrayStack line
        outputLines.set(index, indexOutputLine);
    }
}
