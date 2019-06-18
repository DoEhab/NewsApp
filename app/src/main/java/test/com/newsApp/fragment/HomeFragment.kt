package test.com.newsApp.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*

import test.com.newsApp.R
import test.com.newsApp.NewsApp
import test.com.newsApp.adapter.ArticlesAdapter
import test.com.newsApp.utils.PreferenceHelper
import test.com.newsApp.viewModel.HomeViewModel
import javax.inject.Inject
import com.google.gson.Gson
import test.com.newsApp.model.Article
import com.google.gson.reflect.TypeToken
import test.com.newsApp.R.id.*
import test.com.newsApp.utils.AppDataHolder
import test.com.newsApp.utils.ConnectivityHelper


/**
 * This fragments makes a service call to get the top headlines in USA.
 * Loads the result in recycler view and upon click on any item in the recycler view
 * it opens the details of the article
 *
 * 1- check if there is internet connection
 * if true
 * It makes the service call and save the result in shared preferences
 * 2- It loads the data in recycler view
 * 3- if there is no internet connection it loads the data from shared preferences
 *
 */
class HomeFragment : NewsAppBaseFragment() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    @Inject
    lateinit var viewModel: HomeViewModel
    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var communicator: HomeFragment.TransitionInterface? = null
    private var gson = Gson()
    private var myArticles = ArrayList<Article>()
    private var json: String? = null
    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeFragment.TransitionInterface) {
        } else {
            throw RuntimeException(context.toString() + "  Communicator error")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = activity?.application as NewsApp
        application.getComponent().inject(this)
        setToolbarWithoutBackBtn(getString(R.string.main_title))
        communicator = activity as TransitionInterface
        setClickListeners()
        if (AppDataHolder.getInstance().connectedToInternet) {
            //call get top headlines webservice
            getTopHeadlinesArticles()
        } else if(!AppDataHolder.getInstance().connectedToInternet && preferenceHelper.articles!=null) {
             layout_refresh.isRefreshing = false
             json=preferenceHelper.articles
             fillData()
             fillRecyclerView(myArticles)
        }else
         {
             layout_no_content.visibility=View.VISIBLE
         }
    }

    private fun setClickListeners() {

            layout_refresh.setOnRefreshListener {
                getTopHeadlinesArticles()
                layout_refresh.isRefreshing = false
            }
            btn_reload.setOnClickListener {
                getTopHeadlinesArticles()

            }

    }

    private fun getTopHeadlinesArticles() {
        if (AppDataHolder.getInstance().connectedToInternet) {
        showProgressDialog()
        val subscription = viewModel.getTopHeadlines("us", 15).subscribe(
            {
                dismissProgressDialog()
                if (it.totalResults > 0) {
                    json = gson.toJson(it.articles)
                    fillData()
                    fillRecyclerView(it.articles)
                } else {

                    layout_no_content.visibility = View.VISIBLE
                }
            },
            {
                dismissProgressDialog()
                if (json!!.isEmpty()) {
                    layout_no_content.visibility = View.VISIBLE
                } else {
                    fillRecyclerView(myArticles)
                }

            }
        )
        compositeDisposable?.add(subscription)
        }
    }

    private fun fillData() {
        preferenceHelper.articles = json
        val type = object : TypeToken<ArrayList<Article>>() {

        }.type
        myArticles = gson.fromJson(json, type)
    }

    private fun fillRecyclerView(articles: ArrayList<Article>) {
        layout_no_content.visibility = View.GONE
        rv_articles.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_articles.adapter = ArticlesAdapter(context!!, imageLoader, articles, {
            communicator?.launchFragment(ArticleDetailsFragment.newInstance(it), true)
        })
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable?.clear()
    }

    override fun onDestroy() {
        super.onDestroy();
        compositeDisposable?.dispose()
    }

    interface TransitionInterface {
        public fun launchFragment(fragment: NewsAppBaseFragment, addToBackStack: Boolean)
    }
}
