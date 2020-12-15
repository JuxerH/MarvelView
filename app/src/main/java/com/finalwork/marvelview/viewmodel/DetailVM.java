package com.finalwork.marvelview.viewmodel;

import androidx.annotation.NonNull;

import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.model.viewobject.SectionVO;

import java.util.List;

public interface DetailVM {


    interface View {

        void showAttribution(String attribution);

        void showCharacter(@NonNull CharacterVO character);

        void showComics(@NonNull List<SectionVO> entries);

        void showSeries(@NonNull List<SectionVO> entries);

        void showStories(@NonNull List<SectionVO> entries);

        void showEvents(@NonNull List<SectionVO> entries);

        void showError(@NonNull Throwable e);

        void openSection(@SectionVO.Type int type, @NonNull android.view.View heroView, String attribution, @NonNull List<SectionVO> entries, int position);

        void openLink(@NonNull String url);

        void openSearch();
    }

    interface Actions {

        void loadComics(long characterId, int offset);

        void loadSeries(long characterId, int offset);

        void loadStories(long characterId, int offset);

        void loadEvents(long characterId, int offset);

        void sectionClick(@SectionVO.Type int type, @NonNull android.view.View heroView, @NonNull List<SectionVO> entries, int position);

        void linkClick(@NonNull String url);

        void searchClick();
    }
}
