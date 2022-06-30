package com.daniel.weeklyassignment4.ui

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.daniel.weeklyassignment4.viewmodel.RandomJokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    protected val randomJokeViewModel: RandomJokeViewModel by activityViewModels()

    protected fun showError (error: Exception, retryAction:() -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("ERROR HAS OCCURRED")
            .setMessage(error.localizedMessage)
            .setPositiveButton("RETRY") { dialog, _ ->
                retryAction()
                dialog.dismiss()
            }
            .setNegativeButton("DISMISS") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()

    }
}