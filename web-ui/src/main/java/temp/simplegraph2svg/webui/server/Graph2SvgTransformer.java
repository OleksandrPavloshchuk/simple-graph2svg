package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import temp.simplegraph2svg.translate.TranslateGraph;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class Graph2SvgTransformer extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String src, List<Object> out) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        new TranslateGraph(inputStream, outputStream).perform();

        out.add(outputStream.toString(StandardCharsets.UTF_8));
    }

}
