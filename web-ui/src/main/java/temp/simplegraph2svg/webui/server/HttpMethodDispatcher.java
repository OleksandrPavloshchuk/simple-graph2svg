package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@ChannelHandler.Sharable
public class HttpMethodDispatcher extends MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest request, List<Object> out) throws ParserConfigurationException, IOException, SAXException {
        out.add(toTypedRequest(request));
    }

    private Object toTypedRequest(FullHttpRequest request) {
        return switch (request.method().name().toUpperCase()) {
            case "POST" -> new PostRequest(request.content().toString(CharsetUtil.UTF_8));
            case "GET" -> new GetRequest();
            default -> throw new RuntimeException("Unexpected method: " + request.method().name());
        };
    }

}
