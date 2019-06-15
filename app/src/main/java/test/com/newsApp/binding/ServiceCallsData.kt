package test.com.newsApp

import test.com.newsApp.model.Article

class ServiceCallsData {
    data class ArticlesResponse(var status: String, var totalResults:Int, var articles: ArrayList<Article>)
}