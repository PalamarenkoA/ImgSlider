package com.example.andrey.imgslider;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.example.andrey.imgslider.fragment.FileFragment;
import com.example.andrey.imgslider.fragment.SlaiderFragment;

import it.sephiroth.android.library.floatingmenu.FloatingActionItem;
import it.sephiroth.android.library.floatingmenu.FloatingActionMenu;

public class ImgSlider extends AppCompatActivity {



    private SharedPreferences settings;
    private final String ANIMATION = "ANIMATION";
    private final String DURATION = "DURATION";
    private final int FILEFRAGMENT = 0;
    private final int SLAIDERFRAGMENT = 1;
    private int howFragment;
    static public Context CONTEXT;
    private FloatingActionMenu mFloatingMenu;
    private FragmentTransaction fTrans;
    private SlaiderFragment slaiderFragment;
    private FileFragment fileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_slider);
        CONTEXT = this;
        toolbarAndFab();
        settings = getPreferences(MODE_PRIVATE);
        createFloatingMenu();
        slaiderFragment = new SlaiderFragment();
        fileFragment = new FileFragment();
        fTrans = getFragmentManager().beginTransaction();

        fTrans.replace(R.id.linearLayout1, slaiderFragment);
        fTrans.commit();
        howFragment = SLAIDERFRAGMENT;

    }

    private void toolbarAndFab() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mFloatingMenu.getVisible()) {

                    mFloatingMenu.hide(true);
                } else {

                    mFloatingMenu.show(true);
                }


            }
        });
    }

    private void createFloatingMenu() {
        FloatingActionItem item1 = new FloatingActionItem.Builder(CONTEXT, 0)
                .withResId(R.drawable.ic_jpg)
                .withDelay(0)
                .build();

        FloatingActionItem item2 = new FloatingActionItem.Builder(CONTEXT, 1)
                .withResId(R.drawable.ic_time)
                .withDelay(5)

                .build();

        FloatingActionItem item3 = new FloatingActionItem.Builder(CONTEXT, 2)
                .withResId(R.drawable.ic_file)
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
                Toast.makeText(CONTEXT, "item click " + i, Toast.LENGTH_SHORT).show();


            }

        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                final String[] mAnimName = {"Default", "Accordion", "Background2Foreground", "CubeIn", "DepthPage",
                        "Fade", "FlipHorizontal", "FlipPage", "Foreground2Background", "RotateDown",
                        "RotateUp", "Stack", "Tablet", "ZoomIn", "ZoomOutSlide", "ZoomOut"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Выбран режим - " + settings.getString(ANIMATION, "Default")); // заголовок для диалога
                builder.setItems(mAnimName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        SharedPreferences.Editor ed = settings.edit();
                        ed.putString(ANIMATION, mAnimName[item]);
                        ed.commit();
                        builder.setTitle("Выбран режим - " + settings.getString(ANIMATION, "Default"));
                        builder.create();

                if(howFragment == SLAIDERFRAGMENT){
                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider))
                            .setPresetTransformer(settings.getString(ANIMATION, "Default"));
                }

                    }
                });

                mFloatingMenu.hide(true);
                builder.create();
                builder.show();
                break;

            case 1:
                final AlertDialog.Builder builderd = new AlertDialog.Builder(this);
                final SeekBar seekBar = new SeekBar(this);

                String title;
                if (settings.getInt(DURATION, 3) == 1) {
                    title = "Смена изображения каждую секунду";
                } else {
                    title = "Смена изображения раз в " + settings.getInt(DURATION, 3) + " секунд";
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
                                if (settings.getInt(DURATION, 3) == 1) {
                                    title = "Смена изображения каждую секунду";
                                } else {
                                    title = "Смена изображения раз в " + settings.getInt(DURATION, 3) + " секунд";
                                }
                                builderd.setTitle(title);
                                builderd.create();


                                if(howFragment == SLAIDERFRAGMENT) {
                                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider))
                                            .setDuration(settings.getInt(DURATION, 3) * 1000);
                                }}})

                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                mFloatingMenu.hide(true);
                builderd.create();
                builderd.show();
                break;
            case 2:
                mFloatingMenu.hide(true);
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.linearLayout1, fileFragment);
                fTrans.commit();
                howFragment = FILEFRAGMENT;

                break;
        }
        return null;
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

}

