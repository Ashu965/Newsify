 package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.Dagger.DaggerNewsComponent
import com.example.newsapi.Dagger.NewsComponent
import com.example.newsapi.NewsAdapter
import com.example.newsapi.R
import com.example.newsapi.viewModel.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject
 
 class NewsActivity : AppCompatActivity() {
    //val key = "store"
   // val db by lazy { AppDatabase.getInstance(this) }

   // val scope = CoroutineScope(Dispatchers.IO)
   // val handler = Handler(Looper.getMainLooper())

  //  lateinit var sharedPref: SharedPreferences
    lateinit var recyclerView: RecyclerView
  //  lateinit var contentResolverHelper: ContentMapper
    lateinit var localBroadcastManager: LocalBroadcastManager
    lateinit var adapter: NewsAdapter

    @Inject lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(findNavController(R.id.newsNavHostFragment))
       // recyclerView = findViewById<RecyclerView>(R.id.container)
       // recyclerView.layoutManager = LinearLayoutManager(this)


      // viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NewsViewModel::class.java)

        // val component = DaggerNewsComponent.builder().appModule(AppModule(application)).build()
        //component.inject(this)
        val appComponent : NewsComponent by lazy{ DaggerNewsComponent.factory().create(applicationContext)}
        appComponent.inject(this)


      /*  viewModel.getNewsList().observe(this, Observer { list ->
            list?.let {
                adapter = NewsAdapter(this, list)
                recyclerView.adapter = adapter
            }
        })*/
        /* contentResolverHelper = ContentMapper(this)
        sharedPref = getSharedPreferences(key, 0)
        recyclerView = findViewById<RecyclerView>(R.id.container)
        recyclerView.layoutManager = LinearLayoutManager(this)
        localBroadcastManager = LocalBroadcastManager.getInstance(this)


        val context = this
        val newsDao = viewModel.allNews
        var allnews = listOf<News>()
        // lifecycleScope.launch{  allnews = newsDao.getAll()}

        // if (allnews.isEmpty()) {
        /* if(!sharedPref.contains(key)){
              makeNetworkCall()
        }
        else {
              /* lifecycleScope.launch {
                val allNews = db.newsDao().getAll()
                recyclerView.adapter = NewsAdapter(allNews)
            }*/
              //  val allNews = db.newsDao().getAll()
             loadFromMemory()
          }
         doAutoRefresh()
    }*/
        allnews = contentResolverHelper.allNews
        if (allnews.isEmpty()) {
            makeNetworkCall()
        } else {
             loadFromMemory()
        }
        doAutoRefresh()
    }

    fun doAutoRefresh(){
        handler.postDelayed(object : Runnable{
            override fun run() {
              //  sharedPref.edit().clear()
                contentResolverHelper.deleteAll()
                makeNetworkCall()
                doAutoRefresh()
            }

        },5000)
    }
    fun makeNetworkCall(){
        val URL = "https://newsapi.org/"
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder().baseUrl(URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()

        val newsInterface = retrofit.create(NewsAPI::class.java)


        val callNewsList = newsInterface.getListOfMovies()
        callNewsList.enqueue(object : Callback<NewsAPIResponse> {
            override fun onFailure(call: Call<NewsAPIResponse>?, t: Throwable?) {
                Log.d("api", t?.message.toString())
            }

            override fun onResponse(
                    call: Call<NewsAPIResponse>?,
                    response: Response<NewsAPIResponse>?
            ) {
                val intent = Intent()
                intent.action = "ashu"
                intent.addCategory(Intent.CATEGORY_APP_FILES)
                localBroadcastManager.sendBroadcast(intent)
                val listOfArticles = response?.body()?.articles
                val listOfNews = ArrayList<News>()
                for(article in response?.body()?.articles!!) {
                    listOfNews.add(News( article.title, article.description, article.urlToImage,article.url))
                    contentResolverHelper.insertNews(News( article.title, article.description, article.urlToImage,article.url))
                }
                //lifecycleScope.launch {
                //newsDao.insert(listOfNews)}
                /*val data = Gson().toJson(listOfNews)
                val editor = sharedPref.edit()
                editor.putString(key,data).apply()*/

                recyclerView.adapter = NewsAdapter(listOfNews)
                Log.d("api", listOfArticles?.size.toString())
            }

        })
    }
    fun loadFromMemory(){
        /*val data = sharedPref.getString(key, "zero")
        val allNews = Gson().fromJson(data, Array<News>::class.java).asList()*/
        val allNews = contentResolverHelper.allNews
        recyclerView.adapter = NewsAdapter(allNews)
    }*/


    }
}