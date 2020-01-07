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
package com.tomtom.online.sdk.samples.cases.search;

import android.content.Context;

import com.google.common.base.Strings;
import com.tomtom.online.sdk.common.util.AndroidStringFormatter;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.search.data.common.Address;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;

public class AddressResponseFormatter implements AndroidStringFormatter<ReverseGeocoderSearchResponse> {

    final Context context;

    AddressResponseFormatter(Context context) {
        this.context = context;
    }

    @Override
    public String format(ReverseGeocoderSearchResponse response) {
        return getAddressFromResponse(response);
    }

    protected String getNoReverseGeocodingResultsMessage() {
        return getContext().getString(R.string.reverse_geocoding_no_results);
    }

    @Override
    public Context getContext() {
        return context;
    }

    protected String getAddressFromResponse(ReverseGeocoderSearchResponse response) {

        String result = getNoReverseGeocodingResultsMessage();

        if (!response.hasResults()) {
            return result;
        }

        Address address = response.getAddresses().get(0).getAddress();
        String freeformAddress = address.getFreeformAddress();
        if (!Strings.isNullOrEmpty(freeformAddress)) {
            result = freeformAddress;
        }

        return result;
    }
}
