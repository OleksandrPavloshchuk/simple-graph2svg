package temp.simplegraph2svg.webui.server;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName) {
        super("Resource not found: /" + resourceName);
    }
}
