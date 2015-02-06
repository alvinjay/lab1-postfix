import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alvinjay on 2/5/15.
 */
public class Parser {
    Constants constants = new Constants();

    private ArrayList<String> outputLines;
    // pre defined operators
    private String OPERATORS;
    // operator weights
    private HashMap<Character, Integer> OPERATOR_WEIGHTS;
    // stacks for operators from inputs
    private Stack ops = new Stack();
    // postfix of input in stack form
    private Stack output = new Stack();
    // postfix of input in reverse stack form (for easier parsing in computing phase)
    private Stack postfix = new Stack();

    public Parser(ArrayList<String> outputLines) {
        this.outputLines = outputLines;
        this.OPERATORS = constants.getOperators();
        this.OPERATOR_WEIGHTS = constants.getOperatorWeights();
    }

    /**
     * Returns the reversed postfix string in stack form (for easier parsing in computation phase)
     * @param line - input line currently being processed
     * @return
     */
    public Stack convertToPostfix(String line) {
        // reset for new input line
        postfix.clear();
        // holds operands ONLY
        // NOTE: of type String because of possibility of 2 or more digit numbers
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
                output.push(operand);
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
        output.push(operand);

        // pop out remaining operations left in ops stack
        // and push them to ouput stack
        while(!ops.empty()) {
            output.push(ops.pop());
        }

        // make reverse form of postfix form
        while(!output.empty()) {
            postfix.push(output.pop());
        }

        return postfix;
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
     * @param a
     * @param b
     * @return
     */
    private void processOperators(char a, char b) {
        System.out.println(a + " " + b);
        if (OPERATOR_WEIGHTS.get(((Object) b)) <= OPERATOR_WEIGHTS.get(((Object) a))) {
            output.push(a);
            ops.pop();
            ops.push(b);
        } else {
            ops.push(b);
        }
    }

    //convertToPostfix
    //isExpression
    //isOperator
}
