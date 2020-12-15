package com.finalwork.marvelview.api.util;

import com.finalwork.marvelview.api.BDTranslateApi;
import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.json.Url;
import com.finalwork.marvelview.api.json.character.CharacterData;
import com.finalwork.marvelview.api.json.character.CharacterDataContainer;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.json.section.SectionData;
import com.finalwork.marvelview.api.json.section.SectionDataContainer;
import com.finalwork.marvelview.api.json.section.SectionDataWrapper;
import com.finalwork.marvelview.api.json.section.SectionSummary;
import com.finalwork.marvelview.api.json.translateresult.TranslateDataWrapper;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.model.viewobject.SectionVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataParser {
    private final static BDTranslateApi translateTool = BDTranslateApi.getInstance();


    public static MarvelResult<CharacterVO> parse(CharacterDataWrapper dataWrapper) {//从DataWrapper转成ViewObject
//        StringBuffer nameTranslatePool=new StringBuffer();
//        StringBuffer descriptionTranslatePool=new StringBuffer();
//        String[] nameTranslateResult;

        MarvelResult<CharacterVO> result = new MarvelResult<>();
        CharacterDataContainer dataContainer = dataWrapper.data;
        if (dataContainer != null) {
            result.setmOffset(dataContainer.offset);
            result.setmTotal(dataContainer.total);
            CharacterData[] results = dataContainer.results;
            if (results != null) {
                List<CharacterVO> characterList = new ArrayList<>(results.length);
                int temp=1;
                for (CharacterData characterData : results) {
                    CharacterVO character = new CharacterVO();
                    character.setId(characterData.id);
                    character.setName(characterData.name);
                    character.setDescription(characterData.description);

//                    try { TODO
//                        if (!characterData.name.equals("")) {
//                            character.setZhName(translateTool.asyTranslate(characterData.name));
//                        } else character.setZhName("代号未知");
//                        if (!characterData.description.equals("")) {
//                            character.setZhDescription(translateTool.asyTranslate(characterData.description));
//                        } else character.setDescription("该角色描述甚少，或许是无名小卒，又或许是......");
//                    } catch (IOException e) {
//                        e.getMessage();
//                    }

//                   nameTranslatePool.append(characterData.name).append("95279527"); TODO

                    character.setThumbnail(characterData.getThumbnail());
                    character.setImage(characterData.getImage());
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

//                try { TODO
//                    String resultString=translateTool.postTranslate(nameTranslatePool.toString());
//                    nameTranslateResult=resultString.split("95279527");
//
//                    if(nameTranslateResult.length==characterList.size()){
//                            for(int i=0;i<nameTranslateResult.length;i++){
//                                characterList.get(i).setZhName(nameTranslateResult[i]);
//                        }
//                    }
//                }catch (IOException e){
//                    e.getMessage();
//                }

                result.setmEntries(characterList);
            }
        }
        result.setmAttribution(dataWrapper.attributionText);
        return result;
    }

    public static String parse(TranslateDataWrapper translateDataWrapper) {
        String result = "";//TODO


        return result;
    }

    private static List<SectionVO> parseSection(SectionSummary[] items) {
        List<SectionVO> list = new ArrayList<>();
        for (SectionSummary summary : items) {
            SectionVO section = new SectionVO();
            section.setmId(summary.getId());
            section.setTitle(summary.name);
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
                    sectionVO.setTitle(sectionData.title);
                    sectionVO.setmThumbnail(sectionData.getThumbnail());
                    sectionVO.setImage(sectionData.getImage());
                    list.add(sectionVO);
                }
                result.setmEntries(list);
            }
        }
        result.setmAttribution(dataWrapper.attributionText);
        return result;
    }

}
