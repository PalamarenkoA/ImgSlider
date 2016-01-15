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
import com.example.andrey.imgslider.ImgSlider;
import com.example.andrey.imgslider.R;

import java.util.HashMap;


public class SlaiderFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private SliderLayout sliderShow;
    private SharedPreferences settings;
    private final String ANIMATION = "ANIMATION";
    private final String DURATION = "DURATION";

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
        settings = getActivity().getPreferences(Context.MODE_PRIVATE);
        sliderShow = (SliderLayout) v.findViewById(R.id.slider);
        sliderShow.setPresetTransformer(settings.getString(ANIMATION,"Default"));
        sliderShow.setDuration(settings.getInt(DURATION,3)*1000);
        HashMap<String,String> url_maps = new HashMap();

        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        sliderSet(sliderShow,url_maps);





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
