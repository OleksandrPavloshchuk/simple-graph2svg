package temp.simplegraph2svg.webui.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import temp.simplegraph2svg.translate.GraphTranslationException;

@ChannelHandler.Sharable
public class LastInChainHandler extends ChannelInboundHandlerAdapter {

    final static Logger LOG = LoggerFactory.getLogger(LastInChainHandler.class);

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        LOG.error(cause.getMessage(), cause);
        final HttpResponseStatus responseStatus = getResponseStatus(cause);
        final FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                responseStatus,
                Unpooled.copiedBuffer(cause.getMessage(), CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private HttpResponseStatus getResponseStatus(final Throwable rootCause) {
        return switch (rootCause) {
            case DecoderException ex -> getResponseStatus(ex);
            default -> HttpResponseStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private HttpResponseStatus getResponseStatus(final DecoderException cause) {
        return switch (cause.getCause()) {
            case ResourceNotFoundException ignored -> HttpResponseStatus.NOT_FOUND;
            case IllegalArgumentException ignored -> HttpResponseStatus.BAD_REQUEST;
            case GraphTranslationException ignored -> HttpResponseStatus.BAD_REQUEST;
            default -> HttpResponseStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
