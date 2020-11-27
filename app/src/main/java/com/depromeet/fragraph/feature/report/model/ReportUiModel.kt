package com.depromeet.fragraph.feature.report.model

import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class ReportUiModel(
    val playCount: String,
    val titles: List<IncenseTitle>,
    val counts: List<Float>,
)