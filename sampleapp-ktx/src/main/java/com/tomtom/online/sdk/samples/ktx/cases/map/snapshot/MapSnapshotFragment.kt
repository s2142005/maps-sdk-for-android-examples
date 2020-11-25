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
package com.tomtom.online.sdk.samples.ktx.cases.map.snapshot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.map.MapView
import com.tomtom.online.sdk.map.snapshot.SnapshotCallback
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_snapshot.*
import java.io.File
import java.io.FileOutputStream

class MapSnapshotFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_snapshot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    private fun confViewActions() {
        map_take_snapshot_btn.setOnClickListener {
            requireActivity().let { activity ->
                //tag::doc_map_snapshot_take_snapshot_fragment[]
                val mapFragment = activity.supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
                mapFragment.takeSnapshot(snapshotCallback)
                //end::doc_map_snapshot_take_snapshot_fragment[]
            }
        }
    }

    //tag::doc_map_snapshot_callback[]
    private val snapshotCallback = object : SnapshotCallback {
        override fun onSuccess(snapshot: Bitmap) {
            // The saveSnapshotToCache(snapshot) creates a file in the context.cacheDir, writes bitmap data
            // into it using snapshot.compress(CompressFormat.JPEG, 100, outputStream) then returns the file.
            // Before you can obtain URI of this file you need to define a FileProvider in the manifest.
            val snapshotFile = saveSnapshotToCache(snapshot)
            snapshotFile?.let {
                val snapshotUri = FileProvider.getUriForFile(requireContext(), FILE_PROVIDER_AUTHORITY, it)

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.type = "image/jpeg"
                shareIntent.putExtra(Intent.EXTRA_STREAM, snapshotUri)
                startActivity(Intent.createChooser(shareIntent, "Send map snapshot"))
            } ?: run { Toast.makeText(requireContext(), FILE_NOT_FOUND, Toast.LENGTH_SHORT).show() }
        }

        override fun onError(error: Throwable) {
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
    }
    //end::doc_map_snapshot_callback[]

    private fun saveSnapshotToCache(snapshot: Bitmap): File? {
        var snapshotFile: File? = null
        val cachePath = File(requireContext().cacheDir, SNAPSHOT_DIRECTORY)
        cachePath.mkdirs()
        FileOutputStream("$cachePath/$FILE_NAME").use {
            snapshot.compress(CompressFormat.JPEG, 100, it)
            snapshotFile = File("$cachePath/$FILE_NAME")
        }
        return snapshotFile
    }

    @Suppress("unused")
    private fun takeSnapshotOfMapView() {
        //tag::doc_map_snapshot_take_snapshot_mapview[]
        val mapView: MapView = requireActivity().findViewById(R.id.map_view)
        mapView.takeSnapshot(snapshotCallback)
        //end::doc_map_snapshot_take_snapshot_mapview[]
    }

    companion object {
        private const val FILE_NAME = "map_snapshot.png"
        private const val FILE_PROVIDER_AUTHORITY = "com.tomtom.online.sdk.examples.fileprovider"
        private const val FILE_NOT_FOUND = "Could not retrieve file from the cache directory."
        private const val SNAPSHOT_DIRECTORY = "snapshots"
    }
}