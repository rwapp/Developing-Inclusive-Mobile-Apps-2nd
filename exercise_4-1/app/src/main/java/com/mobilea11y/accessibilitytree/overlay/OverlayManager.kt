package com.mobilea11y.accessibilitytree.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.mobilea11y.accessibilitytree.lifecycle.ServiceLifecycleOwner

class OverlayManager(
    private val context: Context,
    private val lifecycleOwner: ServiceLifecycleOwner,
    private val onTreeClick: () -> Unit
) {
    private var overlayView: ComposeView? = null
    private val wm: WindowManager by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun show() {
        if (overlayView != null) return

        val lp = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }

        overlayView = ComposeView(context).apply {
            // Provide owners so Compose has a lifecycle, VM store, and saved state
            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeViewModelStoreOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(lifecycleOwner)

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                MaterialTheme {
                    TreeButtonLayout(onTreeClick)
                }
            }
        }

        wm.addView(overlayView, lp)
    }

    fun hide() {
        overlayView?.let { view ->
            wm.removeView(view)
            overlayView = null
        }
    }
}