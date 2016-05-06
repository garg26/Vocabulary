package com.example.kartikeya.vocabulary;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class SearchResultActivity extends ActionBarActivity{

    private DictionaryDatabase db;
    private Cursor word1,word2;
    public TextView textView,textView1;
    private static final String TAG = SearchResultActivity.class.getSimpleName();
    String [] myArray = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            db = new DictionaryDatabase(this);
            myArray = db.searchWord(query);
            textView = (TextView)findViewById(R.id.textView7);
            textView1 = (TextView)findViewById(R.id.textView8);

            if(myArray[0]==null)
            {
                textView1.setText(query+" :-");
                textView.setText("Coming Soon!!");
            }
            else {
                textView1.setText(myArray[0]+" :-");
                textView.setText(myArray[1]);
            }

        }
    }
}
