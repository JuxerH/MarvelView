package com.finalwork.marvelview.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.api.util.PagerSharedElementCallback;
import com.finalwork.marvelview.databinding.ActivitySectionBinding;
import com.finalwork.marvelview.model.viewobject.SectionVO;
import com.finalwork.marvelview.viewmodel.SectionViewModel;

import java.io.Serializable;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    private static final String EXTRA_ATTRIBUTION = SectionActivity.class.getPackage().getName() + ".extra.ATTRIBUTION";
    private static final String EXTRA_ENTRIES = SectionActivity.class.getPackage().getName() + ".extra.ENTRIES";
    private static final String EXTRA_POSITION = SectionActivity.class.getPackage().getName() + ".extra.POSITION";
    private static final String EXTRA_TYPE = SectionActivity.class.getPackage().getName() + ".extra.TYPE";
    public static final int EXTRA_NOT_FOUND = -1;

    public static void start(Activity activity, @SectionVO.Type int type, View heroView, String attribution, List<SectionVO> entries, int position) {

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity,
                        heroView, ViewCompat.getTransitionName(heroView));
        Intent intent = new Intent(activity, SectionActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_ATTRIBUTION, attribution);
        Intent intent1 = intent.putExtra(EXTRA_ENTRIES, (Serializable) entries);
        intent.putExtra(EXTRA_POSITION, position);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static int getType(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra(EXTRA_TYPE)) {
            return data.getIntExtra(EXTRA_TYPE, EXTRA_NOT_FOUND);
        }
        return EXTRA_NOT_FOUND;
    }

    public static int getPosition(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra(EXTRA_POSITION)) {
            return data.getIntExtra(EXTRA_POSITION, EXTRA_NOT_FOUND);
        }
        return EXTRA_NOT_FOUND;
    }

    private ViewPager mViewPager;
    private SectionViewModel sectionViewModel;
    private int mType;
    private PagerSharedElementCallback mSharedElementCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_section);

        // Postpone transition until the image of ViewPager's initial item is loaded
        supportPostponeEnterTransition();
        setEnterSharedElementCallback(mSharedElementCallback = new PagerSharedElementCallback());

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mType = getIntent().getExtras().getInt(EXTRA_TYPE);
        String attribution = getIntent().getStringExtra(EXTRA_ATTRIBUTION);
        //noinspection unchecked
        List<SectionVO> entries = (List<SectionVO>) getIntent().getExtras().get(EXTRA_ENTRIES);
        assert entries != null;
        int position = getIntent().getExtras().getInt(EXTRA_POSITION);

        if (savedInstanceState == null) {
            sectionViewModel = new SectionViewModel(entries, position,this);
        } else {
            sectionViewModel = (SectionViewModel) getLastCustomNonConfigurationInstance();
        }
        sectionViewModel.attachView(sectionViewModel);

        binding.setAttribution(attribution);
        binding.setSectionViewModel(sectionViewModel);
    }

    @Override
    public void finish() {
        setResult();
        super.finish();
    }

    @Override
    public void finishAfterTransition() {
        setResult();
        super.finishAfterTransition();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return sectionViewModel;
    }

    @Override
    protected void onDestroy() {
        sectionViewModel.detachView();
        super.onDestroy();
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public PagerSharedElementCallback getmSharedElementCallback() {
        return mSharedElementCallback;
    }

    public void setmSharedElementCallback(PagerSharedElementCallback mSharedElementCallback) {
        this.mSharedElementCallback = mSharedElementCallback;
    }

    private void setResult() {
        int position = mViewPager.getCurrentItem();
        Intent data = new Intent();
        data.putExtra(EXTRA_TYPE, mType);
        data.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, data);
    }

}
