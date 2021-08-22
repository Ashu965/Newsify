package com.example.newsapp.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.NewsAdapter

import com.example.newsapi.R
import com.example.newsapi.models.Article
import com.example.newsapi.room.News
import com.example.newsapi.viewModel.NewsViewModel
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.util.Resource
import javax.inject.Inject





class BreakingNewsFragment  : Fragment(R.layout.fragment_breaking_news){
    lateinit var  recyclerView : RecyclerView
    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val QUERY_PAGE_SIZE = 10
    lateinit var  progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        recyclerView = view.findViewById(R.id.rvBreakingNews)
        progressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)
        newsAdapter = NewsAdapter(){
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addOnScrollListener(this@BreakingNewsFragment.scrollListener)

       /* newsAdapter.setOnItemClickListener {
            /*val bundle = Bundle().apply {
                putSerializable("article",it)
            }*/
           /* findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )*/
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
                findNavController().navigate(action)
        }*/

       /* viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
            newsAdapter.setNews(it)
        })*/
        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner , Observer { response ->
            when(response){
                is Resource.Loading -> showProgressBar()
                is Resource.Success -> {
                    hideProgressBar()
                 response.data?.let{newsResponse ->
                     val newsList = articleToNewsList(newsResponse.articles)
                     newsAdapter.setNews(newsList)
                     val totalPages = newsResponse.totalResults/QUERY_PAGE_SIZE + 2
                     isLastPage = viewModel.breakingNewsPage == totalPages
                 }
                }
                is Resource.Error -> {
                    response.message?.let{
                        hideProgressBar()
                        Log.i("Error", "An Error Occured : $it")
                    }
                }
            }

        })

    }
    private fun showProgressBar(){
       progressBar.visibility = ProgressBar.VISIBLE
       isLoading = true
    }
    private fun hideProgressBar(){
        progressBar.visibility = ProgressBar.INVISIBLE
        isLoading = false
    }
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotAtLastPage = !isLastPage && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBegining = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNotAtLastPage && isAtLastItem
                    && isNotAtBegining && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                viewModel.getBreakingNews()
                isScrolling = false
            }

        }
    }


  fun articleToNewsList(articles : List<Article>) : ArrayList<News>{
      val listOfNews = ArrayList<News>()
      for (article in articles) {
          listOfNews.add(
              News(
                  null,
                  article.title,
                  article.description,
                  article.urlToImage,
                  article.url,
                  article.source.name,
                  article.publishedAt
              )
          )
      }
      return listOfNews
  }

}