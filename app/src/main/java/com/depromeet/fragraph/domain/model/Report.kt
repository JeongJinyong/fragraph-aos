package com.depromeet.fragraph.domain.model

import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class Report(
    val titles: List<IncenseTitle>,
    val counts: List<Float>,
)