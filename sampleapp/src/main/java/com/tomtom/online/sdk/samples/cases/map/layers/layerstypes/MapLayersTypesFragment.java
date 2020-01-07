/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.map.layers.layerstypes;

import android.app.AlertDialog;

import com.tomtom.online.sdk.common.util.EnumUtils;
import com.tomtom.online.sdk.map.model.MapLayersType;
import com.tomtom.online.sdk.map.model.MapTilesType;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class MapLayersTypesFragment extends ExampleFragment<MapLayersTypesPresenter> implements MapLayersTypesView {

    @Override
    protected MapLayersTypesPresenter createPresenter() {
        return new MapLayersTypesPresenter();
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.map_layers_tiles_type);
        view.addOption(R.string.map_layers_layers_type);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        if (newValues[0]) {
            showMapTilesTypesDialog();
        } else if (newValues[1]) {
            showMapLayersTypesDialog();
        }
        optionsView.selectItem(0, false);
        optionsView.selectItem(1, false);
    }

    @Override
    public void showTilesStatus(String text) {
        getInfoBarView().show();
        getInfoBarView().hideLeftIcon();
        getInfoBarView().setLeftText(text);
    }

    private void showMapTilesTypesDialog() {
        String title = getString(R.string.map_layers_tiles_type_select);
        int selectedItem = presenter.getMapTilesType().ordinal();
        String[] items = EnumUtils.enumAsStringArray(MapTilesType.class, MapTilesType.values().length);
        showTypesDialog(title, items, selectedItem, which ->
                presenter.setMapTilesType(MapTilesType.values()[which]));
    }

    private void showMapLayersTypesDialog() {
        String title = getString(R.string.map_layers_layers_type_select);
        int selectedItem = presenter.getMapLayersType().ordinal();
        String[] items = EnumUtils.enumAsStringArray(MapLayersType.class, MapLayersType.values().length);
        showTypesDialog(title, items, selectedItem, which ->
                presenter.setMapLayersType(MapLayersType.values()[which]));
    }

    private void showTypesDialog(String title, String[] items, int selectedItem, TypesDialogItemClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        builder.setSingleChoiceItems(items, selectedItem, (dialog, which) -> {
            onClickListener.onClick(which);
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private interface TypesDialogItemClickListener {
        void onClick(int which);
    }

}
