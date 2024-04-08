package edu.ntnu.idatt2105.rizzlet.properties;

import org.springframework.stereotype.Component;

/**
 * Component containing properties related to quiz creation and validation.
 */
@Component
public final class QuizProperties {

  // Title
  public static final String TITLE_EMPTY = "Title is required";
  public static final int TITLE_LEN_MIN = 4;
  public static final int TITLE_LEN_MAX = 16;
  public static final String TITLE_LEN_MSG = "Title must be between " + TITLE_LEN_MIN + " and " + TITLE_LEN_MAX + " characters";

  // Description
  public static final int DESC_LEN_MAX = 128;
  public static final String DESC_LEN_MSG = "Description must be at most " + DESC_LEN_MAX + " characters";

  // Tag
  public static final String TAG_EMPTY = "Tag cannot be empty";
  public static final int TAG_LEN_MIN = 3;
  public static final int TAG_LEN_MAX = 12;
  public static final String TAG_LEN_MSG = "Tag must be between " + TAG_LEN_MIN + " and " + TAG_LEN_MAX + " characters";
  public static final String TAG_REGEX = "[\\w]+$";
  public static final String TAG_REGEX_MSG = "Tag must contain only letters, digits and underscores";
  public static final int TAG_LIST_LEN_MAX = 16;
  public static final String TAG_LIST_LEN_MSG = "There must be at most " + TAG_LIST_LEN_MAX + " tags";

  // Co-Authors
  public static final int CO_AUTH_LIST_LEN_MAX = 5;
  public static final String CO_AUTH_LIST_LEN_MSG = "There must be at most " + CO_AUTH_LIST_LEN_MAX + " co-authors";

  // Question list
  public static final String QUESTION_TEXT_EMPTY = "Question text is required";
  public static final int QUESTION_TEXT_LEN_MIN = 4;
  public static final int QUESTION_TEXT_LEN_MAX = 64;
  public static final String QUESTION_TEXT_LEN_MSG = "Question text must be between " + QUESTION_TEXT_LEN_MIN + " and " + QUESTION_TEXT_LEN_MAX + " characters";
  public static final String QUESTION_LIST_EMPTY = "Question list cannot be empty";
  public static final int QUESTION_LIST_LEN_MIN = 1;
  public static final int QUESTION_LIST_LEN_MAX = 50;
  public static final String QUESTION_LIST_LEN_MSG = "There must be between " + QUESTION_LIST_LEN_MIN + " and " + QUESTION_LIST_LEN_MAX + " questions";

  // Option
  public static final String OPTION_TEXT_EMPTY = "Option text is required";
  public static final int OPTION_TEXT_LEN_MIN = 1;
  public static final int OPTION_TEXT_LEN_MAX = 16;
  public static final String OPTION_TEXT_LEN_MSG = "Option text must be between " + OPTION_TEXT_LEN_MIN + " and " + OPTION_TEXT_LEN_MAX + " characters";

  // Option list
  public static final String OPTION_LIST_EMPTY = "A list of multiple choice options is required";
  public static final int OPTION_LIST_LEN_MIN = 1;
  public static final int OPTION_LIST_LEN_MAX = 10;
  public static final String OPTION_LIST_LEN_MSG = "There must be between " + OPTION_LIST_LEN_MIN + " and " + OPTION_LIST_LEN_MAX + " multiple choice options";

  // Fill in the blank solution
  public static final String SOLUTION_TEXT_EMPTY = "Solution text is required";
  public static final int SOLUTION_TEXT_LEN_MIN = 1;
  public static final int SOLUTION_TEXT_LEN_MAX = 16;
  public static final String SOLUTION_TEXT_LEN_MSG = "Solution must be between " + SOLUTION_TEXT_LEN_MIN + " and " + SOLUTION_TEXT_LEN_MAX + " characters";
}
