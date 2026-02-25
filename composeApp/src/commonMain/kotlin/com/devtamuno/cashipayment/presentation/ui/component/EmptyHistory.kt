package com.devtamuno.cashipayment.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.no_transactions_yet
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyHistory(modifier: Modifier = Modifier) {
  Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Icon(
        Icons.Default.History,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(Res.string.no_transactions_yet),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.outline,
    )
  }
}
