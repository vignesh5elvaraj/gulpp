package com.gulpp.android.gulppapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.Global;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.fragments.MenuPageFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static String TAG_MAIN = "MainActivity" ;
    private static String TAG_PARSE = "PARSE_HANDLER" ;
    private static String TAG_SP = "TYPE_NAME" ;

    private List<com.gulpp.android.gulppapp.model.Menu> menuList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataForMenuPage();
    }

    private void loadDataForMenuPage() {

            menuList = new ArrayList<>();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
            query.include("Type");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        Log.i(TAG_PARSE, "fetch combos retrieval success");

                        try {
                            setupMenuList(objects);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        Log.i(TAG_PARSE, "fetch combos retrieval Failure");
                        Log.i(TAG_PARSE, e.getMessage());
                    }
                }
            });

        }

    private void showMenuPageActivity(List<com.gulpp.android.gulppapp.model.Menu> menuList) {


        getGlobal().setMenuList(menuList);
        goToActivity(MenuPageActivity.class);

    }


    private void setupMenuList(List<ParseObject> objects) throws ParseException {

        AsynchTaskRunner runner = new AsynchTaskRunner();
        runner.execute(objects);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private class AsynchTaskRunner extends AsyncTask< List<ParseObject> , String , List<com.gulpp.android.gulppapp.model.Menu> > {

        private List<com.gulpp.android.gulppapp.model.Menu> responseMenuList = new ArrayList<>();


        @Override
        protected List<com.gulpp.android.gulppapp.model.Menu> doInBackground(List<ParseObject>... objects) {

            List<ParseObject> responseObjects = objects[0];

            for (int i =0 ; i<responseObjects.size() ; i++) {
                com.gulpp.android.gulppapp.model.Menu menu = new com.gulpp.android.gulppapp.model.Menu();

                menu.setMenuImageURL(responseObjects.get(i).getString("imageUrl"));
                menu.setMenuType(getTypeForMenu(responseObjects.get(i)));
                menu.setMenuName(responseObjects.get(i).getString("Name"));
                try {
                    menu.setMenuItems(getItemsForMenu(responseObjects.get(i)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                menu.setMenuPrice(String.valueOf(responseObjects.get(i).getNumber("Price")));
                menu.setMenuId(responseObjects.get(i).getObjectId());
                Log.i(TAG_PARSE, "The object id " + responseObjects.get(i).getObjectId());

                 responseMenuList.add(menu);
            }

            return responseMenuList;
        }

        private List<String> getItemsForMenu(ParseObject object) throws ParseException {

            ParseRelation itemRelations = object.getRelation("Items");
            List<ParseObject> itemList = itemRelations.getQuery().find();

            List<String> menuItems = new ArrayList<>();

            for (int i=0 ; i<itemList.size() ; i++) {
                menuItems.add(itemList.get(i).getString("itemName"));
                Log.i(TAG_PARSE, menuItems.get(i));
            }
            return menuItems;
        }

        private String getTypeForMenu(ParseObject object) {

            ParseObject type ;
            type = object.getParseObject("Type");
            Log.i(TAG_SP, type.getString("TypeName"));
            return type.getString("TypeName");
        }


        @Override
        protected void onPostExecute(List<com.gulpp.android.gulppapp.model.Menu> result) {
            // execution of result of Long time consuming operation
            showMenuPageActivity(result);

        }

    }

}
