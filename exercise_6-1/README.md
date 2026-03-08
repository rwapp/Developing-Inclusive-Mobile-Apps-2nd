# Exercise 6-1: Semantic Views (iOS)

A SwiftUI exercise demonstrating how to improve the accessibility tree of a sample UI using SwiftUI's accessibility modifiers.

## Projects

| Folder | Description |
|--------|-------------|
| `exercise_6-1/` | Starter project — the UI as originally written |
| `exercise_6-1_fixed/` | Suggested solution applying accessibility improvements |

## The UI

Both projects display a row of app store–style statistics: a star rating, an age rating, and a chart position — each separated by a divider. The `AppDetail` view stacks a title, a prominent value, and a detail label inside a `VStack`.

## The problem

In the starter project, VoiceOver navigates each `Text` element within `AppDetail` as a separate accessibility element. This means users hear three separate announcements per statistic (e.g. *"1.6M ratings"*, *"4.9"*, *"⭐️⭐️⭐️⭐️⭐️"*), without a clear sense of how they relate to each other, and with no context for values like *"4.9"* on their own.

## The fix

The `exercise_6-1_fixed` project makes two changes:

- `accessibilityElement(children: .combine)` is added to `AppDetail`, merging the three text elements into a single accessibility element whose label is the concatenation of all child text.
- `accessibilityLabel(_:)` is applied to the ratings and chart entries with human-readable descriptions, replacing the raw concatenated label (e.g. *"1.6M RATINGS4.9⭐️⭐️⭐️⭐️⭐️"*) with *"4.9 stars from 1.6 million ratings"* and *"Travel chart number 1"*.

## Your turn

After reading Chapter 6, open the starter project and apply what you have learned to bring its accessibility tree in line with the fixed version.

## Relevant chapter

Chapter 6 — SwiftUI Accessibility Model

## Requirements

- Xcode
- Min deployment target: iOS 26.2
- Swift 5.0