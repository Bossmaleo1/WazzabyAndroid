package com.android.wazzabysama.ui.views



import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.wazzabysama.R
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun Problems(scope: CoroutineScope, drawerState: DrawerState) {
    DrawerAppBar(scope, drawerState,stringResource(R.string.problematic_app))
}