package test.com.newsApp;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import dagger.Module;
import dagger.Provides;
import test.com.newsApp.dataLayer.impl.DataLayerModule;
import test.com.newsApp.utils.PreferenceHelper;
import test.com.newsApp.viewModel.impl.ViewModelModule;

import javax.inject.Singleton;


@Module(includes = {DataLayerModule.class, ViewModelModule.class})

public class AppModule {
    private Application app;

    AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    PreferenceHelper providesHelper() {
        return new PreferenceHelper(app);
    }

    @Singleton
    @Provides
    Context providesContext() {
        return app;
    }

    @Singleton
    @Provides
    ImageLoader providesImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(context.getCacheDir()))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(config);
        return ImageLoader.getInstance();
    }
}

