package test.com.newsApp.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import test.com.newsApp.R;

/**
 * Created by Doha on 15/06/19.
 */
public class Utils {
    public static DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .showImageOnFail(R.drawable.ic_launcher_foreground)
                .cacheInMemory(true)
                .cacheOnDisk(true)
               .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

}