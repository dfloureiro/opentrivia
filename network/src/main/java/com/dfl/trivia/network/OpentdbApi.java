package com.dfl.trivia.network;

import com.dfl.trivia.datamodel.category.TriviaCategoryList;
import com.dfl.trivia.datamodel.questions.QuestionsResponse;
import com.dfl.trivia.datamodel.token.ResetSessionTokenResponse;
import com.dfl.trivia.datamodel.token.SessionTokenResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Loureiro on 13/11/2017.
 */

//https://opentdb.com/api_config.php

public interface OpentdbApi {

  @GET("api_category.php") Flowable<TriviaCategoryList> getTriviaCategoryList();

  @GET("api_token.php") Flowable<SessionTokenResponse> getSessionToken(
      @Query("command") String command);

  @GET("api_token.php") Flowable<ResetSessionTokenResponse> resetSessionToken(
      @Query("command") String command, @Query("token") String sessionToken);

  @GET("api.php") Flowable<QuestionsResponse> getQuestions(@Query("token") String token,
      @Query("amount") int amount, @Query("category") String category,
      @Query("difficulty") String difficulty, @Query("type") String questionType,
      @Query("encode") String encode);
}