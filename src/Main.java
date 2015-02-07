import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alvinjay on 2/5/15.
 */
public class Main implements Generator{

    // acts as symbol table in the compilation process :)
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    // arraylist of output lines for each input line
    private ArrayList<String> outputLines = new ArrayList<String>();

    Parser parser = new Parser(outputLines);
    Computer comp = new Computer(variables, outputLines);

    private final String preOutputLine = "Line ";

    public static void main(String[] args){
        Main m = new Main();
        m.process();
    }

    public void process () {
        String sample = "a = 671 * 23 % 3 / 9 - 1 + 3\nb = 3\n3 * 9 / 5 + 1 % 2";
        String[] temp = sample.split("\n");

        for (int i = 0; i < temp.length; i++) {
            String line = temp[i];
            generateOutputLine(line, i);
            Stack temps = parser.convertToPostfix(line, i);
            comp.computeValue(temps);
        }

        for (int i = 0; i < outputLines.size(); i++) {
            System.out.println(outputLines.get(i));
        }
    }

    @Override
    public void generateOutputLine(String given, int index) {
        // include pre output line words for readability
        String indexOutputLine = preOutputLine + (index + 1) + ": " + given + "\n";
        // add to arraylist of output lines
        outputLines.add(index, indexOutputLine);
    }
}
