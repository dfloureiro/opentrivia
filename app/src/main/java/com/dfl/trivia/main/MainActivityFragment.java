package com.dfl.trivia.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.dfl.trivia.R;
import com.dfl.trivia.main.model.Category;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MainContract.View {

    private Unbinder unbinder;
    private MainContract.Presenter presenter;

    @BindView(R.id.categories_spinner)
    Spinner categoriesSpinner;

    private CategorySpinnerAdapter spinnerArrayAdapter;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerArrayAdapter = new CategorySpinnerAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<>());
        categoriesSpinner.setAdapter(spinnerArrayAdapter);
        presenter.subscribe(savedInstanceState != null ? Parcels.unwrap(savedInstanceState.getParcelable(MainState.CATEGORY_STATE_KEY)) : null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainState.CATEGORY_STATE_KEY, Parcels.wrap(presenter.getState()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick(R.id.start_button)
    void startButtonClick() {
        // TODO: 15/11/2017 navigate to
        Log.d("", "");
    }

    @Override
    public void ShowCategory(Category category) {
        spinnerArrayAdapter.add(category);
        spinnerArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSelectedCategory(int position) {
        categoriesSpinner.setSelection(position);
    }
}
