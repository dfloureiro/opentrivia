package com.dfl.trivia.networking;

import com.dfl.trivia.data.category.TriviaCategoryList;
import com.dfl.trivia.data.questions.QuestionsResponse;
import com.dfl.trivia.data.token.ResetSessionTokenResponse;
import com.dfl.trivia.data.token.SessionTokenResponse;
import com.dfl.trivia.enums.Command;
import io.reactivex.Flowable;

/**
 * Created by Loureiro on 13/11/2017.
 */

public class RequestFactory {

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
        .getSessionToken(Command.REQUEST.toString()
            .toLowerCase());
  }

  public Flowable<ResetSessionTokenResponse> resetSessionTokenRequest(String sessionToken) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .resetSessionToken(Command.RESET.toString()
            .toLowerCase(), sessionToken);
  }

  public Flowable<QuestionsResponse> getQuestionsRequest(String token, int amount, String category,
      String difficulty, String questionType) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getQuestions(token, amount, category, difficulty, questionType);
  }
}
