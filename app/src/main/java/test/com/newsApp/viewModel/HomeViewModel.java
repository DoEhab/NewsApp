package test.com.newsApp.viewModel;


import io.reactivex.Flowable;
import test.com.newsApp.ServiceCallsData;


public interface HomeViewModel {
    Flowable<ServiceCallsData.ArticlesResponse> getTopHeadlines(String country, int pageSize);


}
