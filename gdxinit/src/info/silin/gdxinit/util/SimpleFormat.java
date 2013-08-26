package info.silin.gdxinit.util;

public final class SimpleFormat {

	public static String format(double num) {
		double floored = Math.floor(num * 100);
		return Double.toString(floored / 100);
	}
}
