package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.Meditation

interface MeditationRepository {
    fun setMeditation(meditation: Meditation)
    fun getMeditation(): Meditation?
}