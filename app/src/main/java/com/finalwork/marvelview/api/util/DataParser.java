package com.finalwork.marvelview.api.util;

import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.Url;
import com.finalwork.marvelview.api.json.character.CharacterData;
import com.finalwork.marvelview.api.json.character.CharacterDataContainer;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.json.section.SectionData;
import com.finalwork.marvelview.api.json.section.SectionDataContainer;
import com.finalwork.marvelview.api.json.section.SectionDataWrapper;
import com.finalwork.marvelview.api.json.section.SectionSummary;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.model.viewobject.SectionVO;

import java.util.ArrayList;
import java.util.List;

public class DataParser {


    public static MarvelResult<CharacterVO> parse(CharacterDataWrapper dataWrapper) {//从DataWrapper转成ViewObject
        MarvelResult<CharacterVO> result = new MarvelResult<>();
        CharacterDataContainer dataContainer = dataWrapper.data;
        if (dataContainer != null) {
            result.setmOffset(dataContainer.offset);
            result.setmTotal(dataContainer.total);
            CharacterData[] results = dataContainer.results;
            if (results != null) {
                List<CharacterVO> characterList = new ArrayList<>(results.length);
                for (CharacterData characterData : results) {
                    CharacterVO character = new CharacterVO();
                    character.setmId(characterData.id);
                    character.setmName(characterData.name);
                    character.setmDescription(characterData.description);
                    character.setmThumbnail(characterData.getThumbnail());
                    character.setmImage(characterData.getImage());
                    for (Url url : characterData.urls) {
                        if (Url.TYPE_DETAIL.equals(url.type)) {
                            character.setmDetail(url.url);
                        } else if (Url.TYPE_WIKI.equals(url.type)) {
                            character.setmWiki(url.url);
                        } else if (Url.TYPE_COMICLINK.equals(url.type)) {
                            character.setmComicLink(url.url);
                        }
                    }
                    character.setmComics(parseSection(characterData.comics.items));
                    character.setmSeries(parseSection(characterData.series.items));
                    character.setmStories(parseSection(characterData.stories.items));
                    character.setmEvents(parseSection(characterData.events.items));
                    characterList.add(character);
                }
                result.setmEntries(characterList);
            }
        }
        result.setmAttribution(dataWrapper.attributionText);
        return result;
    }

    private static List<SectionVO> parseSection(SectionSummary[] items) {
        List<SectionVO> list = new ArrayList<>();
        for (SectionSummary summary : items) {
            SectionVO section = new SectionVO();
            section.setmId(summary.getId());
            section.setmTitle(summary.name);
            list.add(section);
        }
        return list;
    }

    public static MarvelResult<SectionVO> parse(SectionDataWrapper dataWrapper) {
        MarvelResult<SectionVO> result = new MarvelResult<>();
        SectionDataContainer dataContainer = dataWrapper.data;
        if (dataContainer != null) {
            result.setmOffset(dataContainer.offset);
            result.setmTotal(dataContainer.total);
            SectionData[] results = dataContainer.results;
            if (results != null) {
                List<SectionVO> list = new ArrayList<>(results.length);
                for (SectionData sectionData : results) {
                    SectionVO sectionVO = new SectionVO();
                    sectionVO.setmId(sectionData.id);
                    sectionVO.setmTitle(sectionData.title);
                    sectionVO.setmThumbnail(sectionData.getThumbnail());
                    sectionVO.setmImage(sectionData.getImage());
                    list.add(sectionVO);
                }
                result.setmEntries(list);
            }
        }
        result.setmAttribution(dataWrapper.attributionText);
        return result;
    }

}
