package com.finalwork.marvelview.viewmodel;

import com.finalwork.marvelview.model.viewobject.SectionVO;

import java.util.List;

public interface SectionVM{

    interface View {

        void showItems(List<SectionVO> entries, int position);

        void close();
    }

    interface Actions {

        void closeClick();
    }
}
