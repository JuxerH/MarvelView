package com.finalwork.marvelview;

import com.finalwork.marvelview.api.BDTranslateApi;
import com.finalwork.marvelview.api.MarvelApi;
import com.finalwork.marvelview.api.MarvelCallback;
import com.finalwork.marvelview.api.MarvelResult;
import com.finalwork.marvelview.api.exception.MarvelException;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.json.translateresult.TranslateDataWrapper;
import com.finalwork.marvelview.model.viewobject.CharacterVO;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest{
    @Test
    public void translateTest() throws IOException {
        String[] query={"batman","superman","spiderman","3-d man"};
        BDTranslateApi  bdTranslateApi=BDTranslateApi.getInstance();
        StringBuffer stringBuffer=new StringBuffer();
        for(String s : query){
            stringBuffer.append(s).append(",");
        }
        String[] result=bdTranslateApi.postTranslate(stringBuffer.toString()).split("，");
       for (String s:result){
           System.out.print(s+" ");
       }
//        System.out.println(bdTranslateApi.translate("Agent Zero"));
//        System.out.println(bdTranslateApi.postTranslate("Agent Zero"));
    }
    @Test
    public void marvelTest() throws IOException, MarvelException {
        MarvelApi marvelApi=MarvelApi.getInstance();
        marvelApi.listCharacters(0,new MarvelCallback<CharacterDataWrapper>(){

            @Override
            public void onResult(CharacterDataWrapper data) {
                return;
            }

            @Override
            public void onError(Throwable e) {
                return;
            }
        });
    }
    @Test
    public void marvelTest1() throws IOException, MarvelException {
        MarvelApi marvelApi=MarvelApi.getInstance();
        MarvelResult<CharacterVO> marvelResult=marvelApi.listCharacters(0);
        System.out.println("1");
    }
}