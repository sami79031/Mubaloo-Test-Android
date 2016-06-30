package com.thracecodeinc.mubalootest.RESTful;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.thracecodeinc.mubalootest.Models.Team;
import com.thracecodeinc.mubalootest.SQLite.DBHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Samurai on 6/22/16.
 */
public class GetTeams extends AsyncTask<Void, Void, Void> {
    public interface AsyncResponse {
        void processFinish(ArrayList<Team> output);
    }

    public AsyncResponse delegate;
    private ArrayList<Team> teamList;
    private ProgressDialog proDialog;
    private Context context;
    private String url = "http://developers.mub.lu/resources/team.json" ;

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_FIRST_NAME = "firstName";
    private static final String TAG_LAST_NAME = "lastName";
    private static final String TAG_ROLE = "role";
    private static final String TAG_PROFILE_IMG = "profileImageURL";
    private static final String TAG_TEAM_NAME = "teamName";
    private static final String TAG_MEMBERS = "members";
    private static final String TAG_TEAM_LEAD = "teamLead";


    public GetTeams(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Please wait...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        ServiceConnection serviceConnection = new ServiceConnection();

        // Making a request to url and getting response
        String jsonStr = null;
        try {
            jsonStr = serviceConnection.makeWebServiceCall(url, ServiceConnection.GETRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        teamList = ParseJSON(jsonStr);


        //Log.d("Response: ", "> " + projectList);
        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Dismiss the progress dialog
        if (proDialog.isShowing())
            proDialog.dismiss();

        delegate.processFinish(teamList);
    }



    private ArrayList<Team> ParseJSON(String json) {
        if (json != null) {
            try {
                // ListView for the teams
                ArrayList<Team> team = new ArrayList<>();

                //sqlite instance
                DBHandler db = new DBHandler(context);

                // Getting JSON Array node
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++){
                    //getting 1st object
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    //getting the CEO
                    if (i == 0) {
                        Team ceo = new Team(jObj.getString(TAG_ID),jObj.getString(TAG_FIRST_NAME)
                                ,jObj.getString(TAG_LAST_NAME),jObj.getString(TAG_ROLE),
                                jObj.getString(TAG_PROFILE_IMG),false,"");
                        team.add(ceo);

                        //if db count > 0 update rows else insert
                        if (db.getMembersCount() > 0)
                            db.updateMember(ceo);
                        else
                            db.addTeam(ceo);

                    } else{

                        //getting the members of a team
                        String teamName = jObj.getString(TAG_TEAM_NAME);
                        JSONArray membersArray = jObj.getJSONArray(TAG_MEMBERS);

                        for (int k = 0; k < membersArray.length(); k++){
                            JSONObject memberObj = membersArray.getJSONObject(k);
                            Team member = new Team();
                            member.setTeamName(teamName);
                            member.setID(memberObj.getString(TAG_ID));
                            member.setFirstName(memberObj.getString(TAG_FIRST_NAME));
                            member.setLastName(memberObj.getString(TAG_LAST_NAME));
                            member.setRole(memberObj.getString(TAG_ROLE));
                            member.setProfileImgURL(memberObj.getString(TAG_PROFILE_IMG));

                            try {
                                member.setTeamLead(memberObj.getBoolean(TAG_TEAM_LEAD));
                            }catch (JSONException e){//e.printStackTrace();
                                 }

                            team.add(member);

                            //if db count > 0 update rows else insert
                            if (db.getMembersCount() > 0)
                                db.updateMember(member);
                            else
                                db.addTeam(member);

                        }
                    }

                }

                return team;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }
}
