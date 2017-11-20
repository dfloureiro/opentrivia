package com.dfl.trivia.question;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import org.parceler.Parcels;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionActivityFragment extends Fragment implements QuestionContract.View {

  @BindView(R.id.loading_spinner) ContentLoadingProgressBar loadingProgressBar;
  @BindView(R.id.categorie_title) TextView categoryTitle;
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
  @BindView(R.id.error_invalid_parameter_layout) ConstraintLayout errorInvalidParameterLayout;
  @BindView(R.id.error_no_results_layout) ConstraintLayout errorNoResultsLayout;

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
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    presenter.subscribe(savedInstanceState != null ? Parcels.unwrap(
        savedInstanceState.getParcelable(QuestionState.QUESTION_STATE_KEY)) : null);

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

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(QuestionState.QUESTION_STATE_KEY, Parcels.wrap(presenter.getState()));
  }

  @Override public void setPresenter(@NonNull QuestionContract.Presenter presenter) {
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
    categoryTitle.setText(text);
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

  @Override public void showResponseCorrect() {
    Toast.makeText(getActivity(), R.string.correct, Toast.LENGTH_SHORT)
        .show();
  }

  @Override public void showResponseIncorrect(String correctAnswer) {
    Toast.makeText(getActivity(), getString(R.string.incorrect_message) + correctAnswer,
        Toast.LENGTH_SHORT)
        .show();
  }

  @Override public void showResults(int numberOfCorrectAnswers, int totalNumberOfAnswers) {
    Toast.makeText(getActivity(), getString(R.string.you_got)
        + numberOfCorrectAnswers
        + getString(R.string.out_of)
        + totalNumberOfAnswers, Toast.LENGTH_LONG)
        .show();
    getActivity().finish();
  }

  @Override public void finishLoading(boolean hasError, boolean noResults) {
    if (hasError) {
      errorInvalidParameterLayout.setVisibility(View.VISIBLE);
    } else if (noResults) {
      errorNoResultsLayout.setVisibility(View.VISIBLE);
    } else {
      categoryTitle.setVisibility(View.VISIBLE);
      difficultyTitle.setVisibility(View.VISIBLE);
      questionText.setVisibility(View.VISIBLE);
      errorInvalidParameterLayout.setVisibility(View.GONE);
    }
    loadingProgressBar.setVisibility(View.GONE);
  }
}
