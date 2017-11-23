package com.dfl.trivia;

import android.util.Base64;
import com.dfl.trivia.datamodel.questions.Result;
import com.dfl.trivia.question.model.Question;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loureiro on 18/11/2017.
 */

public class Base64Decoder {

  private static final String CHARSET_NAME_TYPE = "UTF-8";

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

  private static String decodeString(String base64) throws UnsupportedEncodingException {
    byte[] data = Base64.decode(base64, Base64.DEFAULT);
    return new String(data, CHARSET_NAME_TYPE);
  }
}
