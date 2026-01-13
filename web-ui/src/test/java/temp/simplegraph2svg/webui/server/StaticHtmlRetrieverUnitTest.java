package temp.simplegraph2svg.webui.server;

import io.netty.channel.ChannelHandlerContext;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class StaticHtmlRetrieverUnitTest {

    @Mock
    private ChannelHandlerContext channelHandlerContext;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void decode() {
        final List<Object> out = new ArrayList<>();
        new StaticHtmlRetriever().decode(channelHandlerContext, new GetRequest(), out);
        final Object raw = out.getFirst();
        Assert.assertTrue(raw instanceof Response);
        final Response response = (Response) raw;
        Assert.assertEquals( response.contentType(),
                "text/html;charset=utf-8");
        Assert.assertEquals( prepare(response.content()), prepare(EXPECTED_HTML));
    }

    private String prepare(String src) {
        return src
                .replace(" ", "")
                .replace("\t", "")
                .replace("\n", "")
                .replace("\r", "");
    }

    private static final String EXPECTED_HTML =
"""
<!DOCTYPE html>

<html lang="en">
<head>
    <title>simple-graph2svg web console</title>
</head>
<body>
<h1>simple-graph2svg web console</h1>

<label for="input">Graph:</label>
<textarea id="input" cols="40" rows="5">
</textarea>
<button onclick="testContentOnServer()">Test</button>
<hr/>
<div id="error" style="display: none"></div>
<img id="result" />
</body>
<script type="text/javascript">
    const testContentOnServer = () => {
        fetch("/", {
            method: "POST",
            headers: { "Content-Type": "text/xml" },
            body: document.getElementById("input").value
        })
            .then(r => {
                if (!r.ok) {
                    return r.text().then(msg => {
                        throw new Error(msg || `HTTP error ${r.status}`);
                    });
                }
                return r.text(); // успішна відповідь → SVG
            })
            .then(svg => {
                document.getElementById("error").setAttribute("style", "display: none");
                document.getElementById("result").src =
                    "data:image/svg+xml;charset=utf-8," + encodeURIComponent(svg);
            })
            .catch(e => {
                let elem = document.getElementById("error");
                elem.setAttribute("style", "display: inline; color: maroon");
                elem.textContent = '';
                elem.appendChild(document.createTextNode(e.message));
            });
    }
</script>
</html>
""";

}


