package temp.simplegraph2svg.translate;

public class GraphTranslationException extends RuntimeException {
    public GraphTranslationException(String reason) {
        super("Can't translate graph: " + reason);
    }
    public GraphTranslationException(Throwable cause) {
        this(cause.getMessage());
    }
}
