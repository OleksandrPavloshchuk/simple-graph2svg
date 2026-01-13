package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class ResponseWriterUnitTest {

    @Mock
    private ChannelHandlerContext channelHandlerContext;

    @Mock
    private ChannelFuture channelFuture;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void decode() throws Exception {
        doReturn(channelFuture).when(channelHandlerContext).writeAndFlush(any());
        final Response response = new Response("content type", "content itself");
        new ResponseWriter().channelRead0(channelHandlerContext, response);
        ArgumentCaptor<FullHttpResponse> argCaptor = ArgumentCaptor.forClass(FullHttpResponse.class);
        verify(channelHandlerContext).writeAndFlush(argCaptor.capture());
        final FullHttpResponse httpResponse = argCaptor.getValue();
        Assert.assertEquals(httpResponse.status().code(), HttpResponseStatus.OK.code());
        Assert.assertEquals(httpResponse.headers().get(HttpHeaderNames.CONTENT_LENGTH), "14");
        Assert.assertEquals(httpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE), response.contentType());
        Assert.assertEquals(httpResponse.content().toString(CharsetUtil.UTF_8), "content itself");

    }

}
