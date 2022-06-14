package com.android.wazzabysama.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class PublicMessageViewModel @Inject constructor(
    private val app: Application
): AndroidViewModel(app) {
}