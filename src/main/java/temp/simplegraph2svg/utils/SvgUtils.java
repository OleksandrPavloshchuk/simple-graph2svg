package temp.simplegraph2svg.utils;

import temp.simplegraph2svg.svg.SvgPoint;

public class SvgUtils {

    private SvgUtils() {}

    public static double length(SvgPoint p1, SvgPoint p2) {
        final double dx = p1.x() - p2.x();
        final double dy = p1.y() - p2.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double sin(SvgPoint p1, SvgPoint p2) {
        final double dy = p1.y() - p2.y();
        return dy / length(p1, p2);
    }

    public static double cos(SvgPoint p1, SvgPoint p2) {
        final double dx = p1.x() - p2.x();
        return dx / length(p1, p2);
    }
}
