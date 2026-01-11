package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final LoggingHandler loggingHandler;
    private final StringWriter stringWriter;
    private final StringReader stringReader;

    public HttpServerChannelInitializer(
            LoggingHandler loggingHandler,
            StringReader stringReader,
            StringWriter stringWriter
    ) {
        this.loggingHandler = loggingHandler;
        this.stringReader = stringReader;
        this.stringWriter = stringWriter;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        final ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("loggingHandler", loggingHandler);
        pipeline.addLast("stringReader", stringReader);
        // TODO insert transformer
        pipeline.addLast("stringWriter", stringWriter);
        pipeline.addLast("end", new LastInChainHandler());
    }
}
