package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:validation.properties")
public final class QuizProperties {

  // Title
  @Value("${quiz.title.empty}")
  public String TITLE_EMPTY;
  @Value("${quiz.title.len.min}")
  public int TITLE_LEN_MIN;
  @Value("${quiz.title.len.max}")
  public int TITLE_LEN_MAX;
  @Value("${quiz.title.len.message}")
  public String TITLE_LEN_MSG;

  // Description
  @Value("${quiz.title.len.max}")
  public int DESC_LEN_MAX;
  @Value("${quiz.desc.len.message}")
  public String DESC_LEN_MSG;

  // Tag
  @Value("${quiz.tag.empty}")
  public String TAG_EMPTY;
  @Value("${quiz.tag.len.min}")
  public int TAG_LEN_MIN;
  @Value("${quiz.tag.len.max}")
  public int TAG_LEN_MAX;
  @Value("${quiz.tag.len.message}")
  public String TAG_LEN_MSG;
  @Value("${quiz.tag.regex}")
  public String TAG_REGEX;
  @Value("${quiz.tag.regex.message}")
  public String TAG_REGEX_MSG;
  @Value("${quiz.tag.list.len.max}")
  public int TAG_LIST_LEN_MAX;
  @Value("${quiz.tag.list.len.message}")
  public String TAG_LIST_LEN_MSG;

  // Question list
  @Value("${question.text.empty}")
  public String QUESTION_TEXT_EMPTY;
  @Value("${question.text.len.min}")
  public int QUESTION_TEXT_LEN_MIN;
  @Value("${question.text.len.max}")
  public int QUESTION_TEXT_LEN_MAX;
  @Value("${question.text.len.message}")
  public String QUESTION_TEXT_LEN_MSG;
  @Value("${question.list.empty}")
  public String QUESTION_LIST_EMPTY;
  @Value("${question.list.len.min}")
  public int QUESTION_LIST_LEN_MIN;
  @Value("${question.list.len.max}")
  public int QUESTION_LIST_LEN_MAX;
  @Value("${question.list.len.message}")
  public String QUESTION_LIST_LEN_MSG;
}
