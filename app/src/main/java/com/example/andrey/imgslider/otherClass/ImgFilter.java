package com.example.andrey.imgslider.otherClass;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by andrey on 15.01.16.
 */
public class ImgFilter implements FileFilter {

       public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return false;
        }

        if( pathname.getName().regionMatches(pathname.getName().length()-3,"jpg",0,3)){
            return true;
        }

        return false;
    }


}