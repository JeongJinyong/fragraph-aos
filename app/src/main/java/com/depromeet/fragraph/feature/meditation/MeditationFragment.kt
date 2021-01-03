package com.depromeet.fragraph.feature.meditation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.SharedViewModel
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.core.ext.dpToPx
import com.depromeet.fragraph.core.player.Player
import com.depromeet.fragraph.core.ui.memo_dialog.MemoViewModel
import com.depromeet.fragraph.core.ui.select_dialog.*
import com.depromeet.fragraph.core.util.KeyboardHelper
import com.depromeet.fragraph.databinding.FragmentMeditationBinding
import com.depromeet.fragraph.feature.meditation.viewmodel.MeditationViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MeditationFragment: Fragment(R.layout.fragment_meditation) {

    private val meditationViewModel: MeditationViewModel by viewModels()

    private val memoViewModel: MemoViewModel by viewModels()

    private val selectDialogViewModel: SelectDialogViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var backType = BackType.OPEN_SESSION_OUT_DIALOG

    @Inject
    lateinit var player: Player

    @Inject
    lateinit var inputMethodManager: InputMethodManager

    lateinit var binding: FragmentMeditationBinding

    lateinit var keyboardHelper: KeyboardHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMeditationBinding.bind(view)
            .apply {
                vm = meditationViewModel
                lifecycleOwner = this@MeditationFragment
            }

        with(binding.ivPlayingMotion) {
            val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            this.startAnimation(rotation)
        }

        val memoBinding = binding.viewMemoDialog.apply {
            vm = memoViewModel
            lifecycleOwner = this@MeditationFragment
        }

        val selectDialogBinding = binding.viewSelectDialog.apply {
            vm = selectDialogViewModel
            lifecycleOwner = this@MeditationFragment
        }

        keyboardHelper = KeyboardHelper(binding.root) {
            val lp = memoBinding.root.layoutParams as ConstraintLayout.LayoutParams
            if (it > 0) {
                lp.bottomMargin = it + requireContext().dpToPx(16f).toInt()
            } else {
                lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.memo_bottom_margin)
            }
            memoBinding.root.layoutParams = lp
        }

        meditationViewModel.meditation.observe(viewLifecycleOwner) { meditation ->
            // 세팅이 완료되면 플레이어 스타트 (초단위로 playtime 지정되므로 1000 을 곱해서 넘김)
            player.setPlayer(meditation.music.url, meditation.playtime * 1000) {
                startPlayer()
            }
        }

        meditationViewModel.exitEvent.observe(viewLifecycleOwner, EventObserver {
            selectDialogViewModel.setDialogType(SelectDialogType.SESSION_OUT)
            blurBackground()
            meditationViewModel.openDialog(memoVisibility = false, selectDialogVisibility = true)
            backType = BackType.CLOSE_DIALOG
        })

        meditationViewModel.backEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })

        meditationViewModel.onBackgroundClickEvent.observe(viewLifecycleOwner, EventObserver {
            if (player.isPlaying()) {
                player.pause()
            } else {
                startPlayer()
            }
        })

        meditationViewModel.onMemoWritingClickEvent.observe(viewLifecycleOwner, EventObserver {
            memoViewModel.setMemoDefault(it.historyId, it.date)
            blurBackground()
            meditationViewModel.openDialog(memoVisibility = true, selectDialogVisibility = false)
            backType = BackType.CLOSE_DIALOG
        })

        meditationViewModel.onMemoBackgroundClickEvent.observe(viewLifecycleOwner, EventObserver {
            inputMethodManager.hideSoftInputFromWindow(memoBinding.etMemoContent.windowToken, 0)
            if (player.remainingTime() > 0) {
                when (it) {
                    SelectDialogType.HIDE_DIALOG -> {
                        Blurry.delete(binding.fragmentMeditationContainer)
                        meditationViewModel.closeDialog()
                    }
                    // Todo 저장하는게 잘 이해는 안감.... 다시 물어보자 !! (메모 있다가 배경 클릭(삭제? 저장?) / 뭔가 그냥 캐시이지 저장이 맞나??)
                    SelectDialogType.MEMO_ON_WRITING -> {
                        // 이 경우에는 메모를 저장
                        memoViewModel.onBgClick()
                    }
                    else -> {
                        meditationViewModel.openDialog(memoVisibility = false, selectDialogVisibility = true)
                        selectDialogViewModel.setDialogType(it)
                    }
                }
            }
        })

        memoViewModel.memoToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            sharedViewModel.showToastMessage(it)
        })

        memoViewModel.memoCloseEvent.observe(viewLifecycleOwner, EventObserver {
            meditationViewModel.closeDialog()
            inputMethodManager.hideSoftInputFromWindow(memoBinding.etMemoContent.windowToken, 0)
            Blurry.delete(binding.fragmentMeditationContainer)
            backType = BackType.OPEN_SESSION_OUT_DIALOG
        })

        selectDialogViewModel.onBtcClickEvent.observe(viewLifecycleOwner, EventObserver {
            Blurry.delete(binding.fragmentMeditationContainer)
            when (it) {
                SAVE_MEMO_DELETE -> {
                    meditationViewModel.closeDialog()
                }
                SAVE_MEMO_CONFIRM -> {
                    blurBackground()
                    meditationViewModel.openDialog(memoVisibility = true, selectDialogVisibility = false)
                }
                OUT_SESSION_SAVE -> {
                    sharedViewModel.showToastMessage(R.string.meditation_session_out_save_success)
                    findNavController().popBackStack()
                }
                OUT_SESSION_EXIT -> {
                    meditationViewModel.deleteHistory()
                    findNavController().popBackStack()
                }
                FINISH_SESSION_RIGHT -> {
                    findNavController().popBackStack()
                }
            }

        })
    }

    private fun blurBackground() {
        Blurry.with(context)
            .radius(12)
            .sampling(8)
            .color(Color.argb(24, 0, 0, 0))
            .animate(200)
            .onto(binding.fragmentMeditationContainer)
    }

    private fun startPlayer() {
        player.start()
        viewLifecycleOwner.lifecycleScope.launch {
            player.remainingTimeFlow()
                .collect {
                    when (it) {
                        Player.FINISHED -> {
                            blurBackground()
                            player.pause()
                            meditationViewModel.openDialog(memoVisibility = false, selectDialogVisibility = true)
                            selectDialogViewModel.setDialogType(SelectDialogType.SESSION_FINISH)
                            backType = BackType.FINISH_MEDITATION
                        }
                        Player.PAUSED -> {
                            // Nothing to do
                        }
                        else -> meditationViewModel.setRemainingTime(it)
                    }
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Timber.d("backType: $backType")
                when (backType) {
                    BackType.OPEN_SESSION_OUT_DIALOG -> {
                        selectDialogViewModel.setDialogType(SelectDialogType.SESSION_OUT)
                        blurBackground()
                        meditationViewModel.openDialog(memoVisibility = false, selectDialogVisibility = true)
                        backType = BackType.CLOSE_DIALOG
                    }
                    BackType.CLOSE_DIALOG -> {
                        meditationViewModel.closeDialog()
                        Blurry.delete(binding.fragmentMeditationContainer)
                        backType = BackType.OPEN_SESSION_OUT_DIALOG
                    }
                    BackType.FINISH_MEDITATION -> {
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        player.releasePlayer()
        keyboardHelper.dismiss()
        super.onDestroy()
    }

    override fun onDetach() {
        player.releasePlayer()
        super.onDetach()
    }

    enum class BackType() {
        OPEN_SESSION_OUT_DIALOG,
        CLOSE_DIALOG,
        FINISH_MEDITATION,
    }

    companion object {
        const val TAG = "MeditationFragment"
    }
}