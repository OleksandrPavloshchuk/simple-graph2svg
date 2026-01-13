package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class HttpServerChannelInitializerUnitTest {

    @Mock
    private SocketChannel socketChannel;

    @Mock
    private ChannelPipeline channelPipeline;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initChannel() {
        doReturn(channelPipeline).when(socketChannel).pipeline();

        final HttpServerChannelInitializer initializer = new HttpServerChannelInitializer(
                new LoggingHandler(),
                new HttpMethodDispatcher(),
                new Graph2SvgTransformer(),
                new StaticHtmlRetriever(),
                new ResponseWriter()
        );
        initializer.initChannel(socketChannel);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ChannelHandler> handlerCaptor = ArgumentCaptor.forClass(ChannelHandler.class);

        verify(channelPipeline, times(8)).addLast(nameCaptor.capture(), handlerCaptor.capture());

        final List<String> names = nameCaptor.getAllValues();
        final List<ChannelHandler> handlers = handlerCaptor.getAllValues();
        Assert.assertEquals(names,
                List.of("httpServerCodec", "httpObjectAggregator", "loggingHandler", "httpMethodDispatcher",
                        "transformer", "htmlRetriever", "stringWriter", "end"));
        Assert.assertTrue(handlers.get(0) instanceof HttpServerCodec);
        Assert.assertTrue(handlers.get(1) instanceof HttpObjectAggregator);
        Assert.assertTrue(handlers.get(2) instanceof LoggingHandler);
        Assert.assertTrue(handlers.get(3) instanceof HttpMethodDispatcher);
        Assert.assertTrue(handlers.get(4) instanceof Graph2SvgTransformer);
        Assert.assertTrue(handlers.get(5) instanceof StaticHtmlRetriever);
        Assert.assertTrue(handlers.get(6) instanceof ResponseWriter);
        Assert.assertTrue(handlers.get(7) instanceof LastInChainHandler);
    }
}
