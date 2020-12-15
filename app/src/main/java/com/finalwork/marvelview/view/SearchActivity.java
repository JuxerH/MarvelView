package com.finalwork.marvelview.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.adapter.CharacterAdapter;
import com.finalwork.marvelview.databinding.ActivitySearchBinding;
import com.finalwork.marvelview.viewmodel.SearchViewModel;



public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private ActivitySearchBinding mBinding;
    private CharacterAdapter mSearchAdapter;
    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setSupportActionBar(mBinding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBinding.search.setIconified(false);
//        mBinding.search.setOnQueryTextListener(this);
//        mBinding.search.setOnCloseListener(this);


        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycler.setHasFixedSize(true);

        if (savedInstanceState == null) {
            searchViewModel = new SearchViewModel(this);
        } else {
            searchViewModel = (SearchViewModel) getLastCustomNonConfigurationInstance();
        }

        mBinding.recycler.setAdapter(mSearchAdapter = new CharacterAdapter(R.layout.item_list_search, searchViewModel, null));



        searchViewModel.attachView(searchViewModel);
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        //noinspection ConstantConditions
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return searchViewModel;
    }

    @Override
    protected void onDestroy() {
        searchViewModel.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText)) {
            searchViewModel.searchCharacters(newText);
        } else {
            searchViewModel.loadCharacters();
        }
        return true;
    }

    @Override
    public boolean onClose() {
        String query = mBinding.search.getQuery().toString();
        searchViewModel.searchCharacters(query);
        return true;
    }

    public ActivitySearchBinding getmBinding() {
        return mBinding;
    }

    public void setmBinding(ActivitySearchBinding mBinding) {
        this.mBinding = mBinding;
    }

    public CharacterAdapter getmSearchAdapter() {
        return mSearchAdapter;
    }

    public void setmSearchAdapter(CharacterAdapter mSearchAdapter) {
        this.mSearchAdapter = mSearchAdapter;
    }
}
