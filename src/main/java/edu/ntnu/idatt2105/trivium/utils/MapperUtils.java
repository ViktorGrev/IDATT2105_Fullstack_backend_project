package edu.ntnu.idatt2105.trivium.utils;

import java.util.List;
import java.util.function.Function;

public final class MapperUtils {

  public static <S, T> List<T> mapList(List<S> source, Function<S, T> mapper) {
    return source.stream().map(mapper).toList();
  }
}
