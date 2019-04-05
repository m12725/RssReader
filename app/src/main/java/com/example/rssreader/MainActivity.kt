package com.example.rssreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Rss> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //　ローダーを呼び出す
        loaderManager.initLoader(1, null, this)
    }

    // ローダーが要求されたときに呼ばれる
    override fun onCreateLoader(id: Int, args: Bundle?) = RssLoader(this)

    //　ローダーがリセットされたときに呼ばれる
    override fun onLoaderReset(loader: Loader<Rss>?) {
        //特に何もしない
    }

    // ローダーで行った処理が狩猟したときに呼ばれる
    override fun onLoadFinished(loader: Loader<Rss>?, data: Rss?) {
            // 処理結果がnullではない場合
            if (data != null) {

                //　RecyclerViewをレイアウトから探す
                val recyclerView = findViewById<RecyclerView>(R.id.articles)

                // RSSの記事一覧のアダプター
                val adapter = ArticlesAdapter(this, data.articles) { article ->
                    // 記事をタップした時の処理
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(this, Uri.parse(article.link))
                }
                recyclerView.adapter = adapter

                //　グリッド表示するレイアウトマネージャー
                val layoutManager = GridLayoutManager(this, 2)

                recyclerView.layoutManager = layoutManager
            }
        }
    }