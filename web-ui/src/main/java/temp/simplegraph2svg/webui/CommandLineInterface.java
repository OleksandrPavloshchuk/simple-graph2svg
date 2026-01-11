package temp.simplegraph2svg.webui;

import io.netty.bootstrap.ServerBootstrap;
import temp.simplegraph2svg.webui.server.*;

public class CommandLineInterface {

    public static void main(String[] args) {
        // Dependency injection:
        final LoggingHandler loggingHandler = new LoggingHandler();
        final StringReader stringReader = new StringReader();
        final Graph2SvgTransformer graph2SvgTransformer = new Graph2SvgTransformer();
        final StringWriter stringWriter = new StringWriter();

        final HttpServerChannelInitializer httpServerChannelInitializer = new HttpServerChannelInitializer(
                loggingHandler,
                stringReader,
                graph2SvgTransformer,
                stringWriter
        );

        new HttpServer(
                httpServerChannelInitializer,
                new ServerBootstrap()
        ).start(8080);

    }
}
