package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class StaticHtmlRetriever extends MessageToMessageDecoder<GetRequest> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, GetRequest src, List<Object> out) {
        final String fileName = "index.html";
        try (final InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            if (inputStream != null) {
                final String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                final Response response = new Response(
                        "text/html;charset=utf-8", html);
                out.add(response);
            } else {
                throw new ResourceNotFoundException(fileName);
            }
        } catch (NullPointerException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
