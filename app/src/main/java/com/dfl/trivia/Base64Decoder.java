package com.dfl.trivia;

import android.util.Base64;
import com.dfl.trivia.datamodel.questions.Result;
import com.dfl.trivia.question.model.Question;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loureiro on 18/11/2017.
 *
 * Handles all the decoding of the strings that are encoded in Base64
 */

public class Base64Decoder {

  private static final String CHARSET_NAME_TYPE = "UTF-8";

  /**
   * decodes a list of results encoded in base64 into a list of questions
   *
   * @param results list to decode
   *
   * @return list of decoded questions
   *
   * @throws UnsupportedEncodingException
   */
  public static List<Question> decodeResults(List<Result> results)
      throws UnsupportedEncodingException {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    for (Result result : results) {
      ArrayList<String> incorrectAnswers = new ArrayList<>();
      for (String answer : result.getIncorrectAnswers()) {
        incorrectAnswers.add(decodeString(answer));
      }
      questionArrayList.add(
          new Question(decodeString(result.getCategory()), decodeString(result.getType()),
              decodeString(result.getDifficulty()), decodeString(result.getQuestion()),
              decodeString(result.getCorrectAnswer()), incorrectAnswers));
    }
    return questionArrayList;
  }

  /**
   * decoded string to UTF-8
   *
   * @param base64 string to decode
   *
   * @return decoded string
   *
   * @throws UnsupportedEncodingException
   */
  private static String decodeString(String base64) throws UnsupportedEncodingException {
    byte[] data = Base64.decode(base64, Base64.DEFAULT);
    return new String(data, CHARSET_NAME_TYPE);
  }
}
