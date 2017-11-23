package com.dfl.trivia.network;

import com.dfl.trivia.datamodel.category.TriviaCategoryList;
import com.dfl.trivia.datamodel.questions.QuestionsResponse;
import com.dfl.trivia.datamodel.token.ResetSessionTokenResponse;
import com.dfl.trivia.datamodel.token.SessionTokenResponse;
import io.reactivex.Flowable;

/**
 * Created by Loureiro on 13/11/2017.
 */

public class RequestFactory {

  private static final String ENCODE = "base64";
  private static final String COMMAND_REQUEST = "request";
  private static final String COMMAND_RESET = "reset";

  public RequestFactory() {
  }

  public Flowable<TriviaCategoryList> getTriviaCategoryListRequest() {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getTriviaCategoryList();
  }

  public Flowable<SessionTokenResponse> getSessionTokenRequest() {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getSessionToken(COMMAND_REQUEST);
  }

  public Flowable<ResetSessionTokenResponse> resetSessionTokenRequest(String sessionToken) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .resetSessionToken(COMMAND_RESET, sessionToken);
  }

  public Flowable<QuestionsResponse> getQuestionsRequest(String token, int amount, String category,
      String difficulty, String questionType) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getQuestions(token, amount, category, difficulty, questionType, ENCODE);
  }
}
