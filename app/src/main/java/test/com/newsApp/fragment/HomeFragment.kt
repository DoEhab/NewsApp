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
import test.com.newsApp.viewModel.HomeViewModel
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : NewsAppBaseFragment() {
    @Inject
    lateinit var viewModel: HomeViewModel
    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var communicator: HomeFragment.TransitionInterface? = null

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
        //call get top headlines webservice
        getTopHeadlinesArticles()
        layout_refresh.setOnRefreshListener {
            getTopHeadlinesArticles()
            layout_refresh.isRefreshing=false
        }
        btn_reload.setOnClickListener {
            getTopHeadlinesArticles()

        }

    }

    private fun getTopHeadlinesArticles() {
        showProgressDialog()
        val subscription = viewModel.getTopHeadlines("us", 25).subscribe(
            {
                dismissProgressDialog()
                if (it.totalResults > 0) {
                    layout_no_content.visibility=View.GONE
                    rv_articles.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rv_articles.adapter = ArticlesAdapter(context!!, imageLoader, it.articles, {
                        communicator?.launchFragment(ArticleDetailsFragment.newInstance(it), true)
                    })
                } else {

                    layout_no_content.visibility = View.VISIBLE
                }
            },
            {
                dismissProgressDialog()
                layout_no_content.visibility = View.VISIBLE

            }
        )
        compositeDisposable?.add(subscription)
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
