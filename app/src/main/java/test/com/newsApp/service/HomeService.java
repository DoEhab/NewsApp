package test.com.newsApp.service;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.com.newsApp.ServiceCallsData;

public interface HomeService {
    @GET("top-headlines")
    Flowable<ServiceCallsData.ArticlesResponse> getTopHeadlines(@Query("country") String country, @Query("pageSize") int pageSize);
}
