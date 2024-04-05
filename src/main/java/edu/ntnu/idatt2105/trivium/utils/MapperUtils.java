package edu.ntnu.idatt2105.trivium.utils;

import java.util.List;
import java.util.function.Function;

/**
 * Utility class for mapping lists using provided mapper functions.
 */
public final class MapperUtils {

  /**
   * Maps a list of objects of type S to a list of objects of type T using the provided mapper function.
   *
   * @param source The source list of objects to be mapped.
   * @param mapper The mapper function that converts each element from type S to type T.
   * @param <S> The type of elements in the source list.
   * @param <T> The type of elements in the target list after mapping.
   * @return A new list containing the mapped elements.
   */
  public static <S, T> List<T> mapList(List<S> source, Function<S, T> mapper) {
    return source.stream().map(mapper).toList();
  }
}
