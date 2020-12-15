package com.finalwork.marvelview.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.finalwork.marvelview.adapter.ArrayAdapter;
import com.finalwork.marvelview.adapter.SectionAdapter;
import com.finalwork.marvelview.api.MarvelApi;
import com.finalwork.marvelview.api.MarvelCallback;
import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.section.SectionDataWrapper;
import com.finalwork.marvelview.api.util.DataParser;
import com.finalwork.marvelview.databinding.ActivityDetailBinding;
import com.finalwork.marvelview.databinding.ItemListSectionBinding;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.model.viewobject.SectionVO;
import com.finalwork.marvelview.view.DetailActivity;
import com.finalwork.marvelview.view.SearchActivity;
import com.finalwork.marvelview.view.SectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends AbsViewModel<DetailVM.View> implements DetailVM.Actions,DetailVM.View,
        ArrayAdapter.OnItemClickListener<SectionVO, SectionAdapter.ViewHolder, ItemListSectionBinding> {
    private final MarvelApi mMarvelApi;
    private final CharacterVO mCharacter;
    private final List<SectionVO> mComicList;
    private final List<SectionVO> mSeriesList;
    private final List<SectionVO> mStoryList;
    private final List<SectionVO> mEventList;

    private DetailActivity detailActivity;
    private ActivityDetailBinding activityDetailBinding;
    private String mAttribution;

    public DetailViewModel(@NonNull CharacterVO character,DetailActivity detailActivity) {
        mMarvelApi = MarvelApi.getInstance();
        mCharacter = character;
        mComicList = new ArrayList<>();
        mSeriesList = new ArrayList<>();
        mStoryList = new ArrayList<>();
        mEventList = new ArrayList<>();

        activityDetailBinding=detailActivity.getmBinding();
        this.detailActivity=detailActivity;
    }

    @Override
    public void attachView(@NonNull DetailVM.View view) {
        super.attachView(view);
        mView.showAttribution(mAttribution);
        mView.showCharacter(mCharacter);
        if (mComicList.isEmpty()) {
            mView.showComics(mCharacter.getComics());
            loadComics(mCharacter.getId(), 0);
        } else {
            mView.showComics(mComicList);
        }
        if (mSeriesList.isEmpty()) {
            mView.showSeries(mCharacter.getSeries());
            loadSeries(mCharacter.getId(), 0);
        } else {
            mView.showSeries(mSeriesList);
        }
        if (mStoryList.isEmpty()) {
            mView.showStories(mCharacter.getStories());
            loadStories(mCharacter.getId(), 0);
        } else {
            mView.showStories(mStoryList);
        }
        if (mEventList.isEmpty()) {
            mView.showEvents(mCharacter.getEvents());
            loadEvents(mCharacter.getId(), 0);
        } else {
            mView.showEvents(mEventList);
        }
    }

    @Override
    public void loadComics(long characterId, int offset) {
        mMarvelApi.listComics(characterId, offset, new MarvelCallback<SectionDataWrapper>() {

            @Override
            public void onResult(SectionDataWrapper data) {
                MarvelResult<SectionVO> result = DataParser.parse(data);
                mComicList.addAll(result.getmEntries());
                mAttribution = result.getmAttribution();
                if (!isViewAttached()) {
                    return;
                }
                mView.showComics(mComicList);
                mView.showAttribution(mAttribution);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
            }
        });
    }

    @Override
    public void loadSeries(long characterId, int offset) {
        mMarvelApi.listSeries(characterId, offset, new MarvelCallback<SectionDataWrapper>() {

            @Override
            public void onResult(SectionDataWrapper data) {
                MarvelResult<SectionVO> result = DataParser.parse(data);
                mSeriesList.addAll(result.getmEntries());
                mAttribution = result.getmAttribution();
                if (!isViewAttached()) {
                    return;
                }
                mView.showSeries(mSeriesList);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
            }
        });
    }

    @Override
    public void loadStories(long characterId, int offset) {
        mMarvelApi.listStories(characterId, offset, new MarvelCallback<SectionDataWrapper>() {

            @Override
            public void onResult(SectionDataWrapper data) {
                MarvelResult<SectionVO> result = DataParser.parse(data);
                mStoryList.addAll(result.getmEntries());
                mAttribution = result.getmAttribution();
                if (!isViewAttached()) {
                    return;
                }
                mView.showStories(mStoryList);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
            }
        });
    }

    @Override
    public void loadEvents(long characterId, int offset) {
        mMarvelApi.listEvents(characterId, offset, new MarvelCallback<SectionDataWrapper>() {

            @Override
            public void onResult(SectionDataWrapper data) {
                MarvelResult<SectionVO> result = DataParser.parse(data);
                mEventList.addAll(result.getmEntries());
                mAttribution = result.getmAttribution();
                if (!isViewAttached()) {
                    return;
                }
                mView.showEvents(mEventList);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                mView.showError(e);
            }
        });
    }

    @Override
    public void sectionClick(@SectionVO.Type int type, @NonNull View heroView, @NonNull List<SectionVO> entries, int position) {
        mView.openSection(type, heroView, mAttribution, entries, position);
    }

    @Override
    public void linkClick(@NonNull String url) {
        mView.openLink(url);
    }

    @Override
    public void searchClick() {
        mView.openSearch();
    }
    @Override
    public void onItemClick(ArrayAdapter<SectionVO, SectionAdapter.ViewHolder> adapter, ItemListSectionBinding binding, int position) {
        SectionAdapter sectionAdapter = (SectionAdapter) adapter;
        sectionClick(sectionAdapter.getType(), binding.image, adapter.getItems(), position);
    }

    @Override
    public void showAttribution(String attribution) {
        activityDetailBinding.setAttribution(attribution);
    }

    @Override
    public void showCharacter(@NonNull CharacterVO character) {
        activityDetailBinding.setCharacter(character);
    }

    @Override
    public void showComics(@NonNull List<SectionVO> entries) {
        SectionAdapter adapter = (SectionAdapter) activityDetailBinding.recyclerComics.getAdapter();
        adapter.setItems(entries);
    }

    @Override
    public void showSeries(@NonNull List<SectionVO> entries) {
        SectionAdapter adapter = (SectionAdapter) activityDetailBinding.recyclerSeries.getAdapter();
        adapter.setItems(entries);
    }

    @Override
    public void showStories(@NonNull List<SectionVO> entries) {
        SectionAdapter adapter = (SectionAdapter) activityDetailBinding.recyclerStories.getAdapter();
        adapter.setItems(entries);
    }

    @Override
    public void showEvents(@NonNull List<SectionVO> entries) {
        SectionAdapter adapter = (SectionAdapter) activityDetailBinding.recyclerEvents.getAdapter();
        adapter.setItems(entries);
    }

    @Override
    public void showError(@NonNull Throwable e) {
        Snackbar.make(activityDetailBinding.toolbarLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openSection(int type, @NonNull View heroView, String attribution, @NonNull List<SectionVO> entries, int position) {
        SectionActivity.start(detailActivity, type, heroView, attribution, entries, position);
    }

    @Override
    public void openLink(@NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        detailActivity.startActivity(intent);
    }

    @Override
    public void openSearch() {
        detailActivity.startActivity(new Intent(detailActivity,SearchActivity.class));
    }
}
