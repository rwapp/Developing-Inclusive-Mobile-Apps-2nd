# Exercise 4-2: Semantic Views (Android)

A Jetpack Compose exercise demonstrating how to improve the accessibility tree of a sample UI using semantic APIs.

## Projects

| Folder | Description |
|--------|-------------|
| `exercise_4-2/` | Starter project — the UI as originally written |
| `exercise_4-2_fixed/` | Suggested solution applying accessibility improvements |

## The UI

Both projects display a row of app store–style statistics: a star rating, a parental guidance rating, and a download count — each separated by a vertical divider.

## The problem

In the starter project, the accessibility tree reflects the raw view hierarchy. Each statistic is split across multiple nodes (a text/icon node and a separate button), which means screen reader users hear fragmented, context-free descriptions like *"Information"* for the info buttons, and must navigate several elements to understand a single piece of information.

## The fix

The `exercise_4-2_fixed` project applies `clearAndSetSemantics` to merge each statistic into a single, well-described accessibility node:

- **Reviews** — merged into one button with the label *"4.1 stars from 2 million reviews"*
- **Parental guidance** — merged into one button with the label *"Rating: Parental guidance"*
- **Downloads** — merged into a single element with the label *"500 million plus downloads"*

## Your turn

After reading Chapter 4, open the starter project and apply what you have learned to bring its accessibility tree in line with the fixed version.

## Relevant chapter

Chapter 4 — Jetpack Compose Accessibility Model

## Requirements

- Android Studio
- Min SDK: 28 (Android 9 / Pie)
- Target SDK: 36 (Android 16)
- Java 11
