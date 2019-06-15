package test.com.newsApp.activity

import android.os.Bundle
import test.com.newsApp.NewsApp
import test.com.newsApp.R
import test.com.newsApp.fragment.HomeFragment

import test.com.newsApp.fragment.NewsAppBaseFragment

class HomeActivity : NewsAppBaseActivity(), HomeFragment.TransitionInterface{
    override fun launchFragment(fragment: NewsAppBaseFragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            transaction
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        } else {
            transaction.add(R.id.fragment_container, fragment).commitAllowingStateLoss()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        (application as NewsApp).getComponent().inject(this)

        launchFragment(HomeFragment(),false)
    }

}

