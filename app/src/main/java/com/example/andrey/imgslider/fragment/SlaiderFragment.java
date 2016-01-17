package com.example.andrey.imgslider.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.andrey.imgslider.otherClass.DescriptionAnim;
import com.example.andrey.imgslider.otherClass.ImgFilter;
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
        sliderShow.setCustomAnimation(new DescriptionAnim());
        url_maps = new HashMap();
        url_maps.clear();

    switch (settings.getInt(ImgSlider.SLIDER,ImgSlider.DEFAULT_SLAIDS)){
        case ImgSlider.CAT_SLAIDS:
            url_maps.put("Добрый котик","http://img-fotki.yandex.ru/get/6313/15965808.b/0_8144b_4be1cfb9_XL.jpg");
            url_maps.put("Котик Рыжик","http://apikabu.ru/img_n/2012-06_4/j76.jpg");
            url_maps.put("Котик который ждет","https://i.ytimg.com/vi/rLtrrgQrt38/maxresdefault.jpg");
            url_maps.put("Котик который смог","https://v1.std3.ru/500/43/98/1452913949-4398d7b1adced150df97397837ee5640.jpeg");
            url_maps.put("Котик устал","https://v1.std3.ru/25/f1/1452952107-25f12c1275a61beb2f5dead58be81081.jpeg");
            url_maps.put("Котик бандит","https://v1.std3.ru/500/89/6f/1452938815-896f5d0ab4c76734296f8d1244d45629.jpeg");
            url_maps.put(" You shall not pass!", "https://v1.std3.ru/500/8c/ec/1452765414-8cec2b89436ec6d175ef9f7e9e226baa.jpeg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.DOG_SLAIDS:
            url_maps.put("Хороша чертовка","http://www.segodnya.ua/img/forall/users/532/53203/new_image4_188.jpg");
            url_maps.put("Мамин охотник", "http://morast.narod.ru/images/sobaka_s_ostrymi_ushami.jpg");
            url_maps.put("Собака из 90тых","http://cs21.babysfera.ru/1/6/b/6/28389316.95295110.jpeg");
            url_maps.put("День не удался","http://joinfo.ua/images/gallery/2015/08/3095_55bf559965cde.jpg");
            url_maps.put("Собака путешественница", "http://batona.net/uploads/posts/2011-04/1302249831_12.jpg");
            url_maps.put("Весь корм моооой", "http://animalworld.com.ua/images/2013/May/Foto/Fun/1/Fun_anim_8.jpg");
            url_maps.put("Вот такая вот посылка", "http://animalworld.com.ua/images/2015/January/Foto/Fun/3/Fun_anim-2.jpg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.SQUIRREL_SLAIDS:
            url_maps.put("Моя прелесть", "http://s00.yaplakal.com/pics/pics_original/7/6/4/1544467.jpg");
            url_maps.put("Местный вид спорта", "http://thekievtimes.ua/wp-content/uploads/2015/03/1212.jpg");
            url_maps.put("Белка за белку, так за основу взято", "http://www.torange.ru/photo/35/13/%D1%83%D1%88%D0%B8-%D0%B1%D0%B5%D0%BB%D0%BA%D0%B8-1415777507_97.jpg");
            url_maps.put("Белка летун", "http://nibler.ru/uploads/users/2015-11-13/belki-veselye-kartinki-koshki-sobaki-smeshnye-zhivotnye-kote_948177639.jpg");
            url_maps.put("О спорт, ты мир", "http://foto-zverey.ru/1/belka_18.jpg");
            url_maps.put("Папин бродяга, мамин симпатяга", "http://www.doodoo.ru/uploads/posts/2010-07/belka-16.jpg");
            url_maps.put("Жизнь во имя Нер'зула!", "http://cameralabs.org/media/camera/aprel/18aprel/23_3f4895088e79a8c04869957727b86b73.jpg");
            sliderSet(sliderShow, url_maps);
            break;
        case ImgSlider.DEFAULT_SLAIDS:
            HashMap<String,Integer> hashMapRes = new HashMap<>();
            hashMapRes.put("Обезьяна",R.drawable.img1);
            hashMapRes.put("Белка",R.drawable.img2);
            hashMapRes.put("Дикая кошка",R.drawable.img3);
            hashMapRes.put("Тигр",R.drawable.img4);
            hashMapRes.put("Слоненок",R.drawable.img5);

            sliderShow.removeAllSliders();
            sliderSetRes(sliderShow, hashMapRes);
            break;
        case ImgSlider.FILE_SLAIDS:
            try {

                if(settings.getString(ImgSlider.FILE,null) != null&& new File(settings.getString(ImgSlider.FILE,null)).listFiles(imgFilter).length>0) {
                    File file = new File(settings.getString(ImgSlider.FILE,null));
                    File[] ingList = file.listFiles(imgFilter);
                    url_maps.clear();
                    for(int i = 0; i<ingList.length;i++) {
                        Uri uri = Uri.fromFile(ingList[i]);
                        url_maps.put(ingList[i].getName(), uri.toString());
                    }
                    sliderSet(sliderShow, url_maps);}else{
                        HashMap<String,Integer> hashMapResF = new HashMap<>();
                        hashMapResF.put("Обезьяна",R.drawable.img1);
                        hashMapResF.put("Белка",R.drawable.img2);
                        hashMapResF.put("Дикая кошка", R.drawable.img3);
                        hashMapResF.put("Тигр",R.drawable.img4);
                        hashMapResF.put("Слоненок",R.drawable.img5);

                        sliderShow.removeAllSliders();
                        sliderSetRes(sliderShow, hashMapResF);
                    }

            }catch (Exception e){
                HashMap<String,Integer> hashMapResF = new HashMap<>();
                hashMapResF.put("Обезьяна",R.drawable.img1);
                hashMapResF.put("Белка",R.drawable.img2);
                hashMapResF.put("Дикая кошка", R.drawable.img3);
                hashMapResF.put("Тигр",R.drawable.img4);
                hashMapResF.put("Слоненок",R.drawable.img5);

                sliderShow.removeAllSliders();
                sliderSetRes(sliderShow, hashMapResF);

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
    private void sliderSetRes(SliderLayout sliderLayout, HashMap <String,Integer> hashMap){
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
