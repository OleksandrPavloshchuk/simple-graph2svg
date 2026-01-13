package temp.simplegraph2svg.webui.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

public class HttpMethodDispatcherUnitTest {

    @Mock
    private ChannelHandlerContext channelHandlerContext;

    @Mock
    private FullHttpRequest request;

    @Mock
    private ByteBuf byteBuf;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void decodePost() {
        final List<Object> out = new ArrayList<>();
        doReturn(HttpMethod.POST).when(request).method();
        doReturn(byteBuf).when(request).content();
        doReturn("the content").when(byteBuf).toString(any());
        new HttpMethodDispatcher().decode(channelHandlerContext, request, out);
        final Object raw = out.getFirst();
        Assert.assertTrue(raw instanceof PostRequest);
        final PostRequest postRequest = (PostRequest) raw;
        Assert.assertEquals(postRequest.body(), "the content");
    }

    @Test
    public void decodeGet() {
        final List<Object> out = new ArrayList<>();
        doReturn(HttpMethod.GET).when(request).method();
        new HttpMethodDispatcher().decode(channelHandlerContext, request, out);
        final Object raw = out.getFirst();
        Assert.assertTrue(raw instanceof GetRequest);
    }

    @Test
    public void decodePut() {
        final List<Object> out = new ArrayList<>();
        doReturn(HttpMethod.PUT).when(request).method();
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new HttpMethodDispatcher().decode(channelHandlerContext, request, out));
    }
}
