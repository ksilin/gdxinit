package info.silin.gdxinit.util;

/**
 * Implementations of the predicate should return true for subjects that satisfy
 * a condition.
 *
 * @author saxonia
 * @param <T>
 */
public interface Predicate<T> {

	boolean accept(T type);
}
