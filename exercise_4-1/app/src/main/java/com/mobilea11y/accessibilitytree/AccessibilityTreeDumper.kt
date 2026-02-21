package com.mobilea11y.accessibilitytree.util

import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.util.ArrayDeque

class AccessibilityTreeDumper {

    private val tag = "A11Y_TREE"

    fun dump(root: AccessibilityNodeInfo) {
        val queue: ArrayDeque<Pair<AccessibilityNodeInfo, Int>> = ArrayDeque()
        queue.add(AccessibilityNodeInfo.obtain(root) to 0)

        var nodeIndex = 0
        val rect = Rect()

        while (queue.isNotEmpty()) {
            val (node, depth) = queue.removeFirst()
            nodeIndex++

            // Enqueue children (obtain copies we will later recycle)
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { child ->
                    queue.addLast(AccessibilityNodeInfo.obtain(child) to (depth + 1))
                    child.recycle()
                }
            }

            val sb = StringBuilder()
            val treePrefix = buildTreePrefix(depth)
            sb.appendLine("$treePrefix Node #$nodeIndex")
            sb.appendLine(indent(depth) + "class=${node.className} pkg=${node.packageName}")

            node.getBoundsInScreen(rect)
            sb.appendLine(indent(depth) + "bounds=$rect")
            sb.appendLine(indent(depth) + "children=${node.childCount}")

            val text = node.text?.toString()?.trim().orEmpty().takeIf { it.isNotEmpty() }
            val desc = node.contentDescription?.toString()?.trim().orEmpty().takeIf { it.isNotEmpty() }
            val hint = if (android.os.Build.VERSION.SDK_INT >= 26) node.hintText?.toString()?.trim() else null
            val stateDesc = if (android.os.Build.VERSION.SDK_INT >= 30) node.stateDescription?.toString()?.trim() else null
            val roleDesc = node.extras?.getCharSequence("AccessibilityNodeInfo.roleDescription")?.toString()

            if (text != null) sb.appendLine(indent(depth) + "text=\"$text\"")
            if (desc != null) sb.appendLine(indent(depth) + "contentDesc=\"$desc\"")
            if (!hint.isNullOrEmpty()) sb.appendLine(indent(depth) + "hint=\"$hint\"")
            if (!roleDesc.isNullOrEmpty()) sb.appendLine(indent(depth) + "role=\"$roleDesc\"")
            if (!stateDesc.isNullOrEmpty()) sb.appendLine(indent(depth) + "stateDesc=\"$stateDesc\"")

            val flags = buildList {
                if (node.isClickable) add("clickable")
                if (node.isLongClickable) add("longClickable")
                if (node.isFocusable) add("focusable")
                if (node.isFocused) add("focused")
                if (node.isCheckable) add("checkable")
                if (node.isChecked) add("checked")
                if (node.isSelected) add("selected")
                if (node.isEnabled) add("enabled") else add("disabled")
                if (node.isPassword) add("password")
                if (node.isScrollable) add("scrollable")
            }
            if (flags.isNotEmpty()) {
                sb.appendLine(indent(depth) + "flags=${flags.joinToString(", ")}")
            }

            node.rangeInfo?.let { r ->
                val type = when (r.type) {
                    AccessibilityNodeInfo.RangeInfo.RANGE_TYPE_INT -> "int"
                    AccessibilityNodeInfo.RangeInfo.RANGE_TYPE_FLOAT -> "float"
                    AccessibilityNodeInfo.RangeInfo.RANGE_TYPE_PERCENT -> "percent"
                    else -> r.type.toString()
                }
                sb.appendLine(indent(depth) + "range=$type ${r.min}..${r.max} current=${r.current}")
            }

            node.collectionInfo?.let { c ->
                sb.appendLine(
                    indent(depth) + "collection rows=${c.rowCount} cols=${c.columnCount} hierarchical=${c.isHierarchical}"
                )
            }

            node.collectionItemInfo?.let { ci ->
                sb.appendLine(
                    indent(depth) + "item row=${ci.rowIndex}..${ci.rowIndex + ci.rowSpan - 1} " +
                            "col=${ci.columnIndex}..${ci.columnIndex + ci.columnSpan - 1} " +
                            "heading=${ci.isHeading} selected=${ci.isSelected}"
                )
            }

            val actions = node.actionList.map { formatAction(it) }
            if (actions.isNotEmpty()) {
                sb.appendLine(indent(depth) + "actions=${actions.joinToString()}")
            }

            Log.d(tag, sb.toString())
            node.recycle()
        }
    }

    private fun buildTreePrefix(depth: Int): String {
        if (depth <= 0) return "●"
        val mid = if (depth > 1) "│   ".repeat(depth - 1) else ""
        return mid + "├──"
    }

    private fun indent(depth: Int): String = "    ".repeat(depth)

    private fun formatAction(a: AccessibilityNodeInfo.AccessibilityAction): String {
        val id = a.id
        return when (id) {
            AccessibilityNodeInfo.ACTION_FOCUS -> "FOCUS"
            AccessibilityNodeInfo.ACTION_CLEAR_FOCUS -> "CLEAR_FOCUS"
            AccessibilityNodeInfo.ACTION_CLICK -> "CLICK"
            AccessibilityNodeInfo.ACTION_LONG_CLICK -> "LONG_CLICK"
            AccessibilityNodeInfo.ACTION_SELECT -> "SELECT"
            AccessibilityNodeInfo.ACTION_CLEAR_SELECTION -> "CLEAR_SELECTION"
            AccessibilityNodeInfo.ACTION_SCROLL_FORWARD -> "SCROLL_FWD"
            AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD -> "SCROLL_BACK"
            AccessibilityNodeInfo.ACTION_EXPAND -> "EXPAND"
            AccessibilityNodeInfo.ACTION_COLLAPSE -> "COLLAPSE"
            AccessibilityNodeInfo.ACTION_DISMISS -> "DISMISS"
            AccessibilityNodeInfo.ACTION_SET_TEXT -> "SET_TEXT"
            else -> a.label?.toString()?.uppercase() ?: "ACTION_$id"
        }
    }
}