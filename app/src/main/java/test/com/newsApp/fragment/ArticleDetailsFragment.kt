package test.com.newsApp.fragment


import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.nostra13.universalimageloader.utils.MemoryCacheUtils
import kotlinx.android.synthetic.main.fragment_article_details.*
import test.com.newsApp.NewsApp
import test.com.newsApp.R
import test.com.newsApp.model.Article
import test.com.newsApp.utils.AppDataHolder
import test.com.newsApp.utils.Constants
import javax.inject.Inject
import android.net.Uri
import test.com.newsApp.R.id.*

/**
 * A simple fragment that receives article object and use it to view the content and date of the article
 *
 */
class ArticleDetailsFragment : NewsAppBaseFragment() {

    private lateinit var articleDetails:Article
    @Inject
    lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            articleDetails = it.getSerializable(Constants.ARTICLE_DETAILS) as Article
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = activity?.application as NewsApp
        application.getComponent().inject(this)
        hideToolbarWithBackBtn()
        if(AppDataHolder.getInstance().connectedToInternet)
        imageLoader.displayImage(articleDetails.urlToImage, img_article)
        else
        {
            val file = imageLoader.diskCache.get(articleDetails.urlToImage);
            //Load image from cache
            img_article.setImageURI(Uri.parse(file.absolutePath))
        }


        tv_bar_title.text=articleDetails.source.name
        tv_article_date.append(articleDetails.publishedAt.substringBefore("T"))
        tv_article_title.text=articleDetails.title
        tv_article_content.text=articleDetails.content

        img_share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, articleDetails.url);
            startActivity(Intent.createChooser(shareIntent,getString(R.string.share_article)))
        }
    }
    companion object {
        fun newInstance(selectedArticle: Article): ArticleDetailsFragment {
            val fragment = ArticleDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constants.ARTICLE_DETAILS, selectedArticle)
            fragment.arguments = bundle
            return fragment
        }
    }
}
