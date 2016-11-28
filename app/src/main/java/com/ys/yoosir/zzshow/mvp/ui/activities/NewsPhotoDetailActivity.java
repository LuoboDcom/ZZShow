package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.NewsPhotoDetail;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.widget.photoview.HackyViewPager;

import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class NewsPhotoDetailActivity extends BaseActivity {

    private static final String PHOTO_DETAIL = "PHOTO_DETAIL";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.photo_viewpager)
    HackyViewPager mHackyViewPager;


    @BindView(R.id.photo_text_layout)
    FrameLayout mPhotoTextLayout;

    @BindView(R.id.photo_count_tv)
    TextView mPhotoCountTv;

    @BindView(R.id.photo_news_title_tv)
    TextView mPhotoTitleTv;

    @BindView(R.id.photo_news_desc_tv)
    TextView mPhotoDescTv;

    private NewsPhotoDetail mNewsPhotoDetail;
    private List<NewsPhotoDetail.PictureItem> mPictureList;

    private boolean isHidden = false;

    public static Intent getNewsDetailIntent(Context context, NewsPhotoDetail newsPhotoDetail){
        Intent intent = new Intent(context,NewsPhotoDetailActivity.class);
        intent.putExtra(PHOTO_DETAIL,newsPhotoDetail);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_photo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_photo_detail;
    }

    @Override
    public void initVariables() {
        mNewsPhotoDetail = getIntent().getParcelableExtra(PHOTO_DETAIL);
        mPictureList = mNewsPhotoDetail.getPictureItemList();

    }

    @Override
    public void initViews() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhotoTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsPhotoDetailActivity.this,"被电击",Toast.LENGTH_SHORT).show();
            }
        });
        mPhotoTitleTv.setText(mNewsPhotoDetail.getTitle());
        mPhotoDescTv.setText(mPictureList.get(0).getDescription());
        mPhotoCountTv.setText("1/"+mPictureList.size());
        mHackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 mPhotoDescTv.setText(mPictureList.get(position).getDescription());
                mPhotoCountTv.setText((position+1)+"/"+mPictureList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mHackyViewPager.setAdapter(new PhotoPagerAdapter());
    }

    public void hideToolBarAndTextView(){
        isHidden = !isHidden;
        if(isHidden){
            startAnimation(true,1.0f,0.0f);
        }else{
            startAnimation(false,0.1f,1.0f);
        }
    }

    private void startAnimation(final boolean endState, float startValue, float endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(startValue,endValue).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1,y2;
                if(endState){
                    y1 = (0 - animation.getAnimatedFraction())*mToolbar.getHeight();
                    y2 = animation.getAnimatedFraction() *mPhotoTextLayout.getHeight();
                }else{
                    y1 = (animation.getAnimatedFraction() - 1)*mToolbar.getHeight();
                    y2 = (1 - animation.getAnimatedFraction()) * mPhotoTextLayout.getHeight();
                }
                mToolbar.setTranslationY(y1);
                mPhotoTextLayout.setTranslationY(y2);
            }
        });
        animator.start();
    }

    class PhotoPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPictureList == null ? 0 : mPictureList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            PhotoView photoView = new PhotoView(NewsPhotoDetailActivity.this);
            Glide.with(NewsPhotoDetailActivity.this).load(mPictureList.get(position).getImgPath())
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_load_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    hideToolBarAndTextView();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }

}
