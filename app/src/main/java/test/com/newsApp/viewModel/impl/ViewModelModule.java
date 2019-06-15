package test.com.newsApp.viewModel.impl;

import dagger.Module;
import dagger.Provides;

import test.com.newsApp.dataLayer.HomeAPI;
import test.com.newsApp.viewModel.HomeViewModel;


import javax.inject.Singleton;

/**
 * Created by Doha on 03/09/18.
 */

@Module
public class ViewModelModule {
    @Provides
    @Singleton
    HomeViewModel provideLoginViewModel(HomeAPI api) {

        return new HomeViewModelImpl(api);
    }


}
