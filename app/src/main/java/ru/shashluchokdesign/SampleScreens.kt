package ru.shashluchokdesign

enum class SampleScreens(
    val title: String,
    val parent: SampleScreens? = null
) {
    Start(title = "Main Screen"),
    Components(title = "Components", parent = Start),
    TabGroup(title = "Tab group", parent = Components),
    Scrollbar(title = "Scrollbar", parent = Components)
}
