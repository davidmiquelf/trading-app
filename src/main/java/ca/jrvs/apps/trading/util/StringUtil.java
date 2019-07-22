package ca.jrvs.apps.trading.util;

import java.util.Arrays;

/**
 * Copied from java_apps project.
 */
public class StringUtil {

  public static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }


  public static boolean isEmpty(String... s) {
    return Arrays.stream(s).anyMatch(StringUtil::isEmpty);

  }
}
