package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import java.util.logging.Level;

public class LoggingHandler extends ChannelDuplexHandler {
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LoggingHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest request) {
            String body = request.content().toString(CharsetUtil.UTF_8);
            log.log(Level.WARNING,
                    ">>> Incoming Request" +
                            "\nMethod: " + request.method() +
                            "\nURI: " + request.uri() +
                            "\nHeaders: " + request.headers() +
                            "\nBody: " + body);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof FullHttpResponse response) {
            String body = response.content().toString(CharsetUtil.UTF_8);
            log.log(Level.WARNING,
                    "<<< Outgoing Response" +
                            "\nStatus: " + response.status() +
                            "\nHeaders: " + response.headers() +
                            "\nBody: " + body);
        }
        super.write(ctx, msg, promise);
    }
}
