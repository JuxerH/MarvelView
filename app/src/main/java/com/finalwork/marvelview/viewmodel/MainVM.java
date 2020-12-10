package com.finalwork.marvelview.viewmodel;

import androidx.annotation.NonNull;

import com.finalwork.marvelview.model.viewobject.CharacterVO;

import java.util.List;

public interface MainVM {
    interface View {

        void showProgress();

        void stopProgress(boolean hasMore);

        void showAttribution(String attribution);

        void showResult(@NonNull List<CharacterVO> entries);

        void showError(@NonNull Throwable e);

        void openCharacter(@NonNull android.view.View heroView, @NonNull CharacterVO character);

        void openSearch();
    }

    interface Actions {

        void initScreen();

        void loadCharacters(int offset);

        void characterClick(@NonNull android.view.View heroView, @NonNull CharacterVO character);

        void searchClick();

        void refresh();
    }
}
