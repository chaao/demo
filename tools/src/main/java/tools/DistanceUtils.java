package tools;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lichao
 * 
 */
public class DistanceUtils {

	private static final double EARTH_RADIUS = 6378137;

	public static final int meter = 1;

	public static final int kilometer = 2;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}

	public static String formatDistance(double distance, int unit) {
		if (unit == meter) {
			if (distance >= 100)
				return new DecimalFormat("#.# km").format(distance / 1000);
			else
				return "< 100 m";
		} else {
			if (distance >= 0.1)
				return new DecimalFormat("#.# km").format(distance);
			else
				return "< 100 m";
		}
	}

	public static String formatDistance(String coordinate1, String coordinate2) {

		if (StringUtils.isBlank(coordinate1) || StringUtils.isBlank(coordinate2))
			return null;

		String[] a = coordinate1.split(Constans.SEPARATOR_COMMA);
		String[] b = coordinate2.split(Constans.SEPARATOR_COMMA);

		double lng1 = Double.valueOf(a[0]);
		double lat1 = Double.valueOf(a[1]);

		double lng2 = Double.valueOf(b[0]);
		double lat2 = Double.valueOf(b[1]);

		double distance = getDistance(lng1, lat1, lng2, lat2);
		return formatDistance(distance, meter);
	}
}
