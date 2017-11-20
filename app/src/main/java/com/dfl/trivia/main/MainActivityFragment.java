package com.dfl.trivia.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.dfl.trivia.R;
import com.dfl.trivia.main.model.Category;
import com.dfl.trivia.question.QuestionActivity;
import java.util.ArrayList;
import org.parceler.Parcels;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MainContract.View {

  String[] difficulties = new String[] { "Any Difficulty", "Easy", "Medium", "Hard" };
  String[] difficultiesValues = new String[] { null, "easy", "medium", "hard" };
  String[] gameTypes = new String[] { "Any Type", "Multiple Choices", "True/False" };
  String[] gameTypesValues = new String[] { null, "multiple", "boolean" };
  String[] amounts = new String[] { "10", "20", "30", "40", "50" };
  int[] amountsValues = new int[] { 10, 20, 30, 40, 50 };

  private final static String CATEGORIES_POSITION_KEY = "CATEGORIES_POSITION_KEY";
  private final static String DIFFICULTIES_POSITION_KEY = "DIFFICULTIES_POSITION_KEY";
  private final static String GAMETYPE_POSITION_KEY = "GAMETYPE_POSITION_KEY";
  private final static String AMOUNTS_POSITION_KEY = "AMOUNTS_POSITION_KEY";

  @BindView(R.id.loading_spinner) ContentLoadingProgressBar loadingProgressBar;
  @BindView(R.id.categories_spinner) Spinner categoriesSpinner;
  @BindView(R.id.difficulties_spinner) Spinner difficultiesSpinner;
  @BindView(R.id.gametypes_spinner) Spinner gameTypesSpinner;
  @BindView(R.id.amounts_spinner) Spinner amountsSpinner;
  @BindView(R.id.start_button) Button startButton;
  @BindView(R.id.error_invalid_parameter_layout) ConstraintLayout invalidParameterLayout;

  private Unbinder unbinder;
  private MainContract.Presenter presenter;
  private ArrayAdapter<String> categorySpinnerAdapter;

  public MainActivityFragment() {

  }

  public static MainActivityFragment newInstance() {
    return new MainActivityFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    categorySpinnerAdapter =
        new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
            new ArrayList<>());
    categoriesSpinner.setAdapter(categorySpinnerAdapter);

    ArrayAdapter<String> difficultySpinnerAdapter =
        new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
            difficulties);
    difficultiesSpinner.setAdapter(difficultySpinnerAdapter);

    ArrayAdapter<String> gameTypeSpinnerAdapter =
        new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, gameTypes);
    gameTypesSpinner.setAdapter(gameTypeSpinnerAdapter);

    ArrayAdapter<String> amountsSpinnerAdapter =
        new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, amounts);
    amountsSpinner.setAdapter(amountsSpinnerAdapter);

    presenter.subscribe(savedInstanceState != null ? Parcels.unwrap(
        savedInstanceState.getParcelable(MainState.CATEGORY_STATE_KEY)) : null);

    categoriesSpinner.setSelection(
        savedInstanceState != null ? savedInstanceState.getInt(CATEGORIES_POSITION_KEY) : 0);
    difficultiesSpinner.setSelection(
        savedInstanceState != null ? savedInstanceState.getInt(DIFFICULTIES_POSITION_KEY) : 0);
    gameTypesSpinner.setSelection(
        savedInstanceState != null ? savedInstanceState.getInt(GAMETYPE_POSITION_KEY) : 0);
    amountsSpinner.setSelection(
        savedInstanceState != null ? savedInstanceState.getInt(GAMETYPE_POSITION_KEY) : 0);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(MainState.CATEGORY_STATE_KEY, Parcels.wrap(presenter.getState()));
    outState.putInt(CATEGORIES_POSITION_KEY, categoriesSpinner.getSelectedItemPosition());
    outState.putInt(DIFFICULTIES_POSITION_KEY, difficultiesSpinner.getSelectedItemPosition());
    outState.putInt(GAMETYPE_POSITION_KEY, gameTypesSpinner.getSelectedItemPosition());
    outState.putInt(AMOUNTS_POSITION_KEY, amountsSpinner.getSelectedItemPosition());
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.unsubscribe();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void setPresenter(@NonNull MainContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @OnClick(R.id.start_button) void startButtonClick() {
    Intent intent = new Intent(getActivity(), QuestionActivity.class);
    intent.putExtra(QuestionActivity.CATEGORY_ID,
        presenter.getSelectedCategoryId(categoriesSpinner.getSelectedItemPosition()));
    intent.putExtra(QuestionActivity.DIFFICULTY,
        difficultiesValues[difficultiesSpinner.getSelectedItemPosition()]);
    intent.putExtra(QuestionActivity.GAME_TYPE,
        gameTypesValues[gameTypesSpinner.getSelectedItemPosition()]);
    intent.putExtra(QuestionActivity.AMOUNT,
        amountsValues[amountsSpinner.getSelectedItemPosition()]);
    startActivity(intent);
  }

  @Override public void ShowCategory(Category category) {
    categorySpinnerAdapter.add(category.getName());
    categorySpinnerAdapter.notifyDataSetChanged();
  }

  @Override public void finishLoading(boolean hasError) {
    if (hasError) {
      invalidParameterLayout.setVisibility(View.VISIBLE);
    } else {
      categoriesSpinner.setVisibility(View.VISIBLE);
      difficultiesSpinner.setVisibility(View.VISIBLE);
      gameTypesSpinner.setVisibility(View.VISIBLE);
      amountsSpinner.setVisibility(View.VISIBLE);
      startButton.setVisibility(View.VISIBLE);
    }
    loadingProgressBar.setVisibility(View.GONE);
  }
}
