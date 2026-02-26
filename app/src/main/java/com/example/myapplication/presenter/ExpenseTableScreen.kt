package com.example.myapplication.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.domain.Expense

private val BackgroundColor = Color(0xFFF5F5F5)
private val CardBackground = Color.White
private val HeaderTextColor = Color(0xFFB0B0B0)
private val HeaderIconColor = Color(0xFFCCCCCC)
private val ExpenseNameColor = Color(0xFF1A1A1A)
private val MethodTextColor = Color(0xFF6B6B6B)
private val AmountTextColor = Color(0xFF1A1A1A)
private val DividerColor = Color(0xFFEEEEEE)
private val EditIconColor = Color(0xFFCCCCCC)

// ─── Main Screen ──────────────────────────────────────────────────────────────

@Composable
fun ExpenseTableScreen(
    viewModel: ExpenseViewModel,
    onEditClick: (Expense) -> Unit = {}
) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column {
                // ── Header Row ────────────────────────────────────────────
                ExpenseTableHeader()

                HorizontalDivider(color = DividerColor, thickness = 1.dp)

                // ── Data Rows ─────────────────────────────────────────────
                LazyColumn {
                    items(expenses, key = { it.expenseId }) { expense ->
                        ExpenseRow(
                            expense = expense,
                            onEditClick = { onEditClick(expense) }
                        )
                        HorizontalDivider(
                            color = DividerColor,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 0.dp)
                        )
                    }
                }
            }
        }
    }
}

// ─── Header ───────────────────────────────────────────────────────────────────

@Composable
private fun ExpenseTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Expense column
        Row(
            modifier = Modifier.weight(1.4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = HeaderIconColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Expense",
                color = HeaderTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Method column
        Row(
            modifier = Modifier.weight(1.4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = HeaderIconColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Method",
                color = HeaderTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Amount column
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                tint = HeaderIconColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Amount",
                color = HeaderTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Spacer for edit icon column
        Spacer(modifier = Modifier.width(40.dp))
    }
}

// ─── Data Row ─────────────────────────────────────────────────────────────────

@Composable
private fun ExpenseRow(
    expense: Expense,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Expense name
        Text(
            text = expense.expenseName,
            modifier = Modifier.weight(1.4f),
            color = ExpenseNameColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        // Payment method
        Text(
            text = expense.method,
            modifier = Modifier.weight(1.4f),
            color = MethodTextColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )

        // Amount
        Text(
            text = "$${expense.amount}",
            modifier = Modifier.weight(1f),
            color = AmountTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        // Edit icon
        IconButton(
            onClick = onEditClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit ${expense.expenseName}",
                tint = EditIconColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
