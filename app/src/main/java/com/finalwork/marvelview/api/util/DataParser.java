package com.finalwork.marvelview.api.util;

import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.character.CharacterData;
import com.finalwork.marvelview.api.json.character.CharacterDataContainer;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.model.viewobject.CharacterVO;

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
                            character.setDetail(url.url);
                        } else if (Url.TYPE_WIKI.equals(url.type)) {
                            character.setWiki(url.url);
                        } else if (Url.TYPE_COMICLINK.equals(url.type)) {
                            character.setComicLink(url.url);
                        }
                    }
                    character.setComics(parseSection(characterData.comics.items));
                    character.setSeries(parseSection(characterData.series.items));
                    character.setStories(parseSection(characterData.stories.items));
                    character.setEvents(parseSection(characterData.events.items));
                    characterList.add(character);
                }
                result.setEntries(characterList);
            }
        }
        result.setAttribution(dataWrapper.attributionText);
        return result;
    }

    private static List<SectionVO> parseSection(SectionSummary[] items) {
        List<SectionVO> list = new ArrayList<>();
        for (SectionSummary summary : items) {
            SectionVO section = new SectionVO();
            section.setId(summary.getId());
            section.setTitle(summary.name);
            list.add(section);
        }
        return list;
    }

    public static MarvelResult<SectionVO> parse(SectionDataWrapper dataWrapper) {
        MarvelResult<SectionVO> result = new MarvelResult<>();
        SectionDataContainer dataContainer = dataWrapper.data;
        if (dataContainer != null) {
            result.setOffset(dataContainer.offset);
            result.setTotal(dataContainer.total);
            SectionData[] results = dataContainer.results;
            if (results != null) {
                List<SectionVO> list = new ArrayList<>(results.length);
                for (SectionData sectionData : results) {
                    SectionVO sectionVO = new SectionVO();
                    sectionVO.setId(sectionData.id);
                    sectionVO.setTitle(sectionData.title);
                    sectionVO.setThumbnail(sectionData.getThumbnail());
                    sectionVO.setImage(sectionData.getImage());
                    list.add(sectionVO);
                }
                result.setEntries(list);
            }
        }
        result.setAttribution(dataWrapper.attributionText);
        return result;
    }

}
