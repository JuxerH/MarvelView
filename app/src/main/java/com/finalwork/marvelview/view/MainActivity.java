package com.finalwork.marvelview.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.adapter.CharacterAdapter;
import com.finalwork.marvelview.api.BDTranslateApi;
import com.finalwork.marvelview.api.MarvelApi;
import com.finalwork.marvelview.databinding.ActivityMainBinding;
import com.finalwork.marvelview.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private MainViewModel mainViewModel;
    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        setSupportActionBar( mainBinding.toolbar);

         mainBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
         mainBinding.recycler.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mainViewModel= new MainViewModel(MarvelApi.getInstance(),this);
        } else {
            mainViewModel = (MainViewModel) getLastCustomNonConfigurationInstance();
        }

        characterAdapter = new CharacterAdapter(R.layout.item_list_character, mainViewModel, mainViewModel);
        mainBinding.recycler.setAdapter(characterAdapter);

        mainViewModel.attachView(mainViewModel);
        mainViewModel.initScreen();

        mainBinding.setMainViewModel(mainViewModel);


    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mainViewModel;
    }

    @Override
    protected void onDestroy() {
        mainViewModel.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_search == item.getItemId()) {
            mainViewModel.searchClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ActivityMainBinding getMainBinding() {
        return mainBinding;
    }

    public void setMainBinding(ActivityMainBinding mainBinding) {
        this.mainBinding = mainBinding;
    }

    public CharacterAdapter getCharacterAdapter() {
        return characterAdapter;
    }

    public void setCharacterAdapter(CharacterAdapter characterAdapter) {
        this.characterAdapter = characterAdapter;
    }
}