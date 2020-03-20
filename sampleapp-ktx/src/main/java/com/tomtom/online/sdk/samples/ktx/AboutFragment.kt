/*
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.common.collect.ImmutableList
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_about.*
import timber.log.Timber
import java.io.IOException
import java.util.*

class AboutFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewModel()
        confActivityCallback()
        confWebView()
        loadData()
    }

    private fun confWebView() {
        webView = about_content
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        viewModel.applyAboutButtonVisibility(false)
    }

    private fun confActivityCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, AboutOnBackPressedCallback())
    }

    private fun loadHeader(): String {
        return requireContext().resources.getString(R.string.license_header_text)
    }

    private fun loadData() {
        val sb = StringBuilder(HTML_TITLE)
        sb.append(loadHeader())
        sb.append(loadModules())
        sb.append(HTML_END)
        webView.loadDataWithBaseURL(BACKUP_URL, sb.toString(), MIME_TYPE, ENCODING, null)
    }

    private fun loadModules(): String {
        val modules: List<String> = getLicencesModules()
        val sb = StringBuilder()
        sb.append(String.format(MODULE_TITLE_FORMAT, MODULE_LICENSE_TITLE))
        modules.forEach { module ->
            sb.append(String.format(LINK_FORMAT, DIR_LICENSES, module, module.replace(HTML_NAME_SEPARATOR, " ")))
        }
        return sb.toString()
    }

    private fun getLicencesModules(): List<String> {
        val modules = ArrayList<String>()
        return try {
            requireContext().assets.list(DIR_LICENSES)?.let { licenseList ->
                licenseList.forEach { file ->
                    val module = file.replace(HTML_EXT, "")
                    Timber.d("module name loaded $module")
                    modules.add(module)
                }
            }
            ImmutableList.copyOf(modules)
        } catch (e: IOException) {
            Timber.e(e)
            ImmutableList.of()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.applyAboutButtonVisibility(true)
    }

    inner class AboutOnBackPressedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val back = webView.url.startsWith(FILE_RES)
            if (webView.canGoBack()) {
                webView.goBack()
                loadData()
            }
            if (!back) {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        private const val HTML_TITLE = "<html><body><h2>TomTom Maps SDK Examples</h2>"
        private const val HTML_END = "</body></html>"
        private const val HTML_EXT = ".html"
        private const val HTML_NAME_SEPARATOR = "_"
        private const val DIR_LICENSES = "licenses"
        private const val MODULE_LICENSE_TITLE = "Open source"
        private const val MODULE_TITLE_FORMAT = "<h3>%s</h3>"
        private const val BACKUP_URL = "https://developer.tomtom.com/maps-android-sdk/"
        private const val MIME_TYPE = "text/html; charset=utf-8"
        private const val ENCODING = "UTF-8"
        private const val FILE_RES = "file"
        private const val FILE_ASSET_URL = "$FILE_RES:///android_asset/"
        private const val LINK_FORMAT = "<a href=\"$FILE_ASSET_URL%s/%s$HTML_EXT\">%s</a><br/><br/>"
    }
}