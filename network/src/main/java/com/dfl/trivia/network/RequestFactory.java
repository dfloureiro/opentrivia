package com.dfl.trivia.network;

import com.dfl.trivia.datamodel.category.TriviaCategoryList;
import com.dfl.trivia.datamodel.questions.QuestionsResponse;
import com.dfl.trivia.datamodel.token.ResetSessionTokenResponse;
import com.dfl.trivia.datamodel.token.SessionTokenResponse;
import io.reactivex.Flowable;

/**
 * Created by Loureiro on 13/11/2017.
 *
 * list of request to do on the network to the API
 */

public class RequestFactory {

  private static final String ENCODE = "base64";
  private static final String COMMAND_REQUEST = "request";
  private static final String COMMAND_RESET = "reset";

  public RequestFactory() {
  }

  /**
   * get the list of categories
   *
   * @return list of categories request flowable
   */
  public Flowable<TriviaCategoryList> getTriviaCategoryListRequest() {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getTriviaCategoryList();
  }

  /**
   * get a new session token
   *
   * @return session token request flowable
   */
  public Flowable<SessionTokenResponse> getSessionTokenRequest() {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getSessionToken(COMMAND_REQUEST);
  }

  /**
   * reset the current session token
   *
   * @param sessionToken current session token
   *
   * @return reseted token request flowable
   */
  public Flowable<ResetSessionTokenResponse> resetSessionTokenRequest(String sessionToken) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .resetSessionToken(COMMAND_RESET, sessionToken);
  }

  /**
   * @param token session token
   * @param amount amount of questions
   * @param category questions category
   * @param difficulty questions difficulty
   * @param questionType questions game type
   *
   * @return list of questions request flowable
   */
  public Flowable<QuestionsResponse> getQuestionsRequest(String token, int amount, String category,
      String difficulty, String questionType) {
    return NetworkModule.newInstance()
        .getOpentdbApi()
        .getQuestions(token, amount, category, difficulty, questionType, ENCODE);
  }
}
