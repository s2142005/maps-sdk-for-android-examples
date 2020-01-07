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
package com.tomtom.online.sdk.samples.ktx.utils.arch

enum class LanguageSelector(private val asciiIcon: String, private val simpleName: String, val code: String) {

    EN("\uD83C\uDDEC\uD83C\uDDE7", "EN", "en-GB"),
    FR("\uD83C\uDDEB\uD83C\uDDF7", "FR", "fr-FR"),
    DE("\uD83C\uDDE9\uD83C\uDDEA", "DE", "de-DE"),
    ES("\uD83C\uDDEA\uD83C\uDDF8", "ES", "es-ES");

    val decoratedName: String
        get() = String.format("%s %s", asciiIcon, simpleName)
}