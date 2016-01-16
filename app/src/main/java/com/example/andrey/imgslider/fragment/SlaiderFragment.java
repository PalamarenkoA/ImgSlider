package com.example.andrey.imgslider.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.andrey.imgslider.ImgFilter;
import com.example.andrey.imgslider.ImgSlider;
import com.example.andrey.imgslider.R;

import java.io.File;
import java.util.HashMap;


public class SlaiderFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private SliderLayout sliderShow;
    private SharedPreferences settings;
    private HashMap<String,String> url_maps;
    ImgFilter imgFilter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_slaider, container, false);
        imgFilter = new ImgFilter();
        settings = getActivity().getPreferences(Context.MODE_PRIVATE);
        sliderShow = (SliderLayout) v.findViewById(R.id.slider);
        sliderShow.setPresetTransformer(settings.getString(ImgSlider.ANIMATION,"Default"));
        sliderShow.setDuration(settings.getInt(ImgSlider.DURATION, 3) * 1000);
        url_maps = new HashMap();
        url_maps.clear();

    switch (settings.getInt(ImgSlider.SLIDER,ImgSlider.DEFAULT_SLAIDS)){
        case ImgSlider.CAT_SLAIDS:
            url_maps.put("Добрый котик","http://www.admlega.com/wp-content/uploads/2015/01/%D0%BA%D0%BE%D1%82%D0%B8%D0%BA.jpeg");
            url_maps.put("Котик который умеет терпеть","http://animalworld.com.ua/images/2010/December/Foto/Kotiki/Kotiki_6.jpg");
            url_maps.put("Котик который ждет","http://img-fotki.yandex.ru/get/6616/22680102.3/0_73901_ebeab762_XL.jpg");
            url_maps.put("Котик который смог","https://v1.std3.ru/500/43/98/1452913949-4398d7b1adced150df97397837ee5640.jpeg");
            url_maps.put("Котик устал","https://v1.std3.ru/25/f1/1452952107-25f12c1275a61beb2f5dead58be81081.jpeg");
            url_maps.put("Котик бандит","https://v1.std3.ru/500/89/6f/1452938815-896f5d0ab4c76734296f8d1244d45629.jpeg");
            url_maps.put(" You shall not pass!", "https://v1.std3.ru/500/8c/ec/1452765414-8cec2b89436ec6d175ef9f7e9e226baa.jpeg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.DOG_SLAIDS:
            url_maps.put("Собака которая умеет просить","http://oboi-dlja-stola.ru/file/1029/760x0/16:9/%D0%A1%D0%BC%D0%B5%D1%88%D0%BD%D0%B0%D1%8F-%D1%81%D0%BE%D0%B1%D0%B0%D0%BA%D0%B0.jpg");
            url_maps.put("Собака подозревает","http://cs303415.vk.me/v303415490/1fa0/dvEJJkyssiM.jpg");
            url_maps.put("Собака из 90тых","http://cs21.babysfera.ru/1/6/b/6/28389316.95295110.jpeg");
            url_maps.put("День не удался","http://joinfo.ua/images/gallery/2015/08/3095_55bf559965cde.jpg");
            url_maps.put("Собака путешественница", "http://batona.net/uploads/posts/2011-04/1302249831_12.jpg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.HEDGEHOG_SLAIDS:
            url_maps.put("Зимний ежик", "https://pbs.twimg.com/profile_images/427123717932466176/ROaKcv7X_400x400.jpeg");
            url_maps.put("Коварный ежик","http://s00.yaplakal.com/pics/pics_original/4/5/4/3050454.png");
            url_maps.put("Ежик спортсмен","http://img.animal-photos.ru/hedgehogs/hedgehog_1.jpg");
            url_maps.put("Ежик настолько крут","http://funreactor.ru/uploads/posts/2013-02/1360584393_crgj26smz-u.jpg");
            url_maps.put("Ежик альбинос", "http://static.ngs.ru/cache/market/4dc6de9ada99ebdd669fd4062471dac9_1364960384_1000_1000.jpg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.DEFAULT_SLAIDS:
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.FILE_SLAIDS:
            if(FileFragment.IMGFILE != null  && FileFragment.IMGFILE.listFiles(imgFilter).length>0) {

                File[] ingList = FileFragment.IMGFILE.listFiles(imgFilter);
                url_maps.clear();
                for(int i = 0; i<ingList.length;i++) {
                    Uri uri = Uri.fromFile(ingList[i]);
                    url_maps.put(ingList[i].getName(), uri.toString());
                }
                sliderSet(sliderShow, url_maps);}else{
                url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
                url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
                sliderSet(sliderShow, url_maps);
            }

    }









        return v;
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
