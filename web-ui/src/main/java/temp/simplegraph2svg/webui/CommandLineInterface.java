package temp.simplegraph2svg.webui;

import io.netty.bootstrap.ServerBootstrap;
import temp.simplegraph2svg.webui.server.*;

public class CommandLineInterface {

    public static void main(String[] args) {
        // Dependency injection:
        final LoggingHandler loggingHandler = new LoggingHandler();
        final HttpMethodDispatcher httpMethodDispatcher = new HttpMethodDispatcher();
        final Graph2SvgTransformer graph2SvgTransformer = new Graph2SvgTransformer();
        final StaticHtmlRetriever staticHtmlRetriever = new StaticHtmlRetriever();
        final ResponseWriter responseWriter = new ResponseWriter();

        final HttpServerChannelInitializer httpServerChannelInitializer = new HttpServerChannelInitializer(
                loggingHandler,
                httpMethodDispatcher,
                graph2SvgTransformer,
                staticHtmlRetriever,
                responseWriter
        );

        new HttpServer(
                httpServerChannelInitializer,
                new ServerBootstrap()
        ).start(8080);

    }
}
