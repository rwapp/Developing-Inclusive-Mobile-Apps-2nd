package com.mobilea11y.accessibilitytree

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.Lifecycle
import com.mobilea11y.accessibilitytree.lifecycle.ServiceLifecycleOwner
import com.mobilea11y.accessibilitytree.overlay.OverlayManager
import com.mobilea11y.accessibilitytree.util.AccessibilityTreeDumper

class AccessibilityTree : AccessibilityService() {

    private lateinit var overlay: OverlayManager
    private lateinit var lifecycleOwner: ServiceLifecycleOwner
    private val dumper = AccessibilityTreeDumper()

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit
    override fun onInterrupt() = Unit

    override fun onServiceConnected() {
        super.onServiceConnected()

        lifecycleOwner = ServiceLifecycleOwner().apply {
            performRestore()
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        overlay = OverlayManager(
            context = this,
            lifecycleOwner = lifecycleOwner,
            onTreeClick = {
                val root = rootInActiveWindow
                if (root != null) dumper.dump(root) else
                    Log.w("A11Y_TREE", "rootInActiveWindow is null")
            }
        )
        overlay.show()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        overlay.hide()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        overlay.hide()
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }
}