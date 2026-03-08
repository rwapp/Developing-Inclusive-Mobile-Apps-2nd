# Exercise 4-1: Accessibility Tree service

An Android accessibility service that prints the full accessibility tree of whatever is currently on screen to Logcat.

## What it does

When enabled, the service displays a small overlay button on screen. Tapping it captures the accessibility tree of the active window and logs each node's properties to Logcat under the tag `A11Y_TREE`.

Each node is printed with:
- Class name and package
- Screen bounds
- Child count
- Text, content description, hint, role description, and state description (where present)
- Flags: clickable, focusable, checkable, checked, selected, enabled, password, scrollable
- Range info (for sliders and progress indicators)
- Collection and collection item info (for lists and grids)
- Available accessibility actions

The tree is rendered with a visual prefix (e.g. `●`, `├──`) to show hierarchy depth.

## How to use

1. Build and install the app on a device or emulator.
2. Open **Settings > Accessibility > Downloaded apps** and enable *Accessibility Tree*.
3. Navigate to any app or screen you want to inspect.
4. Tap the overlay button to dump the current accessibility tree.
5. Open Logcat in Android Studio and filter by the tag `A11Y_TREE`.

## Relevant chapter

Chapter 4 — Jetpack Compose Accessibility Model

## Requirements

- Android Studio
- Min SDK: 28 (Android 9 / Pie)
- Target SDK: 36 (Android 16)
- Java 11