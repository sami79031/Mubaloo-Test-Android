package com.thracecodeinc.mubalootest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thracecodeinc.mubalootest.Models.Team;

import java.util.ArrayList;

/**
 * Created by Samurai on 6/28/16.
 */
public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder>{
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private ArrayList<Team> teamsList;

    // 2
    public TeamsAdapter(Context context, ArrayList<Team> teamsList) {
        this.mContext = context;
        this.teamsList = teamsList;
    }

    // 3
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout placeHolder;
        public FrameLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;
        public TextView leaderText;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.firstName);
            placeNameHolder = (FrameLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            leaderText = (TextView) itemView.findViewById(R.id.leader_id);
            placeHolder.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, teamsList.get(getAdapterPosition()));
            }
        }

    }


    public interface OnItemClickListener {
        void onItemClick(View view, Team member);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    // 1
    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    // 2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_teams, parent, false);
        return new ViewHolder(view);
    }

    // 3
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Team team = teamsList.get(position);
        if (team.isTeamLead()) {
            holder.placeNameHolder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            holder.leaderText.setText(mContext.getString(R.string.team_leader));
        }
        else {
            holder.placeNameHolder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primary_dark));
            holder.leaderText.setText(team.getRole());
        }


        String fullName = team.getFirstName()+" "+team.getLastName();
        holder.placeName.setText(fullName);
        Picasso.with(mContext)
                .load(team.getProfileImgURL())
                .into(holder.placeImage);

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
