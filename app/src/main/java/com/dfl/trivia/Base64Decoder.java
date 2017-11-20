package com.dfl.trivia;

import android.util.Base64;
import com.dfl.trivia.data.questions.Result;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loureiro on 18/11/2017.
 */

public class Base64Decoder {

  public static List<Result> decodeResults(List<Result> results)
      throws UnsupportedEncodingException {
    ArrayList<Result> resultList = new ArrayList<>();
    for (Result result : results) {
      result.setCategory(decodeString(result.getCategory()));
      result.setCorrectAnswer(decodeString(result.getCorrectAnswer()));
      result.setDifficulty(decodeString(result.getDifficulty()));
      result.setQuestion(decodeString(result.getQuestion()));
      result.setType(decodeString(result.getType()));
      ArrayList<String> incorrectAnswers = new ArrayList<>();
      for (String answer : result.getIncorrectAnswers()) {
        incorrectAnswers.add(decodeString(answer));
      }
      result.setIncorrectAnswers(incorrectAnswers);
      resultList.add(result);
    }
    return resultList;
  }

  private static String decodeString(String base64) throws UnsupportedEncodingException {
    byte[] data = Base64.decode(base64, Base64.DEFAULT);
    return new String(data, "UTF-8");
  }
}
