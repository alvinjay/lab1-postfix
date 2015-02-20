import java.util.Stack;

/**
 * Created by alvinjay on 2/20/15.
 * Description:
 *  -includes methods for mathematical operations
 */
public class Operations {
    private Long first, second, tempAnswer;

    /**
     * Does addition operation to two stack operands
     * @param a - stack containing operands
     * @return result (Long)
     */
    public Long additionOp(Stack a) {
        first = Long.parseLong(a.pop().toString());
        second = Long.parseLong(a.pop().toString());
        tempAnswer = second + first;

        return tempAnswer;
    }

    /**
     * Does addition operation to two stack operands
     * @param a - stack containing operands
     * @return result (Long)
     */
    public Long subtractionOp(Stack a) {
        first = Long.parseLong(a.pop().toString());
        second = Long.parseLong(a.pop().toString());
        tempAnswer = second - first;

        return tempAnswer;
    }

    /**
     * Does subtraction operation to two stack operands
     * @param a - stack containing operands
     * @return result (Long)
     */
    public Long multiplicationOp(Stack a) {
        first = Long.parseLong(a.pop().toString());
        second = Long.parseLong(a.pop().toString());
        tempAnswer = second * first;

        return tempAnswer;
    }

    /**
     * Does division operation to two stack operands
     * @param a - stack containing operands
     * @return result (Long)
     */
    public Long divisionOp(Stack a) {
        first = Long.parseLong(a.pop().toString());
        second = Long.parseLong(a.pop().toString());
        tempAnswer = second / first;

        return tempAnswer;
    }

    /**
     * Does modulo operation to two stack operands
     * @param a - stack containing operands
     * @return result (Long)
     */
    public Long moduloOp(Stack a) {
        first = Long.parseLong(a.pop().toString());
        second = Long.parseLong(a.pop().toString());
        tempAnswer = second % first;

        return tempAnswer;
    }

}
