package com.finalwork.marvelview.viewmodel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;


import androidx.annotation.NonNull;

import com.finalwork.marvelview.adapter.ArrayAdapter;
import com.finalwork.marvelview.adapter.CharacterAdapter;
import com.finalwork.marvelview.api.MarvelApi;
import com.finalwork.marvelview.api.MarvelCallback;
import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.util.DataParser;
import com.finalwork.marvelview.api.util.StringUtils;
import com.finalwork.marvelview.api.util.ViewUtils;
import com.finalwork.marvelview.databinding.ActivityMainBinding;
import com.finalwork.marvelview.databinding.ItemListCharacterBinding;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.view.DetailActivity;
import com.finalwork.marvelview.view.MainActivity;
import com.finalwork.marvelview.view.SearchActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AbsViewModel<MainVM.View> implements MainVM.View, CharacterAdapter.
        OnItemClickListener<CharacterVO, CharacterAdapter.ViewHolder, ItemListCharacterBinding>,
        CharacterAdapter.OnLoadListener {

    private final MarvelApi mMarvelApi;
    private final List<CharacterVO> mEntries;
    private String mAttribution;
    private boolean mHasMore;
    private ActivityMainBinding mBinding;
    private MainActivity mainActivity;

    public MainViewModel(@NonNull MarvelApi marvelApi,MainActivity mainActivity) {
        mMarvelApi = marvelApi;
        mEntries = new ArrayList<>();

        mBinding=mainActivity.getMainBinding();
        this.mainActivity=mainActivity;
//        mCharacterAdapter=mainActivity.getCharacterAdapter();

    }


    public void initScreen() {//初始化列表
        if (mEntries.isEmpty()) {
            loadCharacters(0);
        } else {
            mView.showResult(mEntries);
            mView.showAttribution(mAttribution);
            mView.stopProgress(mHasMore);
        }
    }


    public void loadCharacters(int offset) {//载入角色数据
        mView.showProgress();
        mMarvelApi.listCharacters(offset, new MarvelCallback<CharacterDataWrapper>() {


            public void onResult(CharacterDataWrapper data) {
                MarvelResult<CharacterVO> result = DataParser.parse(data);
                mEntries.addAll(result.getmEntries());
                mAttribution = result.getmAttribution();
                int offset = result.getmOffset();
                int count = result.getmCount();
                int total = result.getmTotal();
                mHasMore = total > offset + count;
                if (!isViewAttached()) {
                    return;
                }
                mView.showResult(mEntries);
                mView.showAttribution(mAttribution);
                mView.stopProgress(mHasMore);
            }


            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
                mView.stopProgress(mHasMore);
            }
        });
    }


    public void characterClick(@NonNull View heroView, @NonNull CharacterVO character) {
        mView.openCharacter(heroView, character);
    }


    public void searchClick() {
        mView.openSearch();
    }


    public void refresh() {
        mEntries.clear();
        loadCharacters(0);
    }

    public void onItemClick(ArrayAdapter<CharacterVO, CharacterAdapter.ViewHolder> adapter, ItemListCharacterBinding binding, int position) {
        characterClick(binding.image, adapter.getItem(position));
    }

    @Override
    public void onLoadMore(int offset) {
        loadCharacters(offset);
    }

    @Override
    public void showProgress() {//设置程序载入状态和是否具有更多数据状态
        mainActivity.getCharacterAdapter().setLoading(true);
        mainActivity.getCharacterAdapter().setHasMore(true);
    }

    @Override
    public void stopProgress(boolean hasMore) {
        mainActivity.getCharacterAdapter().setLoading(false);
        mainActivity.getCharacterAdapter().setHasMore(hasMore);
        mBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showAttribution(String attribution) {
        //noinspection ConstantConditions
       // getSupportActionBar().setSubtitle(attribution);
    }

    @Override
    public void showResult(@NonNull List<CharacterVO> entries) {
        mainActivity.getCharacterAdapter().setItems(entries);

        if (mBinding.error.isShown()) {
            mBinding.error.setText(null);
            ViewUtils.fadeView(mBinding.recycler, true, true);
            ViewUtils.fadeView(mBinding.error, false, true);
        }
    }

    @Override
    public void showError(@NonNull Throwable e) {
        String msg = StringUtils.getApiErrorMessage(mainActivity, e);
        if (mainActivity.getCharacterAdapter().getItemCount() > 1) { // If user already has items shown
            Snackbar.make(mBinding.recycler, msg, Snackbar.LENGTH_LONG).show();
        } else {
            boolean animate = !TextUtils.equals(mBinding.error.getText(), msg);
            boolean showError = !TextUtils.isEmpty(msg);
            mBinding.error.setText(msg);
            ViewUtils.fadeView(mBinding.recycler, !showError, animate);
            ViewUtils.fadeView(mBinding.error, showError, animate);
        }
    }

    @Override
    public void openCharacter(@NonNull View heroView, @NonNull CharacterVO character) {
        DetailActivity.start(mainActivity, heroView, character);
    }

    @Override
    public void openSearch() {
        mainActivity.startActivity(new Intent(mainActivity, SearchActivity.class));
    }

}

