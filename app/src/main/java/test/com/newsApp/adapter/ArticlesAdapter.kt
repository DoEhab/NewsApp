package test.com.newsApp.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.articles_list.view.*
import kotlinx.android.synthetic.main.fragment_article_details.*
import test.com.newsApp.R
import test.com.newsApp.R.id.img_article
import test.com.newsApp.model.Article
import test.com.newsApp.utils.AppDataHolder
import test.com.newsApp.utils.Utils

/**
 * Created by Doha on 14/06/19.
 */
class ArticlesAdapter (val context: Context?, var myImageLoader: ImageLoader, var availableArticles: ArrayList<Article>, val navigateToArticleDetails: (Article) -> Unit) : RecyclerView.Adapter<ArticlesAdapter.ItemsViewHolder>()
{

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.articles_list, parent, false))

    }

    // Binds each service in the ArrayList to a view
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {

        holder.view.tv_title.text = availableArticles[position].title
        if (AppDataHolder.getInstance().connectedToInternet)
        myImageLoader.displayImage(availableArticles[position].urlToImage, holder.view.img_article,
           Utils.getOptions()
        )else
        {
            val file = myImageLoader.diskCache.get(availableArticles[position].urlToImage);
            //Load image from cache
            holder.view.img_article.setImageURI(Uri.parse(file.absolutePath))
        }

        holder.view.setOnClickListener {
            navigateToArticleDetails.invoke(availableArticles[position])
        }

    }

    override fun getItemCount(): Int {

        return availableArticles.size

    }

    inner class ItemsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
