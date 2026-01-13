package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandlerContext;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Graph2SvgTransformerUnitTest {

    @Mock
    private ChannelHandlerContext channelHandlerContext;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void decode() {
        final PostRequest postRequest = new PostRequest("<graph>" +
                "<node id=\"node1\" color=\"red\" />" +
                "<node id=\"node2\" color=\"blue\" />" +
                "<edge id=\"edge1\" color=\"yellow\" sourceRef=\"node1\" targetRef=\"node2\" />" +
                "</graph>");
        final List<Object> out = new ArrayList<>();
        new Graph2SvgTransformer().decode(channelHandlerContext, postRequest, out);
        final Object raw = out.getFirst();
        Assert.assertTrue(raw instanceof Response);
        final Response response = (Response) raw;
        Assert.assertEquals( response.contentType(),
                "image/svg+xml;charset=utf-8");
        Assert.assertEquals( response.content(),
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 1000 400\"><style type=\"text/css\">text {font-family: Arial, sans serif; font-size: 8pt; text-anchor:middle; dominant-baseline: middle;}</style><defs><marker id=\"arrow\" markerHeight=\"6\" markerWidth=\"6\" orient=\"auto-start-reverse\" refX=\"10\" refY=\"5\" viewBox=\"0 0 10 10\"><path d=\"M 0 0 L 10 5 L 0 10 Z\" fill=\"gray\"/></marker></defs><circle cx=\"300\" cy=\"100\" fill=\"blue\" r=\"15\" stroke=\"blue\" stroke-width=\"1\"/><text fill=\"black\" x=\"300\" y=\"100\">node2</text><path d=\"M 485 100 Q 400 168, 315 100\" fill=\"none\" marker-end=\"url(#arrow)\" stroke=\"yellow\"/><circle cx=\"500\" cy=\"100\" fill=\"red\" r=\"15\" stroke=\"red\" stroke-width=\"1\"/><text fill=\"black\" x=\"500\" y=\"100\">node1</text></svg>");
    }

}
