package com.depromeet.fragraph.feature.home.model

import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class ReportUiModel(
    val playCount: String,
    val titles: List<IncenseTitle>,
    val counts: List<Float>,
)

fun getDefaultReportUiModel(): ReportUiModel =
    ReportUiModel(
        "0",
        listOf(
            IncenseTitle.LAVENDER,
            IncenseTitle.PEPPERMINT,
            IncenseTitle.SANDALWOOD,
            IncenseTitle.ORANGE,
            IncenseTitle.EUCALYPTUS,
        ),
        listOf(0f, 0f, 0f, 0f, 0f),
    )