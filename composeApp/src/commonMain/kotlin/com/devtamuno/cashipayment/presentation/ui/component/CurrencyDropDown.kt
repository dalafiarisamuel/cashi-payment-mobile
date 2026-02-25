package com.devtamuno.cashipayment.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.currency
import com.devtamuno.cashipayment.domain.model.Currency
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDown(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  var expanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded },
      modifier = modifier.fillMaxWidth(),
  ) {
    OutlinedTextField(
        value = selectedCurrency,
        onValueChange = {},
        readOnly = true,
        label = {
          Text(stringResource(Res.string.currency), style = MaterialTheme.typography.bodySmall)
        },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        modifier =
            Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
    )

    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      Currency.SUPPORTED_CURRENCIES.forEach { currency ->
        DropdownMenuItem(
            text = { Text(currency, style = MaterialTheme.typography.bodySmall) },
            onClick = {
              onCurrencySelected(currency)
              expanded = false
            },
        )
      }
    }
  }
}

@Preview
@Composable
fun CurrencyDropDownPreview() {
  CurrencyDropDown(selectedCurrency = "USD", onCurrencySelected = {})
}
