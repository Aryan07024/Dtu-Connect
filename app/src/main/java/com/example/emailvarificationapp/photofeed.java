package com.example.emailvarificationapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class photofeed extends ListFragment {
    ArrayList<Date> postdate= new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> caption=new ArrayList<>();
    ArrayList<String> photo =new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<ArrayList<String>> likeby =new ArrayList<ArrayList<String>>();
    ArrayList<Integer> likes = new ArrayList<>();
    ListView feed;
    MyAdapter adapter;
    ArrayList<ArrayList<String>> commentBy=new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> comment =new ArrayList<ArrayList<String>>();
    public photofeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_photofeed, container, false);

        if(ParseUser.getCurrentUser()==null)
        {
            Toast.makeText(getContext(),"You are Logout Please Signin back",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }


        if(ParseUser.getCurrentUser()==null)
        {
            Toast.makeText(getContext(),"You are Logout Please Signin back",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }

        if(ParseUser.getCurrentUser().get("isFollowing")==null)
        {
            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing",emptyList);
        }


        ParseQuery<ParseObject> query =new ParseQuery<ParseObject>("Image");

        query.whereContainedIn("username",ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null)
                {

                    if(objects.size()>0)
                    {

                        for(ParseObject object : objects)
                        {
                            ParseFile file=(ParseFile)object.getParseFile("image");
                            photo.add(file.getUrl());


                            postdate.add(object.getDate("postdate"));
                            name.add(object.getString("username"));
                            caption.add(object.getString("Content"));
                            id.add(object.getObjectId());
                            likes.add(object.getInt("likes"));
                            ArrayList<String> testStringArrayList = (ArrayList<String>)object.get("likeby");
                            likeby.add(testStringArrayList);
                            ArrayList<String> testStringArrayList1 = (ArrayList<String>)object.get("commentBy");
                            commentBy.add(testStringArrayList1);
                            ArrayList<String> testStringArrayList2 = (ArrayList<String>)object.get("comment");
                            comment.add(testStringArrayList2);
                            // Toast.makeText(getApplicationContext(),object.getObjectId(),Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getApplicationContext(),String.valueOf(id.size())+id.get(0),Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"ERROR : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

         //Toast.makeText(getContext(),id.size(),Toast.LENGTH_SHORT).show();

        Toast.makeText(getContext(), "hey", Toast.LENGTH_SHORT).show();
        adapter=new MyAdapter(getActivity(),postdate,name,photo, caption, id, likes, likeby, commentBy, comment);
        setListAdapter(adapter);
        /*feed=(ListView)view.findViewById(R.id.photofeeds);
        feed.setAdapter(adapter);
        feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ckeck click","done"+position);
            }
        });*/

        return view;
    }
}