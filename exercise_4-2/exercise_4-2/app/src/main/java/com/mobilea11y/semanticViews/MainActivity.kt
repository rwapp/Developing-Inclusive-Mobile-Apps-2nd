package com.mobilea11y.semanticViews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilea11y.semanticViews.ui.theme.SemanticViewsTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SemanticViewsTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AppDetails()
                    }
                }
            }
        }
    }
}

@Composable
fun AppDetails() {
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle(
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Reviews(Modifier.weight(1f))
            ColumnDivider()
            Guidance(Modifier.weight(1f))
            ColumnDivider()
            Downloads(Modifier.weight(1f))
        }
    }
}

@Composable
fun Reviews(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "4.1 ⭐️",
            Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
        LabelWithButton("2m reviews") { }
    }
}

@Composable
fun Guidance(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = "Rating"
        )
        LabelWithButton("Parental guidance") { }
    }
}

@Composable
fun Downloads(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("500m+", Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
        Text("Downloads", Modifier.fillMaxWidth())
    }
}

@Composable
fun ColumnDivider() {
    VerticalDivider(
        Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        thickness = 1.dp,
        color = Color.LightGray
    )
}

@Composable
fun LabelWithButton(text: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text,
            Modifier
                .weight(1f),
            textAlign = TextAlign.Start
        )
        IconButton(
            onClick = onClick,
            Modifier.size(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Information"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SemanticViewsTheme {
        AppDetails()
    }
}