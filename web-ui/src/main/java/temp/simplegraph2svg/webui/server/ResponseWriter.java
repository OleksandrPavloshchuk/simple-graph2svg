package temp.simplegraph2svg.webui.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class ResponseWriter extends SimpleChannelInboundHandler<Response> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {

        final FullHttpResponse httpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(response.content(), CharsetUtil.UTF_8)
        );
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, response.contentType());
        httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                String.valueOf(httpResponse.content().readableBytes()));
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }

}