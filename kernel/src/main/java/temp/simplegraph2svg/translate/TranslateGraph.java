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
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public record TranslateGraph(
        InputStream inputStream,
        OutputStream outputStream
) {
    private static final Logger LOGGER = Logger.getLogger(TranslateGraph.class.getName());

    public void perform() throws GraphTranslationException {
        buildDocumentFromInput()
                .flatMap(this::perform)
                .ifPresent(dest -> XmlUtils.writeDocumentToOutput(dest, outputStream));
    }

    private Optional<Document> perform(Document src) {

        final Graph graph = new TranslateXml2GraphJavaObject().apply(src);

        final DistributeNodes distributeNodes = new DistributeNodes(graph).perform();
        final Map<String, SvgPoint> coordinates = new TranslateGraph2SvgCoordinates()
                .apply(distributeNodes.getPositions());

        return new SvgBaseDocumentBuilder(distributeNodes.getMaxCol(), distributeNodes.getMaxRow())
                .build()
                .stream()
                .peek(svgDoc -> new TranslateGraph2Svg(svgDoc, coordinates, graph).convert())
                .findFirst();
    }

    private Optional<Document> buildDocumentFromInput() {
        try {
            return Optional.of(XmlUtils.getDocumentBuilder().parse(inputStream));
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, "Can't read document", ex);
            throw new GraphTranslationException(ex);
        }
    }

}
