package temp.simplegraph2svg.translate;

import org.testng.Assert;
import org.testng.annotations.Test;
import temp.simplegraph2svg.svg.SvgPoint;

import java.util.Map;

public class TranslateGraph2SvgCoordinatesUnitTest {

    @Test
    public void apply() {
        final Map<String, SvgPoint> actual =
                new TranslateGraph2SvgCoordinates().apply(createDistribution());

        Assert.assertEquals( actual, Map.of(
                "1", new SvgPoint(300,100),
                "2", new SvgPoint(500,100),
                "3", new SvgPoint(300,200),
                "4", new SvgPoint(500,200),
                "5", new SvgPoint(400,300),
                "6", new SvgPoint(700,200)
        ) );

    }

    private static Map<String, DistributeNodes.Position> createDistribution() {
        return Map.of(
                "1", new DistributeNodes.Position(0, 0),
                "2", new DistributeNodes.Position(0, 1),
                "3", new DistributeNodes.Position(1, 1),
                "4", new DistributeNodes.Position(1, 2),
                "5", new DistributeNodes.Position(2, 0),
                "6", new DistributeNodes.Position(1, 3)
        );
    }
}
