import java.util.HashMap;

/**
 * Created by alvinjay on 2/5/15.
 */
public class Constants {
    // predefined operators
    private final String OPERATORS = "=+-*/%";
    // operators (key) and corresponding weights (value)
    private HashMap<Character,Integer> OPERATORS_VALUES = new HashMap<Character, Integer>();

    public Constants() {
        // assign weights to the operators (precendence)
        for (int i = 0; i < OPERATORS.length(); i++) {
            switch(OPERATORS.charAt(i)){
                case '=': OPERATORS_VALUES.put(OPERATORS.charAt(i), 0);
                    break;
                case '+': OPERATORS_VALUES.put(OPERATORS.charAt(i), 1);
                    break;
                case '-': OPERATORS_VALUES.put(OPERATORS.charAt(i), 1);
                    break;
                case '*': OPERATORS_VALUES.put(OPERATORS.charAt(i), 2);
                    break;
                case '/': OPERATORS_VALUES.put(OPERATORS.charAt(i), 2);
                    break;
                case '%': OPERATORS_VALUES.put(OPERATORS.charAt(i), 2);
                    break;
            }
        }
    }

    /**
     * Retrieve operator precendences
     * @return
     */
    public HashMap<Character, Integer> getOperatorWeights() {
        return OPERATORS_VALUES;
    }
}
