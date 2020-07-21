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
package com.tomtom.online.sdk.samples.cases;

import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.routing.route.RouteSpecification;

public class MultiRoutesSpecificationAdapter {

    private final RouteSpecification routeSpecification;
    private RouteStyle routeStyle = RouteStyle.DEFAULT_ROUTE_STYLE;
    private String routeTag = "";
    private boolean primary;

    public MultiRoutesSpecificationAdapter(RouteSpecification routeSpecification) {
        this.routeSpecification = routeSpecification;
    }

    public RouteSpecification getRouteSpecification() {
        return routeSpecification;
    }

    public void setRouteStyle(RouteStyle routeStyle) {
        this.routeStyle = routeStyle;
    }

    public RouteStyle getRouteStyle() {
        return routeStyle;
    }

    public void setRouteTag(String routeTag) {
        this.routeTag = routeTag;
    }

    public String getRouteTag() {
        return routeTag;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isPrimary() {
        return primary;
    }
}
