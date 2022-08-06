package com.android.wazzabysama.ui.views



import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.views.model.ConstValue
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun Problems(
    scope: CoroutineScope, drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    context: Any,
    userViewModel: UserViewModel,
    publicMessageViewModel: PublicMessageViewModel,
    listStatePublicMessage: LazyListState
) {
    viewItem.value = ConstValue.problem

    DrawerAppBar(
        scope,
        drawerState,
        stringResource(R.string.problematic_app),
        viewItem,
        context,
        publicMessageViewModel,
        userViewModel,
        listStatePublicMessage
    )
}