/*
    Programmers: Alvin Jay Cosare, Gabriel Lagmay, Raphael Tugasan
    Exercise 1: Arithmetic Evaluation, Date Due: February 20, 2015
*/

/**
 * Created by alvinjay on 2/7/15.
 * Interface for generating output lines
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
