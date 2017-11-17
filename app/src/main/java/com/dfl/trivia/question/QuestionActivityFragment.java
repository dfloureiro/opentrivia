package com.dfl.trivia.question;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.dfl.trivia.R;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionActivityFragment extends Fragment implements QuestionContract.View {

  @BindView(R.id.categorie_title) TextView categorieTitle;
  @BindView(R.id.difficulty_title) TextView difficultyTitle;
  @BindView(R.id.question_text) TextView questionText;
  @BindView(R.id.multiple_type_layout) LinearLayout multipleTypeLayout;
  @BindView(R.id.answer_a_button) Button answerAButton;
  @BindView(R.id.answer_b_button) Button answerBButton;
  @BindView(R.id.answer_c_button) Button answerCButton;
  @BindView(R.id.answer_d_button) Button answerDButton;
  @BindView(R.id.boolean_type_layout) LinearLayout booleanTypeLayout;
  @BindView(R.id.true_button) Button trueButton;
  @BindView(R.id.false_button) Button falseButton;

  private Unbinder unbinder;
  private QuestionContract.Presenter presenter;

  public static QuestionActivityFragment newInstance() {
    return new QuestionActivityFragment();
  }

  public QuestionActivityFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_question, container, false);
    unbinder = ButterKnife.bind(this, view);
    presenter.subscribe(null);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    View.OnClickListener buttonOnClickListener = v -> {
      multipleTypeLayout.setVisibility(View.GONE);
      booleanTypeLayout.setVisibility(View.GONE);
      Button selectedButton = (Button) v;
      presenter.answerQuestion(selectedButton.getText()
          .toString());
    };

    answerAButton.setOnClickListener(buttonOnClickListener);
    answerBButton.setOnClickListener(buttonOnClickListener);
    answerCButton.setOnClickListener(buttonOnClickListener);
    answerDButton.setOnClickListener(buttonOnClickListener);
    trueButton.setOnClickListener(buttonOnClickListener);
    falseButton.setOnClickListener(buttonOnClickListener);
  }

  @Override public void setPresenter(QuestionContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override public void onPause() {
    super.onPause();
    presenter.unsubscribe();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void setCategoryTitle(String text) {
    categorieTitle.setText(text);
  }

  @Override public void setDifficultyTitle(String text) {
    difficultyTitle.setText(text);
  }

  @Override public void setQuestionText(String text) {
    questionText.setText(text);
  }

  @Override public void setAnswers(boolean isMultiple, List<String> answers) {
    if (isMultiple) {
      multipleTypeLayout.setVisibility(View.VISIBLE);
      answerAButton.setText(answers.get(0));
      answerBButton.setText(answers.get(1));
      answerCButton.setText(answers.get(2));
      answerDButton.setText(answers.get(3));
    } else {
      booleanTypeLayout.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showResponse(String correctAnswer) {
    Toast.makeText(getActivity(), correctAnswer, Toast.LENGTH_SHORT)
        .show();
  }

  @Override public void showResults(int numberOfCorrectAnswers, int totalNumberOfAnswers) {
    Toast.makeText(getActivity(),
        "You got " + numberOfCorrectAnswers + " out of " + totalNumberOfAnswers, Toast.LENGTH_LONG)
        .show();
    getActivity().finish();
  }
}
