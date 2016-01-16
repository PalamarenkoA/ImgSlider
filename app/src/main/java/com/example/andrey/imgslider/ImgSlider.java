package com.example.andrey.imgslider;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.SeekBar;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.andrey.imgslider.fragment.FileFragment;
import com.example.andrey.imgslider.fragment.SlaiderFragment;

import java.util.HashMap;

import it.sephiroth.android.library.floatingmenu.FloatingActionItem;
import it.sephiroth.android.library.floatingmenu.FloatingActionMenu;

public class ImgSlider extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {




    public  final static String ANIMATION = "ANIMATION";
    public  final static String DURATION = "DURATION";
    public  final static String SLIDER = "SLIDER";
    public  final static int FILE_SLAIDS = 4;
    public  final static int DEFAULT_SLAIDS = 3;
    public  final static int HEDGEHOG_SLAIDS = 2;
    public  final static int DOG_SLAIDS = 1;
    public  final static int CAT_SLAIDS = 0;


    private SharedPreferences settings;


    private final int BUTTONANIMATION = 0;
    private final int BUTTONDURATION  = 1;
    private final int BUTTONFILE  = 2;
    private final int BUTTONAMANIMAL = 3;

    private final int FILEFRAGMENT = 0;
    private final int SLAIDERFRAGMENT = 1;
    private int howFragment = SLAIDERFRAGMENT;


