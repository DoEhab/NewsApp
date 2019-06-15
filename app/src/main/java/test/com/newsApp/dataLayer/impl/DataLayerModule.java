package test.com.newsApp.dataLayer.impl;

import dagger.Module;
import dagger.Provides;

import test.com.newsApp.dataLayer.HomeAPI;
import test.com.newsApp.service.HomeService;
import test.com.newsApp.utils.RetrofitFactory;


import javax.inject.Singleton;


@Module
public class DataLayerModule {

    @Singleton
    @Provides
    HomeService providesHomeService() {
        return RetrofitFactory.createService(HomeService.class);
    }


    @Singleton
    @Provides
    HomeAPI providesHomeAPI(HomeService service) {
        return new HomeAPIImpl(service);
    }


}
