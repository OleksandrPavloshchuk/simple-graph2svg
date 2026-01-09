package temp.simplegraph2svg;

import temp.simplegraph2svg.translate.TranslateGraph;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Assumes valid graph XML.
 */
public class CommandLineInterface {
    private static final Logger LOGGER = Logger.getLogger(CommandLineInterface.class.getName());

    public static void main(String[] args) {
        try {
            new TranslateGraph(System.in,System.out).perform();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Translation error", ex);
        }
    }
}