    static public Context CONTEXT;
    private FloatingActionMenu mFloatingMenu;
    private FragmentTransaction fTrans;
    private SlaiderFragment slaiderFragment;
    private FileFragment fileFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_slider);
        CONTEXT = this;
        createFloatingActionButton();
        settings = getPreferences(MODE_PRIVATE);
        createFloatingMenu();
        slaiderFragment = new SlaiderFragment();
        fileFragment = new FileFragment();
        startFragment(slaiderFragment);


    }

    private void createFloatingActionButton() {
       fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howFragment == SLAIDERFRAGMENT) {

                    if (mFloatingMenu.getVisible()) {
                        mFloatingMenu.hide(true);
                    } else {
                        mFloatingMenu.show(true);
                    }
                } else {
                    SharedPreferences.Editor ed = settings.edit();
                    ed.putInt(SLIDER,FILE_SLAIDS);
                    ed.commit();
                    startFragment(slaiderFragment);


                }
            }
        });
    }

    private void createFloatingMenu() {
        FloatingActionItem item1 = new FloatingActionItem.Builder(CONTEXT, BUTTONANIMATION)
                .withResId(R.drawable.ic_jpg)
                .withDelay(0)
                .build();

        FloatingActionItem item2 = new FloatingActionItem.Builder(CONTEXT, BUTTONDURATION)
                .withResId(R.drawable.ic_time)
                .withDelay(5)
                .build();

        FloatingActionItem item3 = new FloatingActionItem.Builder(CONTEXT, BUTTONFILE)
                .withResId(R.drawable.ic_file)
                .withDelay(10)
                .build();
        FloatingActionItem item4 = new FloatingActionItem.Builder(CONTEXT, BUTTONAMANIMAL)
                .withResId(R.drawable.ic_file)
                .withDelay(10)
                .build();



        mFloatingMenu = new FloatingActionMenu
                .Builder(this)
                .addItem(item1)
                .addItem(item2)
                .addItem(item3)
                .addItem(item4)
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


            }

        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case BUTTONANIMATION:
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
                Snackbar.make(fab, "Выбран режим - " + settings.getString(ANIMATION, "Default"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFloatingMenu.hide(true);
                builder.create();
                builder.show();
                break;

            case BUTTONDURATION:
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
                                Snackbar.make(fab, title, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
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
            case BUTTONFILE:
                mFloatingMenu.hide(true);
                if(howFragment == SLAIDERFRAGMENT){
                startFragment(fileFragment);
                    Snackbar.make(fab, "Укажите папку с изображениями", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                ;}else{
                startFragment(slaiderFragment);
                }

                break;

            case BUTTONAMANIMAL:
                final String[] animals = {"Котики", "Собачки", "Ежики", "Стандартные"};

                final AlertDialog.Builder builderAnim = new AlertDialog.Builder(this);
                builderAnim.setTitle("Выбери животное"); // заголовок для диалога
                builderAnim.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        HashMap<String,String> url_maps = new HashMap();
                        switch (item){
                            case CAT_SLAIDS:
                                url_maps.clear();
                                url_maps.put("Добрый котик","http://www.admlega.com/wp-content/uploads/2015/01/%D0%BA%D0%BE%D1%82%D0%B8%D0%BA.jpeg");
                                url_maps.put("Котик который умеет терпеть","http://animalworld.com.ua/images/2010/December/Foto/Kotiki/Kotiki_6.jpg");
                                url_maps.put("Котик который ждет","http://img-fotki.yandex.ru/get/6616/22680102.3/0_73901_ebeab762_XL.jpg");
                                url_maps.put("Котик который смог","https://v1.std3.ru/500/43/98/1452913949-4398d7b1adced150df97397837ee5640.jpeg");
                                url_maps.put("Котик устал","https://v1.std3.ru/25/f1/1452952107-25f12c1275a61beb2f5dead58be81081.jpeg");
                                url_maps.put("Котик бандит","https://v1.std3.ru/500/89/6f/1452938815-896f5d0ab4c76734296f8d1244d45629.jpeg");
                                url_maps.put(" You shall not pass!","https://v1.std3.ru/500/8c/ec/1452765414-8cec2b89436ec6d175ef9f7e9e226baa.jpeg");
                                if(howFragment == SLAIDERFRAGMENT){
                                    SharedPreferences.Editor ed = settings.edit();
                                    ed.putInt(SLIDER, CAT_SLAIDS);
                                    ed.commit();
                                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)).removeAllSliders();
                                    sliderSet(((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)), url_maps);
                                }
                                break;
                            case DOG_SLAIDS:
                                url_maps.clear();
                                url_maps.put("Собака которая умеет просить","http://oboi-dlja-stola.ru/file/1029/760x0/16:9/%D0%A1%D0%BC%D0%B5%D1%88%D0%BD%D0%B0%D1%8F-%D1%81%D0%BE%D0%B1%D0%B0%D0%BA%D0%B0.jpg");
                                url_maps.put("Собака подозревает","http://cs303415.vk.me/v303415490/1fa0/dvEJJkyssiM.jpg");
                                url_maps.put("Собака из 90тых","http://cs21.babysfera.ru/1/6/b/6/28389316.95295110.jpeg");
                                url_maps.put("День не удался","http://joinfo.ua/images/gallery/2015/08/3095_55bf559965cde.jpg");
                                url_maps.put("Собака путешественница","http://batona.net/uploads/posts/2011-04/1302249831_12.jpg");
                                if(howFragment == SLAIDERFRAGMENT){
                                    SharedPreferences.Editor ed = settings.edit();
                                    ed.putInt(SLIDER, DOG_SLAIDS);
                                    ed.commit();
                                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)).removeAllSliders();
                                    sliderSet(((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)), url_maps);
                                    break;
                                }
                            case HEDGEHOG_SLAIDS:
                                url_maps.clear();
                                url_maps.put("Зимний ежик", "https://pbs.twimg.com/profile_images/427123717932466176/ROaKcv7X_400x400.jpeg");
                                url_maps.put("Коварный ежик","http://s00.yaplakal.com/pics/pics_original/4/5/4/3050454.png");
                                url_maps.put("Ежик спортсмен","http://img.animal-photos.ru/hedgehogs/hedgehog_1.jpg");
                                url_maps.put("Ежик настолько крут","http://funreactor.ru/uploads/posts/2013-02/1360584393_crgj26smz-u.jpg");
                                url_maps.put("Ежик альбинос","http://static.ngs.ru/cache/market/4dc6de9ada99ebdd669fd4062471dac9_1364960384_1000_1000.jpg");
                                if(howFragment == SLAIDERFRAGMENT){
                                    SharedPreferences.Editor ed = settings.edit();
                                    ed.putInt(SLIDER, HEDGEHOG_SLAIDS);
                                    ed.commit();
                                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)).removeAllSliders();
                                    sliderSet(((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)), url_maps);
                                }
                                break;
                            case FILE_SLAIDS:
                                url_maps.clear();
                                url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
                                url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
                                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                                url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
                                if(howFragment == SLAIDERFRAGMENT){
                                    SharedPreferences.Editor ed = settings.edit();
                                    ed.putInt(SLIDER, DEFAULT_SLAIDS);
                                    ed.commit();
                                    ((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)).removeAllSliders();
                                    sliderSet(((SliderLayout) slaiderFragment.getView().findViewById(R.id.slider)), url_maps);
                                }
                                  }





                        builderAnim.create();



                    }
                });
                Snackbar.make(fab, "Выбран режим - " + settings.getString(ANIMATION, "Default"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFloatingMenu.hide(true);

                builderAnim.show();
                break;

        }
        return null;
    }


    private void startFragment(Fragment fragment){
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.linearLayout1, fragment);
        fTrans.commit();
        if(howFragment ==SLAIDERFRAGMENT){
            howFragment = FILEFRAGMENT;
        }else{
            howFragment = SLAIDERFRAGMENT;
        }
    }

    private void sliderSet(SliderLayout sliderLayout, HashMap <String,String> hashMap){
        for(String name : hashMap.keySet()){
            TextSliderView textSliderView = new TextSliderView(ImgSlider.CONTEXT);
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

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

