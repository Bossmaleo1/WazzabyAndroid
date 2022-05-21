package com.android.wazzabysama.ui.views



import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import com.android.wazzabysama.R
import com.android.wazzabysama.ui.views.utils.ConstValue
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable as rememberSaveable1

@Composable
@ExperimentalMaterial3Api
fun Problems(scope: CoroutineScope, drawerState: DrawerState, viewItem: MutableLiveData<String>, context: Any) {
    viewItem.value = ConstValue.problem
    DrawerAppBar(scope, drawerState,stringResource(R.string.problematic_app), viewItem, context)
}