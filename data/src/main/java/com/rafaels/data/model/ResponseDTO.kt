package com.rafaels.data.model

import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("topRightCorner")
    val topRightCorner: CoordinatesDTO,

    @SerializedName("roverPosition")
    val roverPosition: CoordinatesDTO,

    @SerializedName("roverDirection")
    val roverInitialDirection: String, //N,S,E,O

    @SerializedName("movements")
    val movements: String,//L, R, M
)


data class CoordinatesDTO(
    @SerializedName("x")
    val x: Int,
    @SerializedName("y")
    val y: Int,
)