package com.finalwork.marvelview.view;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.adapter.SectionAdapter;
import com.finalwork.marvelview.api.BDTranslateApi;
import com.finalwork.marvelview.api.util.PagerSharedElementCallback;
import com.finalwork.marvelview.databinding.ActivityDetailBinding;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.model.viewobject.SectionVO;
import com.finalwork.marvelview.viewmodel.DetailViewModel;

import java.io.IOException;

import okhttp3.internal.http2.Http2Reader;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_CHARACTER = DetailActivity.class.getPackage().getName() + ".extra.CHARACTER";

    public static void start(@NonNull Activity activity, @NonNull View characterView, @NonNull CharacterVO character) {

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, characterView, ViewCompat.getTransitionName(characterView));
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER, character);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static PendingIntent getPendingIntent(@NonNull Context context, @NonNull CharacterVO character, int id) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.setAction(Integer.toString(id)); // 用于更新每个小部件的所有PendingIntent Extras数据
        intent.putExtra(EXTRA_CHARACTER, character);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent); // 返回MainActivity

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private ActivityDetailBinding mBinding;
    private DetailViewModel detailViewModel;
    private PagerSharedElementCallback mSharedElementCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(mBinding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CharacterVO character = (CharacterVO) getIntent().getExtras().get(EXTRA_CHARACTER);
        assert character != null;





        if (savedInstanceState == null) {
            detailViewModel = new DetailViewModel(character,this);
        } else {
            detailViewModel = (DetailViewModel) getLastCustomNonConfigurationInstance();
        }

        setupSectionView(mBinding.recyclerComics,SectionVO.TYPE_COMIC);
        setupSectionView(mBinding.recyclerSeries, SectionVO.TYPE_SERIES);
        setupSectionView(mBinding.recyclerStories, SectionVO.TYPE_STORY);
        setupSectionView(mBinding.recyclerEvents, SectionVO.TYPE_EVENT);

        detailViewModel.attachView(detailViewModel);


        mBinding.setDetailViewModel(detailViewModel);
    }

    private void setupSectionView(RecyclerView recyclerView, @SectionVO.Type int type) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        SectionAdapter adapter = new SectionAdapter(this, type, detailViewModel);
        recyclerView.setAdapter(adapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        //noinspection ConstantConditions
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return detailViewModel;
    }

    @Override
    protected void onDestroy() {
        detailViewModel.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_search == item.getItemId()) {
            detailViewModel.searchClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

        int type = SectionActivity.getType(resultCode, data);
        final int position = SectionActivity.getPosition(resultCode, data);

        final RecyclerView recyclerView;
        switch (type) {
            case SectionVO.TYPE_COMIC:
                recyclerView = mBinding.recyclerComics;
                break;
            case SectionVO.TYPE_SERIES:
                recyclerView = mBinding.recyclerSeries;
                break;
            case SectionVO.TYPE_STORY:
                recyclerView = mBinding.recyclerStories;
                break;
            case SectionVO.TYPE_EVENT:
                recyclerView = mBinding.recyclerEvents;
                break;
            default:
                recyclerView = null;
        }

        if (recyclerView == null) {
            return;
        }

        if (position != SectionActivity.EXTRA_NOT_FOUND) {
            recyclerView.scrollToPosition(position);
        }

        mSharedElementCallback = new PagerSharedElementCallback();
        setExitSharedElementCallback(mSharedElementCallback);

        //noinspection ConstantConditions
        supportPostponeEnterTransition();
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                if (holder instanceof SectionAdapter.ViewHolder) {
                    SectionAdapter.ViewHolder mediaViewHolder = (SectionAdapter.ViewHolder) holder;
                    mSharedElementCallback.setSharedElementViews(mediaViewHolder.itemView.findViewById(R.id.image));
                }

                supportStartPostponedEnterTransition();

                return true;
            }
        });

    }


    public ActivityDetailBinding getmBinding() {
        return mBinding;
    }

    public void setmBinding(ActivityDetailBinding mBinding) {
        this.mBinding = mBinding;
    }
}
