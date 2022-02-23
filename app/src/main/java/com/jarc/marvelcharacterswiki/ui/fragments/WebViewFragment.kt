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
import com.jarc.marvelcharacterswiki.databinding.FragmentWebViewBinding
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.let { WebViewFragmentArgs.fromBundle(it).url }

        if (url != null) {
            Log.d("url", url)

            binding.detailWebview.settings.apply {
                this.loadsImagesAutomatically = true
                this.javaScriptEnabled = true
            }

            binding.detailWebview.scrollIndicators = View.SCROLL_INDICATOR_BOTTOM

            binding.progressBarWebview.visibility = View.VISIBLE

            binding.detailWebview.webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBarWebview.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBarWebview.visibility = View.GONE
                }

            }
            binding.detailWebview.loadUrl(url)
        }

    }


}