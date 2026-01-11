package temp.simplegraph2svg;

import temp.simplegraph2svg.translate.TranslateGraph;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Assumes valid graph XML.
 */
public class CommandLineInterface {
    private static final Logger LOGGER = Logger.getLogger(CommandLineInterface.class.getName());

    public static void main(String[] args) {
        try {
            InputStream in = Files.newInputStream(Path.of("/home/oleksandr/personal/java/simple-graph2svg/samples/graph2.xml"));
            new TranslateGraph(in,System.out).perform();
            // TODO (2026/01/11) uncomment for production
            // new TranslateGraph(System.in,System.out).perform();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Translation error", ex);
        }
    }
}
