package edu.ntnu.idatt2105.trivium.config;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.FillTheBlankQuestionDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.question.MultipleChoiceQuestionDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.question.TrueFalseQuestionDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.question.FillTheBlankQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.MultipleChoiceQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.question.TrueFalseQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.tag.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the ModelMapper bean.
 */
@Configuration
public class ModelMapperConfig {

  /**
   * Configures and provides the ModelMapper bean.
   *
   * @return ModelMapper bean configured with custom mappings.
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    // Tag
    modelMapper.createTypeMap(String.class, Tag.class).setConverter(
        context -> Tag.builder().name(context.getSource().toLowerCase())
            .build());

    modelMapper.createTypeMap(Tag.class, String.class)
        .setConverter(context -> context.getSource().getName().toLowerCase());

    // Fill in the blank question
    modelMapper.createTypeMap(FillTheBlankQuestionDTO.class, Question.class)
        .setConverter(context -> {
          FillTheBlankQuestionDTO dto = context.getSource();
          return FillTheBlankQuestion.builder().type(Question.Type.valueOf(dto.getType().toUpperCase()))
              .text(dto.getText()).solution(dto.getSolution()).build();
        });

    modelMapper.createTypeMap(FillTheBlankQuestion.class, QuestionDTO.class)
        .setConverter(context -> {
          FillTheBlankQuestion dto = context.getSource();
          return FillTheBlankQuestionDTO.builder().id(dto.getId()).type(dto.getType().name()).text(dto.getText())
              .solution(dto.getSolution()).build();
        });

    // Multiple choice question
    modelMapper.createTypeMap(MultipleChoiceQuestionDTO.class, Question.class)
        .setConverter(context -> {
          MultipleChoiceQuestionDTO dto = context.getSource();
          return MultipleChoiceQuestion.builder().type(Question.Type.valueOf(dto.getType().toUpperCase())).text(dto.getText())
              .options(dto.getOptions().stream().map(dto1 -> MultipleChoiceQuestion.Option.builder()
                  .optionText(dto1.getOptionText()).correct(dto1.isCorrect()).build()).toList()).build();
        });

    modelMapper.createTypeMap(MultipleChoiceQuestion.class, QuestionDTO.class)
        .setConverter(context -> {
          MultipleChoiceQuestion dto = context.getSource();
          return MultipleChoiceQuestionDTO.builder().id(dto.getId()).type(dto.getType().name()).text(dto.getText())
              .options(dto.getOptions().stream().map(option -> MultipleChoiceQuestionDTO.OptionDTO
                  .builder().id(option.getId()).optionText(option.getOptionText()).correct(option.isCorrect()).build())
                  .toList()).build();
        });

    // True false question
    modelMapper.createTypeMap(TrueFalseQuestionDTO.class, Question.class)
        .setConverter(context -> {
          TrueFalseQuestionDTO dto = context.getSource();
          return TrueFalseQuestion.builder().type(Question.Type.valueOf(dto.getType().toUpperCase())).text(dto.getText()).isTrue(dto.isTrue()).build();
        });

    modelMapper.createTypeMap(TrueFalseQuestion.class, QuestionDTO.class)
        .setConverter(context -> {
          TrueFalseQuestion dto = context.getSource();
          return TrueFalseQuestionDTO.builder().id(dto.getId()).type(dto.getType().name()).text(dto.getText())
              .isTrue(dto.isTrue()).build();
        });

    return modelMapper;
  }
}
