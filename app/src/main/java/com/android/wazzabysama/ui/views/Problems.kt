package com.android.wazzabysama.ui.views



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.views.model.ConstValue
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun Problems(
    scope: CoroutineScope, drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    userViewModel: UserViewModel,
    publicMessageViewModel: PublicMessageViewModel
) {
    viewItem.value = ConstValue.problem
    DrawerAppBar(
        scope,
        drawerState,
        viewItem,
        publicMessageViewModel,
        userViewModel
    )
}