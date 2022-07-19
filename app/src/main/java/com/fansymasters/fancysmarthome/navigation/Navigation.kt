package com.fansymasters.fancysmarthome.navigation

sealed class Navigation(val destination: String) {
    object MainScreen: Navigation("MAIN")
    object Details: Navigation("DETAILS")
}