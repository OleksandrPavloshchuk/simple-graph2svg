package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final LoggingHandler loggingHandler;
    final HttpMethodDispatcher httpMethodDispatcher;
    private final ResponseWriter responseWriter;
    private final Graph2SvgTransformer graph2SvgTransformer;
    private final StaticHtmlRetriever staticHtmlRetriever;

    public HttpServerChannelInitializer(
            LoggingHandler loggingHandler,
            HttpMethodDispatcher httpMethodDispatcher,
            Graph2SvgTransformer graph2SvgTransformer,
            StaticHtmlRetriever staticHtmlRetriever,
            ResponseWriter responseWriter
    ) {
        this.loggingHandler = loggingHandler;
        this.httpMethodDispatcher = httpMethodDispatcher;
        this.responseWriter = responseWriter;
        this.graph2SvgTransformer = graph2SvgTransformer;
        this.staticHtmlRetriever = staticHtmlRetriever;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        final ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("loggingHandler", loggingHandler);
        pipeline.addLast("httpMethodDispatcher", httpMethodDispatcher);
        pipeline.addLast("transformer", graph2SvgTransformer);
        pipeline.addLast("htmlRetriever", staticHtmlRetriever);
        pipeline.addLast("stringWriter", responseWriter);
        pipeline.addLast("end", new LastInChainHandler());
    }
}
