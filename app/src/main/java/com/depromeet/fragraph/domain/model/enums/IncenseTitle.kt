package com.depromeet.fragraph.domain.model.enums

import com.google.gson.annotations.SerializedName

enum class IncenseTitle {
    @SerializedName("LAVENDER")
    LAVENDER,
    @SerializedName("PEPPERMINT")
    PEPPERMINT,
    @SerializedName("SANDALWOOD")
    SANDALWOOD,
    @SerializedName("ORANGE")
    ORANGE,
    @SerializedName("EUCALYPTUS")
    EUCALYPTUS,

    // 빈 값 위해 넣어둠
    @SerializedName("EMPTY")
    EMPTY,
}