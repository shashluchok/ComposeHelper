package ru.shashluchokdesign

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavigationHost {
    private var current by mutableStateOf(SampleScreens.Start)
    private var currentGroup: List<SampleScreens> = listOf()

    fun navigateTo(screen: SampleScreens) {
        current = screen
        currentGroup = getSameParentGroup(current)
    }

    fun getCurrentScreen(): SampleScreens {
        return current
    }

    fun goHome() {
        current = SampleScreens.Start
    }

    fun canGoUp(): Boolean {
        return current.parent != null
    }

    fun goUp() {
        if (current.parent != null) navigateTo(current.parent!!)
    }

    fun hasNextScreen(): Boolean {
        val currentIndex = currentGroup.indexOf(current)
        return (currentIndex < currentGroup.size - 1)
    }

    fun hasPreviousScreen(): Boolean {
        val currentIndex = currentGroup.indexOf(current)
        return (currentIndex > 0)
    }

    fun goNextScreen() {
        if (hasNextScreen()) {
            navigateTo(currentGroup[currentGroup.indexOf(current) + 1])
        }
    }

    fun goPreviousScreen() {
        if (hasPreviousScreen()) {
            navigateTo(currentGroup[currentGroup.indexOf(current) - 1])
        }
    }

    fun getChildren(screen: SampleScreens): List<SampleScreens> {
        return SampleScreens.values().filter {
            it.parent == screen
        }
    }

    private fun getSameParentGroup(screen: SampleScreens): List<SampleScreens> {
        return SampleScreens.values().filter {
            it.parent == screen.parent
        }
    }
}
