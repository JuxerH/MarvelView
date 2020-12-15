package com.finalwork.marvelview.viewmodel;

import androidx.annotation.NonNull;

import com.finalwork.marvelview.model.viewobject.CharacterVO;

import java.util.List;

public interface SearchVM {

    interface View {

        void showProgress();

        void stopProgress();

        void showResult(List<CharacterVO> entries);

        void showError(Throwable e);

        void openCharacter(@NonNull android.view.View heroView, @NonNull CharacterVO character);
    }

    interface Actions {

        void loadCharacters();

        void searchCharacters(String query);

        void characterClick(@NonNull android.view.View heroView, @NonNull CharacterVO character);
    }
}