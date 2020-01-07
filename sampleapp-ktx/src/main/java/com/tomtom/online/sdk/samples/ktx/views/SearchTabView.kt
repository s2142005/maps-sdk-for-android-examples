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

package com.tomtom.online.sdk.samples.ktx.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout

class SearchTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    fun initSearchTabs(tabTitlesResId: IntArray? = null, tabTitles: Array<String>? = null) {
        tabTitlesResId?.apply {
            addSearchTabs(this)
        }
        tabTitles?.apply {
            addSearchTabs(this)
        }
    }

    fun setupTabSelectedListener(onTabSelected: (Tab) -> Unit) {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: Tab?) {
            }

            override fun onTabUnselected(tab: Tab?) {
            }

            override fun onTabSelected(tab: Tab) {
                selectTabAt(tab.position)
                onTabSelected(tab)
            }
        })
    }

    fun selectTabAt(position: Int) {
        getTabAt(position)?.select()
    }

    private fun addSearchTabs(tabTitlesResIds: IntArray) {
        tabTitlesResIds.forEach { titleResId ->
            newTab().apply {
                text = context!!.getString(titleResId)
                addTab(this, false)
            }
        }
    }

    private fun addSearchTabs(tabTitles: Array<String>) {
        tabTitles.forEach { tabTitle ->
            newTab().apply {
                text = tabTitle
                addTab(this, false)
            }
        }
    }

}