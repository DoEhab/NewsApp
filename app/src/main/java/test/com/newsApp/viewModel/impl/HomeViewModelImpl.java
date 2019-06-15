package test.com.newsApp.viewModel.impl;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.com.newsApp.ServiceCallsData;
import test.com.newsApp.dataLayer.HomeAPI;
import test.com.newsApp.viewModel.HomeViewModel;


public class HomeViewModelImpl implements HomeViewModel {

    private final HomeAPI homeAPI;

    HomeViewModelImpl(HomeAPI api) {
        this.homeAPI = api;
    }


    @Override
    public Flowable<ServiceCallsData.ArticlesResponse> getTopHeadlines(String country, int pageSize) {
           return homeAPI.getTopHeadlines(country, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
}
