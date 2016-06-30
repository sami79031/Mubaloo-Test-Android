package com.thracecodeinc.mubalootest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.thracecodeinc.mubalootest.Models.Team;
import com.thracecodeinc.mubalootest.RESTful.GetTeams;
import com.thracecodeinc.mubalootest.SQLite.DBHandler;
import com.thracecodeinc.mubalootest.utils.NetworkChangeReceiver;
import com.thracecodeinc.mubalootest.utils.NetworkUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTeams.AsyncResponse, NetworkChangeReceiver.OnNetworkChangeListener {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private TeamsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*check if network connection
        *load from DB if no network
        */
        if (isNetworkConnected()) {
            //call the restful api
            GetTeams getTeams = new GetTeams(this);
            getTeams.execute();
            getTeams.delegate = this;
        } else {
            initRecycleView(new DBHandler(this).getAllMembers());
            Log.d("DB sqlite", "loaded from DB");
        }

    }


    //on selected cell go to DetailActivity class
    TeamsAdapter.OnItemClickListener onItemClickListener = new TeamsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, Team member) {
            //pass selected member obj to detail view
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_PARAM_ID, member);

            //passing common views// transitioning helper
            ImageView placeImage = (ImageView) view.findViewById(R.id.placeImage);
            FrameLayout placeNameHolder = (FrameLayout) view.findViewById(R.id.placeNameHolder);
            // 2
            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
            Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
            // 3
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                    imagePair, holderPair);
            ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<Team> output) {
        initRecycleView(output);
    }

    public void initRecycleView(ArrayList<Team> output){
        if (output != null) {
            mRecyclerView = (RecyclerView) findViewById(R.id.list);
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mAdapter = new TeamsAdapter(this, output);
            mAdapter.SetOnItemClickListener(onItemClickListener);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        }
    }


    public boolean isNetworkConnected(){
        int status = NetworkUtil.getConnectivityStatus(this);
        if (status ==  NetworkUtil.TYPE_NOT_CONNECTED)
            return false;

        return true;
    }

    //getting message s about the internet connection status
    @Override
    public void onSNetworkChange(String message) {
        //do something in case of network change
    }
}
