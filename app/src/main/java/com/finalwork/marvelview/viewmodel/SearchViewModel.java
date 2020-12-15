package com.finalwork.marvelview.viewmodel;

import android.text.TextUtils;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import com.finalwork.marvelview.adapter.ArrayAdapter;
import com.finalwork.marvelview.adapter.CharacterAdapter;
import com.finalwork.marvelview.api.MarvelApi;
import com.finalwork.marvelview.api.MarvelCallback;
import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.util.DataParser;
import com.finalwork.marvelview.api.util.StringUtils;
import com.finalwork.marvelview.databinding.ItemListSearchBinding;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.view.DetailActivity;
import com.finalwork.marvelview.view.SearchActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends AbsViewModel<SearchVM.View> implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, SearchVM.Actions, SearchVM.View,
        CharacterAdapter.OnItemClickListener<CharacterVO, CharacterAdapter.ViewHolder, ItemListSearchBinding> {

    private final MarvelApi mMarvelApi;
    private final List<CharacterVO> mEntries;
    private SearchActivity searchActivity;

    public SearchViewModel(SearchActivity searchActivity) {
        mMarvelApi = MarvelApi.getInstance();
        mEntries = new ArrayList<>();
        this.searchActivity = searchActivity;

        searchActivity.getmBinding().search.setOnCloseListener(this);
        searchActivity.getmBinding().search.setOnQueryTextListener(this);
    }

    @Override
    public void attachView(@NonNull SearchVM.View view) {
        super.attachView(view);
        if (mEntries.isEmpty()) {
            loadCharacters();
        } else {
            mView.showResult(mEntries);
            mView.stopProgress();
        }
    }

    @Override
    public void loadCharacters() {
        mView.showProgress();
        mMarvelApi.listCharacters(0, new MarvelCallback<CharacterDataWrapper>() {

            @Override
            public void onResult(CharacterDataWrapper data) {
                MarvelResult<CharacterVO> result = DataParser.parse(data);
                mEntries.clear();
                mEntries.addAll(result.getmEntries());
                if (!isViewAttached()) {
                    return;
                }
                mView.showResult(mEntries);
                mView.stopProgress();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
                mView.stopProgress();
            }
        });
    }

    @Override
    public void searchCharacters(String query) {
        mView.showProgress();
        mMarvelApi.searchCharacters(query, new MarvelCallback<CharacterDataWrapper>() {

            @Override
            public void onResult(CharacterDataWrapper data) {
                MarvelResult<CharacterVO> result = DataParser.parse(data);
                mEntries.clear();
                mEntries.addAll(result.getmEntries());
                if (!isViewAttached()) {
                    return;
                }
                mView.showResult(mEntries);
                mView.stopProgress();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
                mView.stopProgress();
            }
        });
    }

    @Override
    public void characterClick(@NonNull View heroView, @NonNull CharacterVO character) {
        mView.openCharacter(heroView, character);
    }

    @Override
    public void onItemClick(ArrayAdapter<CharacterVO, CharacterAdapter.ViewHolder> adapter, ItemListSearchBinding binding, int position) {
        characterClick(binding.image, adapter.getItem(position));
    }

    @Override
    public void showProgress() {
        searchActivity.getmSearchAdapter().setHasMore(true);
    }

    @Override
    public void stopProgress() {
        searchActivity.getmSearchAdapter().setHasMore(false);
    }

    @Override
    public void showResult(List<CharacterVO> entries) {
        searchActivity.getmSearchAdapter().setItems(entries);
    }

    @Override
    public void showError(Throwable e) {
        Snackbar.make(searchActivity.getmBinding().toolbar, StringUtils.getApiErrorMessage(searchActivity, e), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openCharacter(@NonNull View heroView, @NonNull CharacterVO character) {
        DetailActivity.start(searchActivity, heroView, character);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText)) {
            searchCharacters(newText);
        } else {
            loadCharacters();
        }
        return true;
    }

    @Override
    public boolean onClose() {
        String query = searchActivity.getmBinding().search.getQuery().toString();
        searchCharacters(query);
        return true;
    }


}
