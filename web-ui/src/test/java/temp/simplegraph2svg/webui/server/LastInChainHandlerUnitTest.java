package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import temp.simplegraph2svg.translate.GraphTranslationException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class LastInChainHandlerUnitTest {

    @Mock
    private ChannelHandlerContext channelHandlerContext;

    @Mock
    private ChannelFuture channelFuture;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public Object[][] exceptionAndHttpResponseStatuses() {
        return new Object[][] {
                new Object[]{new Exception("some exception"), HttpResponseStatus.INTERNAL_SERVER_ERROR},
                new Object[]{new DecoderException(
                        new ResourceNotFoundException("no resource")
                ), HttpResponseStatus.NOT_FOUND},
                new Object[]{new DecoderException(
                        new IllegalArgumentException("no resource")
                ), HttpResponseStatus.BAD_REQUEST},
                new Object[]{new DecoderException(
                        new GraphTranslationException("")
                ), HttpResponseStatus.BAD_REQUEST},
                new Object[]{new DecoderException(
                        new Exception("??")
                ), HttpResponseStatus.INTERNAL_SERVER_ERROR}
        };
    }

    @Test(dataProvider = "exceptionAndHttpResponseStatuses")
    public void exceptionCaughtCustomException(Exception cause, HttpResponseStatus httpResponseStatus) {
        doReturn(channelFuture).when(channelHandlerContext).writeAndFlush(any());
        new LastInChainHandler().exceptionCaught(channelHandlerContext, cause);
        ArgumentCaptor<FullHttpResponse> responseCaptor = ArgumentCaptor.forClass(FullHttpResponse.class);
        verify(channelHandlerContext).writeAndFlush(responseCaptor.capture());
        final FullHttpResponse response = responseCaptor.getValue();
        Assert.assertEquals(response.headers().get(HttpHeaderNames.CONTENT_TYPE), "text/plain; charset=UTF-8");
        Assert.assertEquals(response.status(), httpResponseStatus);

    }

}
