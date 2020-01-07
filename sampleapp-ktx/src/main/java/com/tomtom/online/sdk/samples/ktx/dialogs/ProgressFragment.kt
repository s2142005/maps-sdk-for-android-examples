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
package com.tomtom.online.sdk.samples.ktx.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tomtom.sdk.examples.R

class ProgressFragment : DialogFragment() {

    init {
        isCancelable = false
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {

        const val PROGRESS_FRAGMENT_TAG = "PROGRESS_FRAGMENT"

        fun show(fragmentManager: FragmentManager) {
            val progressFragment = findProgressDialog(fragmentManager)
            if (progressFragment == null) {
                ProgressFragment().show(fragmentManager, PROGRESS_FRAGMENT_TAG)
            }
        }

        fun hide(fragmentManager: FragmentManager) {
            val progressFragment = findProgressDialog(fragmentManager)
            if (progressFragment != null && progressFragment is ProgressFragment) {
                progressFragment.dismiss()
            }
        }

        private fun findProgressDialog(fragmentManager: FragmentManager): Fragment? {
            return fragmentManager.findFragmentByTag(PROGRESS_FRAGMENT_TAG)
        }
    }

}