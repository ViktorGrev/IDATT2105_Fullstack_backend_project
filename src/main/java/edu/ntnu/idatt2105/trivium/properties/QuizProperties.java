package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.stereotype.Component;

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

  // Question list
  public static final String QUESTION_TEXT_EMPTY = "Question text is required";
  public static final int QUESTION_TEXT_LEN_MIN = 4;
  public static final int QUESTION_TEXT_LEN_MAX = 64;
  public static final String QUESTION_TEXT_LEN_MSG = "Question text must be between " + QUESTION_TEXT_LEN_MIN + " and " + QUESTION_TEXT_LEN_MAX + " characters";
  public static final String QUESTION_LIST_EMPTY = "Question list cannot be empty";
  public static final int QUESTION_LIST_LEN_MIN = 1;
  public static final int QUESTION_LIST_LEN_MAX = 50;
  public static final String QUESTION_LIST_LEN_MSG = "There must be between " + QUESTION_LIST_LEN_MIN + " and " + QUESTION_LIST_LEN_MAX + "questions";
}
