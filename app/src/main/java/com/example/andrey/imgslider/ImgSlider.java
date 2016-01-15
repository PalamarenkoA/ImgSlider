package com.example.andrey.imgslider;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import it.sephiroth.android.library.floatingmenu.FloatingActionItem;
import it.sephiroth.android.library.floatingmenu.FloatingActionMenu;

public class ImgSlider extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{


    private SliderLayout sliderShow;
    private SharedPreferences settings;
    private final String ANIMATION = "ANIMATION";
    private final String DURATION = "DURATION";
    private Context context;
    private FloatingActionMenu  mFloatingMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_slider);
        context = this;
        toolbarAndFab();
        settings = getPreferences(MODE_PRIVATE);
        sliderShow = (SliderLayout) findViewById(R.id.slider);
        sliderShow.setPresetTransformer(settings.getString(ANIMATION,"Default"));
        sliderShow.setDuration(settings.getInt(DURATION,3)*1000);
        HashMap<String,String> url_maps = new HashMap<String, String>();

        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        sliderSet(sliderShow, url_maps);

        createFloatingMenu();



    }



























    private void sliderSet(SliderLayout sliderLayout, HashMap <String,String> hashMap){
    for(String name : hashMap.keySet()){
        TextSliderView textSliderView = new TextSliderView(this);
        // initialize a SliderLayout
        textSliderView
                .description(name)
                .image(hashMap.get(name))
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);

        //add your extra information
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle()
                .putString("extra", name);
        sliderLayout.addSlider(textSliderView);
}}
    private void toolbarAndFab(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {

                if(mFloatingMenu.getVisible()){
                mFloatingMenu.hide(true);}
                else{
                mFloatingMenu.show(true);
                }





            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
            final String[] mAnimName ={"Default", "Accordion", "Background2Foreground","CubeIn","DepthPage",
                        "Fade","FlipHorizontal","FlipPage","Foreground2Background","RotateDown",
                        "RotateUp","Stack","Tablet","ZoomIn","ZoomOutSlide","ZoomOut" };
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Выбран режим - " +settings.getString(ANIMATION,"Default") ); // заголовок для диалога
                builder.setItems(mAnimName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        SharedPreferences.Editor ed = settings.edit();
                        ed.putString(ANIMATION, mAnimName[item]);
                        ed.commit();
                        builder.setTitle("Выбран режим - " + settings.getString(ANIMATION, "Default"));
                        builder.create();

                        sliderShow.setPresetTransformer(settings.getString(ANIMATION,"Default"));
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                break;

            case 1:
                final AlertDialog.Builder builderd = new AlertDialog.Builder(this);
                final SeekBar seekBar = new SeekBar (this);

                String title;
                if(settings.getInt(DURATION,3) ==1){
                    title = "Смена изображения каждую секунду";
                }else{
                    title = "Смена изображения раз в " + settings.getInt(DURATION,3) + " секунд";
                }
                builderd.setTitle(title);

                builderd.setView(seekBar);

                seekBar.setMax(60);
                seekBar.setProgress(settings.getInt(DURATION, 3));

                builderd.setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences.Editor ed = settings.edit();
                                ed.putInt(DURATION, seekBar.getProgress());
                                ed.commit();
                                String title;
                                if(settings.getInt(DURATION,3) ==1){
                                    title = "Смена изображения каждую секунду";
                                }else{
                                    title = "Смена изображения раз в " + settings.getInt(DURATION,3) + " секунд";
                                }
                                builderd.setTitle(title);
                                builderd.create();
                                sliderShow.setDuration(settings.getInt(DURATION, 3) * 1000);
                                Toast.makeText(context, String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                builderd.create();
                builderd.show();
           break;
        }
        return null;
    }

private void createFloatingMenu(){
    FloatingActionItem item1 = new FloatingActionItem.Builder(context,0)
            .withResId(R.drawable.ic_action)
            .withDelay(0)

            .build();

    FloatingActionItem item2 = new FloatingActionItem.Builder(context,1)
            .withResId(R.drawable.ic_add)
            .withDelay(5)

            .build();

    FloatingActionItem item3 = new FloatingActionItem.Builder(context,2)
            .withResId(R.drawable.ic_back)
            .withDelay(10)


            .build();

    mFloatingMenu = new FloatingActionMenu
            .Builder(this)
            .addItem(item1)
            .addItem(item2)
            .addItem(item3)
             .withThreshold(R.dimen.floating_action_item_elevation_pressed)
            .withGap(R.dimen.floating_action_item_elevation_pressed)
            .withHorizontalPadding(R.dimen.floating_action_item_elevation_pressed)
            .withVerticalPadding(R.dimen.floating_action_item_elevation_pressed)
            .withGravity(FloatingActionMenu.Gravity.CENTER_HORIZONTAL | FloatingActionMenu.Gravity.BOTTOM)
            .withDirection(FloatingActionMenu.Direction.Horizontal)
            .animationDuration(300)
            .animationInterpolator(new OvershootInterpolator(0))
            .visible(true)
            .build();
        mFloatingMenu.hide(false);
        mFloatingMenu.setOnItemClickListener(new FloatingActionMenu.OnItemClickListener() {
        @Override
        public void onItemClick(FloatingActionMenu floatingActionMenu, int i) {
            showDialog(i);
            Toast.makeText(context, "item click " + i, Toast.LENGTH_SHORT).show();


            }

    });
}







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_img_slider, menu);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
