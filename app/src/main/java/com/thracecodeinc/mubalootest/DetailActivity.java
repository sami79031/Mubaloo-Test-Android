package com.thracecodeinc.mubalootest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thracecodeinc.mubalootest.Models.Team;


public class DetailActivity extends Activity implements View.OnClickListener {

  public static final String EXTRA_PARAM_ID = "member_id";

  private ImageView mImageView;
  private TextView mFullName;
  private TextView mRole;
  private TextView mID;
  private TextView teamLeader;
  private LinearLayout mRevealView;
  private Team mMember;
  int defaultColor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    mMember = (Team) getIntent().getExtras().getSerializable(EXTRA_PARAM_ID);

    mImageView = (ImageView) findViewById(R.id.placeImage);
    mFullName = (TextView) findViewById(R.id.textView);
    mRole = (TextView) findViewById(R.id.role_id);
    mID = (TextView) findViewById(R.id.id_text);
    teamLeader = (TextView) findViewById(R.id.teamLeader_id);

    mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);

    defaultColor = ContextCompat.getColor(this, R.color.primary_dark);

    mRevealView.setVisibility(View.INVISIBLE);


    loadMember();
    windowTransition();

  }



  private void loadMember() {
    String fullName = mMember.getFirstName()+" "+mMember.getLastName();
    mFullName.setText("Name: "+fullName);
    mRole.setText("Team role: "+mMember.getRole());
    mID.setText("ID: "+mMember.getID());
    if (mMember.isTeamLead())
      teamLeader.setText("Team Leader");



    Picasso.with(this)
            .load(mMember.getProfileImgURL())
            .into(mImageView);

  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void windowTransition() {
    getWindow().getEnterTransition().addListener(new TransitionAdapter() {
      @Override
      public void onTransitionEnd(Transition transition) {
        mImageView.animate().alpha(1.0f);
        getWindow().getEnterTransition().removeListener(this);
      }
    });

  }


  @Override
  public void onClick(View v) {

  }



  @Override
  public void onBackPressed() {
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
    alphaAnimation.setDuration(100);
    mImageView.startAnimation(alphaAnimation);
    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @TargetApi(Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onAnimationEnd(Animation animation) {
        //mAddButton.setVisibility(View.GONE);
        finishAfterTransition();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }
}
