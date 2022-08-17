package com.android.wazzabysama.ui.views.bottomnavigationviews.privatemessage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.android.wazzabysama.ui.views.model.ConstValue

@Composable
fun Conversation(navController: NavHostController,
                 viewItem: MutableLiveData<String>,) {
  viewItem.value = ConstValue.privateMessage
  Text(text = "Hello World Conversation")

}