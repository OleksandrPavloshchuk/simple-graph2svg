package temp.simplegraph2svg.translate;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import temp.simplegraph2svg.graph.Graph;
import temp.simplegraph2svg.svg.SvgBaseDocumentBuilder;
import temp.simplegraph2svg.svg.SvgPoint;
import temp.simplegraph2svg.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public record TranslateGraph(
        InputStream inputStream,
        OutputStream outputStream
) {
    private static final Logger LOGGER = Logger.getLogger(TranslateGraph.class.getName());

    public void perform() {
        buildDocumentFromInput()
                .flatMap(this::perform)
                .ifPresent(dest -> XmlUtils.writeDocumentToOutput(dest, outputStream));
    }

    private Optional<Document> perform(Document src) {

        final Graph graph = new TranslateXml2GraphJavaObject().apply(src);

        // TODO pass start node id here
        final String rootNodeId = "r";

        final DistributeNodes distributeNodes = new DistributeNodes(graph, rootNodeId).perform();
        final Map<String, SvgPoint> coordinates = new TranslateGraph2SvgCoordinates()
                .apply(distributeNodes.getPositions());

        final Optional<Document> destOpt = new SvgBaseDocumentBuilder(
                distributeNodes.getMaxCol(), distributeNodes.getMaxRow()).build();
        if (destOpt.isPresent()) {
            new TranslateGraph2Svg(destOpt.get(), coordinates, graph).convert();
            return destOpt;
        } else {
            return Optional.empty();
        }
    }

    private Optional<Document> buildDocumentFromInput() {

        try (InputStream in = Files.newInputStream(Path.of("/home/oleksandr/personal/java/simple-graph2svg/samples/graph1.xml"))) {
            return Optional.of(XmlUtils.getDocumentBuilder().parse(in));
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, "Can't read document", ex);
            return Optional.empty();
        }
        // TODO restore it for CLI
        //return getDocumentBuilder().parse(input);
    }


}
