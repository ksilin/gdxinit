package info.silin.gdxinit.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CollectionFilter {

	private CollectionFilter() {
	}

	public static <T> Collection<T> filter(final Collection<T> subject,
			final Predicate<T> predicate) {
		return doFilter(subject, predicate);
	}

	public static <T> List<T> filter(final List<T> subject,
			final Predicate<T> predicate) {
		return (List<T>) doFilter(subject, predicate);
	}

	private static <T> Collection<T> doFilter(final Collection<T> filtered,
			final Predicate<T> predicate) {
		final Collection<T> result = new ArrayList<T>();
		for (final T element : filtered) {
			if (predicate.accept(element)) {
				result.add(element);
			}
		}
		return result;
	}

	public static <T, S> List<S> filterToType(final Collection<T> subject,
			final Class<S> klazz) {

		final Predicate<T> acceptType = new Predicate<T>() {

			@Override
			public boolean accept(final T type) {
				return type.getClass().equals(klazz);
			}
		};
		return doFilterToType(subject, acceptType);
	}

	public static <T, S> List<S> filterToType(final Collection<T> subject,
			final Predicate<T> predicate) {
		return doFilterToType(subject, predicate);
	}

	@SuppressWarnings("unchecked")
	private static <S, T> List<S> doFilterToType(final Collection<T> subject,
			final Predicate<T> predicate) {
		final List<S> result = new ArrayList<S>();
		for (final T element : subject) {
			if (predicate.accept(element)) {
				result.add((S) element);
			}
		}
		return result;
	}

}
