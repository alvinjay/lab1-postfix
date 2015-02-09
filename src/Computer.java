import com.sun.tools.javac.jvm.Gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alvinjay on 2/5/15.
 */
public class Computer implements Generator{

    private final String preOutputLine = "Evaluation: ";
    private ArrayList<String> variableNames;
    private HashMap<String, Integer> variables;
    private ArrayList<String> outputLines;

    private Object popped;
    private Integer first, second, tempAnswer;

    private Stack operand = new Stack();

    public Computer(ArrayList<String> variableNames, HashMap<String, Integer> variables, ArrayList<String> outputLines) {
        this.variableNames = variableNames;
        this.variables = variables;
        this.outputLines = outputLines;
        //a = 5
        //= 5 a
        //a 5
    }

    //computeValue
    public void computeValue (Stack a, int index) {

        // flag for expression or statement
        boolean expression = false;

        if (a.contains("=")) {
            operand.push(a.pop().toString());
            expression = true;
        }

        while(!a.isEmpty()) {
            popped = a.pop();
            if(variables.containsKey(popped)) {
                operand.push(variables.get(popped));
            }
            else if(popped.toString().equals("+")) {
                first = Integer.parseInt(operand.pop().toString());
                second = Integer.parseInt(operand.pop().toString());
                tempAnswer = second + first;
                operand.push(tempAnswer.toString());
//                System.out.println("plus");
            }
            //a b 2 + a - =
            //= - a + 2 b a
            else if(popped.toString().equals("-")) {
                first = Integer.parseInt(operand.pop().toString());
                second = Integer.parseInt(operand.pop().toString());
                tempAnswer = second - first;
                operand.push(tempAnswer.toString());
//                System.out.println("minus");
            }

            else if(popped.toString().equals("*")) {
                first = Integer.parseInt(operand.pop().toString());
                second = Integer.parseInt(operand.pop().toString());
                tempAnswer = second * first;
                operand.push(tempAnswer.toString());
//                System.out.println("times");
            }

            else if(popped.toString().equals("/")) {
                first = Integer.parseInt(operand.pop().toString());
                second = Integer.parseInt(operand.pop().toString());

                tempAnswer = second / first;
                operand.push(tempAnswer.toString());
//                System.out.println("divide");
            }

            else if(popped.toString().equals("%")) {
                first = Integer.parseInt(operand.pop().toString());
                second = Integer.parseInt(operand.pop().toString());
                tempAnswer = second % first;
                operand.push(tempAnswer.toString());
//                System.out.println("modulo");
            }

            else if(popped.toString().equals("=")) {
                int answer = Integer.parseInt(operand.pop().toString());
//                System.out.println(operand.peek().toString() + " = "  + answer);

                variables.put(operand.peek().toString(), answer);
                variableNames.add(operand.peek().toString());
            }

            else {
                operand.push(popped);
//                System.out.println("var");
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
