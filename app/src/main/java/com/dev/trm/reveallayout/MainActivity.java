package com.dev.trm.reveallayout;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import fabreveallayout.FABRevealLayout;
import fabreveallayout.OnRevealChangeListener;

public class MainActivity extends AppCompatActivity {

    private FABRevealLayout mFabRevealLayout;
    private TextView        mAlbumTitleText;
    private TextView        mArtistNameText;
    private SeekBar         mSongProgress;
    private TextView        mSongTitleText;
    private ImageView       mPrevButton;
    private ImageView       mStopButton;
    private ImageView       mNextButton;
    private ImageView       mAlbumCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        configureFABReveal();
    }

    private void findViews() {
        mFabRevealLayout = (FABRevealLayout) findViewById(R.id.fab_reveal_layout);
        mAlbumTitleText  = (TextView)        findViewById(R.id.album_title_text);
        mArtistNameText  = (TextView)        findViewById(R.id.artist_name_text);
        mSongProgress    = (SeekBar)         findViewById(R.id.song_progress_bar);
        mAlbumCoverImage = (ImageView)       findViewById(R.id.album_cover_image);
        mSongTitleText   = (TextView)        findViewById(R.id.song_title_text);
        mPrevButton      = (ImageView)       findViewById(R.id.previous);
        mStopButton      = (ImageView)       findViewById(R.id.stop);
        mNextButton      = (ImageView)       findViewById(R.id.next);

        styleSeekbar(mSongProgress);
    }

    private void styleSeekbar(SeekBar songProgress) {
        int color = getResources().getColor(R.color.background);
        songProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        songProgress.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void configureFABReveal() {
        mFabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                showSecondaryViewItems();
                prepareBackTransition(fabRevealLayout);
            }
        });
    }

    private void showMainViewItems() {
        scale(mAlbumTitleText, 50);
        scale(mArtistNameText, 150);
    }

    private void showSecondaryViewItems() {
        scale(mSongProgress, 0);
        animateSeekBar(mSongProgress);
        scale(mSongTitleText, 100);
        scale(mPrevButton, 150);
        scale(mStopButton, 100);
        scale(mNextButton, 200);
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 5000);
    }

    private void scale(View view, long delay){
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    private void animateSeekBar(SeekBar seekBar){
        seekBar.setProgress(15);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.start();
    }
}
