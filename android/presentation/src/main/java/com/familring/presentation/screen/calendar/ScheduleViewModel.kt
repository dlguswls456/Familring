package com.familring.presentation.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.familring.domain.mapper.toProfile
import com.familring.domain.model.ApiResponse
import com.familring.domain.repository.ScheduleRepository
import com.familring.domain.repository.FamilyRepository
import com.familring.domain.request.ScheduleCreateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor(
        private val familyRepository: FamilyRepository,
        private val scheduleRepository: ScheduleRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ScheduleUiState())
        val uiState = _uiState.asStateFlow()

        private val _event = MutableSharedFlow<ScheduleUiEvent>()
        val event = _event.asSharedFlow()

        init {
            getFamilyMembers()
        }

        private fun getFamilyMembers() {
            viewModelScope.launch {
                familyRepository.getFamilyMembers().collect { result ->
                    when (result) {
                        is ApiResponse.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    familyProfiles = result.data.map { user -> user.toProfile() },
                                    familyMembers = result.data,
                                )
                            }
                        }

                        is ApiResponse.Error -> {
                            _event.emit(ScheduleUiEvent.Error(result.code, result.message))
                            Timber.d("code: ${result.code}, message: ${result.message}")
                        }
                    }
                }
            }
        }

        fun createSchedule(schedule: ScheduleCreateRequest) {
            viewModelScope.launch {
                scheduleRepository.createSchedule(schedule).collect { result ->
                    when (result) {
                        is ApiResponse.Success -> {
                            _event.emit(ScheduleUiEvent.Success)
                        }

                        is ApiResponse.Error -> {
                            _event.emit(ScheduleUiEvent.Error(result.code, result.message))
                            Timber.d("code: ${result.code}, message: ${result.message}")
                        }
                    }
                }
            }
        }

        fun updateSchedule(
            id: Long,
            schedule: ScheduleCreateRequest,
        ) {
            viewModelScope.launch {
                scheduleRepository.updateSchedule(id, schedule).collect { result ->
                    when (result) {
                        is ApiResponse.Success -> {
                            _event.emit(ScheduleUiEvent.Success)
                        }

                        is ApiResponse.Error -> {
                            _event.emit(ScheduleUiEvent.Error(result.code, result.message))
                            Timber.d("code: ${result.code}, message: ${result.message}")
                        }
                    }
                }
            }
        }
    }
