package temp.simplegraph2svg.utils;

import org.testng.Assert;
import org.testng.annotations.Test;
import temp.simplegraph2svg.svg.SvgPoint;

public class SvgUtilsUnitTest {

    @Test
    public void middle() {
        Assert.assertEquals(
                SvgUtils.middle(
                        new SvgPoint(7, 1),
                        new SvgPoint(1, 3)
                ),
                new SvgPoint( 4, 2)
        );
    }

    @Test
    public void length() {
        Assert.assertEquals(
                SvgUtils.length(
                        new SvgPoint(1, 2),
                        new SvgPoint(4, 6)
                ),
                5
        );
    }

    @Test
    public void cos() {
        Assert.assertEquals(
                SvgUtils.cos(
                        new SvgPoint(1, 2),
                        new SvgPoint(4, 6)
                ),
                -0.6
        );
    }

    @Test
    public void sin() {
        Assert.assertEquals(
                SvgUtils.sin(
                        new SvgPoint(10, 11),
                        new SvgPoint(4, 6)
                ),
                0.6401843996644799
        );
    }
}
