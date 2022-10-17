package com.weiran.updoot.puck

import com.weiran.updoot.puck.MyUtils.Configuration.Edges

class MyUtils {

    /**
     * Define behaviour of the Composable
     */
    sealed class Behaviour {
        object FreeForm : Behaviour()
        data class Sticky(var config: Configuration = Edges) : Behaviour()
        data class Gravity(var circle: Circle) : Behaviour()
    }

    sealed class Configuration {
        object Edges : Configuration()
        object Corners : Configuration()
        object VerticalEdges : Configuration()
        object HorizontalEdges : Configuration()
    }

    sealed class Edge {
        object Top : Edge()
        object Bottom : Edge()
        object Right : Edge()
        object Left : Edge()
    }
}