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
package com.tomtom.online.sdk.samples.cases.search.revgeo.polygon;

import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchResponse;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;

public class RevGeoWithAdpResponse {

    private final ReverseGeocoderSearchResponse revGeoResponse;
    private final AdditionalDataSearchResponse adpResponse;

    RevGeoWithAdpResponse(ReverseGeocoderSearchResponse response, AdditionalDataSearchResponse adpResponse) {
        this.revGeoResponse = response;
        this.adpResponse = adpResponse;
    }

    boolean isValid() {
        return revGeoResponse != null && adpResponse != null;
    }

    public AdditionalDataSearchResponse getAdpResponse() {
        return adpResponse;
    }

    public ReverseGeocoderSearchResponse getRevGeoResponse() {
        return revGeoResponse;
    }
}
