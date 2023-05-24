package com.topic3.android.reddit.routing

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner

private val localBackPressedDispatcher=
    staticCompositionLocalOf<OnBackPressedDispatcher?>{null}





@Composable
fun BackButtonHandler(
    enabled: Boolean =true,
    onBackPressed:()->Unit

){
    val dispatcher = localBackPressedDispatcher.current?:return
    val backCallBack=remember{
        object:OnBackPressedCallback(enabled){
            override fun handleOnBackPressed(){
                onBackPressed.invoke()
            }
        }

    }
    DisposableEffect(dispatcher){
        dispatcher.addCallback(backCallBack)
        onDispose{
            backCallBack.remove()
        }
    }
}

@Composable
fun BackButtonAction(onBackPressed: () -> Unit){
    CompositionLocalProvider(
        localBackPressedDispatcher provides (
                LocalLifecycleOwner.current as ComponentActivity
                ).onBackPressedDispatcher
    ) {
        BackButtonHandler() {
            onBackPressed.invoke()
        }
    }

}