package com.depromeet.fragraph.feature.home.view.chart

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.depromeet.fragraph.R
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.model.GradientColor

// Todo 그래프 위 숫자 간격을 4~8 의 간격으로 띄울 것
//

class ReportChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): BarChart(context, attrs, defStyleAttr) {

    init {
        description.isEnabled = false
        setBackgroundColor(Color.argb(0, 250, 250, 250))
        setDrawGridBackground(false)
        setDrawBarShadow(false)
        setTouchEnabled(false)
        setScaleEnabled(false)
        isDragEnabled = false
        isHighlightFullBarEnabled = false
        extraBottomOffset = resources.getDimension(R.dimen.report_chart_extra_bottom) + 12f

        with(legend) {
            isEnabled = false
        }

        with(axisRight) {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setDrawLabels(false)
            axisMinimum = 0f // this replaces setStartAtZero(true)
        }

        with(axisLeft) {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setDrawLabels(false)
            axisMinimum = 0f // this replaces setStartAtZero(true)
        }

        with(xAxis) {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            textColor = context.getColor(R.color.colorBlackGray_4)
            textSize = 12f
            setDrawGridLines(false)
            setDrawAxisLine(false)
        }
    }

    fun setDataAndStyle(incenses: List<IncenseTitle>, playCounts: List<Float>) {
        val entries = mutableListOf<BarEntry>()
        if (incenses.size != playCounts.size) {
            return
        }

        for (index in incenses.indices) {
            entries.add(BarEntry(index.toFloat(), playCounts[index]))
        }

        val set = BarDataSet(entries, "Incenses").apply {
            val colors = incenses.map { getGradientColor(it) }
            gradientColors = colors
            valueTextSize = 12f
            valueTextColor = context.getColor(R.color.colorBlackGray_3)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
            setDrawValues(true)
        }

        val dataSets = mutableListOf<IBarDataSet>(set)
        dataSets.add(set)

        val barData = BarData(dataSets).apply {
            this.barWidth = 0.05f
            data = this
        }

        with(xAxis) {
            axisMinimum = -0.3f
            axisMaximum = barData.xMax + 0.3f
            labelCount = incenses.size
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return getXAxisName(incenses[value.toInt() % incenses.size])
                }
            }
        }
        setXAxisRenderer(ReportChartViewXAxisRenderer(viewPortHandler, xAxis, getTransformer(YAxis.AxisDependency.LEFT)))
    }

    private fun getGradientColor(incenseTitle: IncenseTitle): GradientColor { // startColor, endColor
        return when(incenseTitle) {
            IncenseTitle.LAVENDER -> GradientColor(context.getColor(R.color.incenseLavender), context.getColor(R.color.incenseLavender))
            IncenseTitle.PEPPERMINT -> GradientColor(context.getColor(R.color.incensePeppermint), context.getColor(R.color.incensePeppermint))
            IncenseTitle.SANDALWOOD -> GradientColor(context.getColor(R.color.incenseSandalwood), context.getColor(R.color.incenseSandalwood))
            IncenseTitle.ORANGE -> GradientColor(context.getColor(R.color.incenseOrange), context.getColor(R.color.incenseOrange))
            IncenseTitle.EUCALYPTUS -> GradientColor(context.getColor(R.color.incenseEucalyptus), context.getColor(R.color.incenseEucalyptus))
            else -> GradientColor(Color.WHITE, Color.CYAN)
        }
    }

    private fun getXAxisName(incenseTitle: IncenseTitle): String { // startColor, endColor
        return when(incenseTitle) {
            IncenseTitle.LAVENDER -> context.getString(R.string.report_x_lavender)
            IncenseTitle.PEPPERMINT -> context.getString(R.string.report_x_perppermint)
            IncenseTitle.SANDALWOOD -> context.getString(R.string.report_x_sandalwood)
            IncenseTitle.ORANGE -> context.getString(R.string.report_x_orange)
            IncenseTitle.EUCALYPTUS -> context.getString(R.string.report_x_eucalyptus)
            else -> incenseTitle.name
        }
    }
}