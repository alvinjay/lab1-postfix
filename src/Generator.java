/**
 * Created by alvinjay on 2/7/15.
 */
public interface Generator {
    /**
     * Generates part of the output lines for each input line
     * One outline for each:
     *  -Given
     *  -Postfix
     *  -Postfix Evaluation
     * @param outputLine - string to be appended
     * @param index - index of the input line being processed
     */
    public void generateOutputLine(String outputLine, int index);
}
