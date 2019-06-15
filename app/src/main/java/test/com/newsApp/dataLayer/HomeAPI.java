package test.com.newsApp.dataLayer;

import io.reactivex.Flowable;
import test.com.newsApp.ServiceCallsData;


public interface HomeAPI {
    Flowable<ServiceCallsData.ArticlesResponse> getTopHeadlines(String country, int pageSize);

}
