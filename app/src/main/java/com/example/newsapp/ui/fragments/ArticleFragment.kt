package com.example.newsapp.ui.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsapi.R
import com.example.newsapi.viewModel.NewsViewModel
import com.example.newsapp.ui.NewsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article){

   lateinit var viewModel : NewsViewModel
   val args : ArticleFragmentArgs by navArgs()


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

      super.onViewCreated(view, savedInstanceState)
      viewModel = (activity as NewsActivity).viewModel
      val news = args.news
      val webView = view.findViewById<WebView>(R.id.articleWebView)
      val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

      webView.apply {
         //Setting webviewclient so that page always load inside webview  and not on browser.
         webViewClient = WebViewClient()
         loadUrl(news.url)
      }

      fab.setOnClickListener {
         viewModel.saveArticle(args.news)
         Snackbar.make(view,"News saved Successfully!!!",Snackbar.LENGTH_SHORT).show()
      }

   }

}