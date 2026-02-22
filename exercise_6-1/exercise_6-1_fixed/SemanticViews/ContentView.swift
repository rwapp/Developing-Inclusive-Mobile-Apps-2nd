//
//  ContentView.swift
//  SemanticViews
//
//  Created by Rob Whitaker on 22/02/2026.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        HStack {
            AppDetail(
                title: "1.6M ratings",
                value: "4.9",
                detail: "⭐️⭐️⭐️⭐️⭐️"
            )
            .accessibilityLabel("4.9 stars from 1.6 million ratings")

            Divider()
                .fixedSize()

            AppDetail(
                title: "Ages",
                value: "4+",
                detail: "Years"
            )


            Divider()
                .fixedSize()

            AppDetail(
                title: "Chart",
                value: "No. 1",
                detail: "Travel"
            )
            .accessibilityLabel("Travel chart number 1")
        }
        .padding()
    }
}

struct AppDetail: View {
    let title: String
    let value: String
    let detail: String

    var body: some View {
        VStack {
            Text(title.uppercased())
            Text(value)
                .font(.headline)
            Text(detail)
                .font(.caption)
        }
        .accessibilityElement(children: .combine)
        .frame(maxWidth: .infinity)
    }
}

#Preview {
    ContentView()
}
