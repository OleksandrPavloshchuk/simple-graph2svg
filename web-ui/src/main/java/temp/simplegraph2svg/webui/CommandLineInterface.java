package temp.simplegraph2svg.webui;

import io.netty.bootstrap.ServerBootstrap;
import temp.simplegraph2svg.webui.server.*;

import java.util.logging.Logger;

public class CommandLineInterface {
    private static final Logger log = Logger.getLogger(CommandLineInterface.class.getName());

    public static void main(String[] args) {
        // Dependency injection:
        final LoggingHandler loggingHandler = new LoggingHandler();
        final StringWriter stringWriter = new StringWriter();
        final StringReader stringReader = new StringReader();

        final HttpServerChannelInitializer httpServerChannelInitializer = new HttpServerChannelInitializer(
                loggingHandler,
                stringReader,
                stringWriter
        );

        new HttpServer(
                httpServerChannelInitializer,
                new ServerBootstrap()
        ).start(8080);

    }
}
