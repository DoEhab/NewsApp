package test.com.newsApp.dataLayer.impl;

import io.reactivex.Flowable;

import test.com.newsApp.ServiceCallsData;
import test.com.newsApp.dataLayer.HomeAPI;
import test.com.newsApp.service.HomeService;

public class HomeAPIImpl implements HomeAPI {
    private final HomeService service;

    HomeAPIImpl(HomeService service){
        this.service = service;
    }


    @Override
    public Flowable<ServiceCallsData.ArticlesResponse> getTopHeadlines(String country, int pageSize) {
        return service.getTopHeadlines(country, pageSize);
    }
}
