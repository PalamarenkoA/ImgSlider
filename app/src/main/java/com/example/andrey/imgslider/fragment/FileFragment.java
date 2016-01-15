package com.example.andrey.imgslider.fragment;


import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.andrey.imgslider.adapter.FileAdapter;
import com.example.andrey.imgslider.ImgSlider;
import com.example.andrey.imgslider.R;

import java.io.File;
import java.util.ArrayList;


public class FileFragment extends Fragment {
    public static File IMGFILE;
    private File currentDirectory = new File("/sdcard/");
    private FileAdapter arrayAdapter;
    private ArrayList<String> directoryList;
    private ListView listView;
    private ArrayList<String> fileList;
    private Button back;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_file, container, false);
        back = (Button) v.findViewById(R.id.button);
        listView = (ListView) v.findViewById(R.id.listView3);
        createListAndClicable(currentDirectory);

        return v;
    }

    private void createListAndClicable(final File file){
        if( file.isDirectory()) {
            fileList = new ArrayList();
            directoryList = new ArrayList();
            for (int i = 0; i < file.listFiles().length; i++) {
                directoryList.add(String.valueOf(file.listFiles()[i]));
                if(file.listFiles()[i].isFile()){
                    fileList.add(String.valueOf(file.listFiles()[i]));
                }
            }
            arrayAdapter = new FileAdapter(ImgSlider.CONTEXT,directoryList);
            listView.setAdapter(arrayAdapter);


        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(file.getPath().equals("/"))) {
                    createListAndClicable(file.getParentFile());
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                if (new File(directoryList.get(position)).isDirectory()) {
                    IMGFILE = new File(directoryList.get(position));
                    createListAndClicable(IMGFILE);

                }
            }

        });
    }



}