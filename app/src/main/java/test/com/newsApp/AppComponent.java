package test.com.newsApp;

import dagger.Component;
import test.com.newsApp.activity.HomeActivity;
import test.com.newsApp.fragment.ArticleDetailsFragment;
import test.com.newsApp.fragment.HomeFragment;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(NewsApp app);
    void inject(HomeActivity homeActivity);
    void inject(HomeFragment homeFragment);
    void inject(ArticleDetailsFragment articleDetailsFragment);
}
