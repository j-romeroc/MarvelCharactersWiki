package com.jarc.marvelcharacterswiki.ui.fragments

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.jarc.marvelcharacterswiki.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.let { WebViewFragmentArgs.fromBundle(it).url}

        if (url != null) {
            Log.d("url", url)

            detail_webview.settings.apply {
                this.loadsImagesAutomatically = true
                this.javaScriptEnabled = true
            }

            detail_webview.scrollIndicators = View.SCROLLBARS_INSIDE_OVERLAY

            progressBar_webview.visibility = View.VISIBLE

            detail_webview.webViewClient = object: WebViewClient(){

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressBar_webview.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar_webview.visibility = View.GONE
                }

            }
            detail_webview.loadUrl(url)
        }




    }


}