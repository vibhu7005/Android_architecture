package com.example.myapplication.presenter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.domain.Expense
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

// ─── Colors ───────────────────────────────────────────────────────────────────
private val BackgroundColor = Color(0xFFF5F5F5)
private val CardBackground = Color.White
private val HeaderTextColor = Color(0xFFB0B0B0)
private val HeaderIconColor = Color(0xFFCCCCCC)
private val ExpenseNameColor = Color(0xFF1A1A1A)
private val MethodTextColor = Color(0xFF6B6B6B)
private val AmountTextColor = Color(0xFF1A1A1A)
private val DividerColor = Color(0xFFEEEEEE)
private val EditIconColor = Color(0xFFCCCCCC)
private val DoneButtonColor = Color(0xFF1A1A1A)
private val LabelColor = Color(0xFF6B6B6B)
private val FieldBorderColor = Color(0xFFD5D5D5)
private val DollarPrefixColor = Color(0xFFB0B0B0)
private val ErrorColor = Color(0xFFD32F2F)

// ─── Main Screen ──────────────────────────────────────────────────────────────

@Composable
fun ExpenseTableScreen(
    viewModel: ExpenseViewModel = viewModel()
) {
    val context = LocalContext.current
    val expenses by viewModel.expenses.collectAsState()

    var editingExpenseId by remember { mutableStateOf<Int?>(null) }

    var editName by remember { mutableStateOf("") }
    var editMethod by remember { mutableStateOf("") }
    var editAmount by remember { mutableStateOf("") }
    var isAmountValid by remember { mutableStateOf(true) }
    
    // Track original values to detect changes
    var originalName by remember { mutableStateOf("") }
    var originalMethod by remember { mutableStateOf("") }
    var originalAmount by remember { mutableStateOf("") }

    val isEditing = editingExpenseId != null

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isEditing) 0.15f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "bgAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            LazyColumn {
                // ── Header ────────────────────────────────────────────
                item(key = "header") {
                    ExpenseTableHeader(
                        modifier = Modifier.alpha(if (isEditing) backgroundAlpha else 1f)
                    )
                    Divider(color = DividerColor, thickness = 1.dp)
                }

                // ── Expense Rows ──────────────────────────────────────
                items(expenses, key = { it.expenseId }) { expense ->
                    val isThisEditing = editingExpenseId == expense.expenseId

                    // Show normal row when NOT the one being edited
                    if (!isThisEditing) {
                        ExpenseRow(
                            expense = expense,
                            modifier = Modifier.alpha(
                                if (isEditing) backgroundAlpha else 1f
                            ),
                            editEnabled = !isEditing,
                            onEditClick = {
                                editName = expense.expenseName
                                editMethod = expense.method
                                editAmount = expense.amount.toLong().toString()
                                originalName = expense.expenseName
                                originalMethod = expense.method
                                originalAmount = expense.amount.toLong().toString()
                                isAmountValid = true
                                editingExpenseId = expense.expenseId
                            }
                        )
                    }

                    // Inline edit form — animated expand/collapse
                    AnimatedVisibility(
                        visible = isThisEditing,
                        enter = expandVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            expandFrom = Alignment.Top
                        ) + fadeIn(animationSpec = tween(250)),
                        exit = shrinkVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            ),
                            shrinkTowards = Alignment.Top
                        ) + fadeOut(animationSpec = tween(200))
                    ) {
                        InlineEditForm(
                            name = editName,
                            method = editMethod,
                            amount = editAmount,
                            isAmountValid = isAmountValid,
                            originalName = originalName,
                            originalMethod = originalMethod,
                            originalAmount = originalAmount,
                            onNameChange = { newValue ->
                                if (newValue.length <= 25) {
                                    editName = newValue
                                } else {
                                    Toast.makeText(context, "Maximum 25 characters allowed", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onMethodChange = { newValue ->
                                if (newValue.length <= 25) {
                                    editMethod = newValue
                                } else {
                                    Toast.makeText(context, "Maximum 25 characters allowed", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onAmountChange = { newValue ->
                                if (newValue.length <= 9) {
                                    editAmount = newValue
                                    isAmountValid = newValue.isEmpty() || (newValue.all { it.isDigit() } && newValue.toLongOrNull() != null)
                                } else {
                                    Toast.makeText(context, "Expense value cannot exceed 9 digits", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onCancel = {
                                editingExpenseId = null
                                isAmountValid = true
                                editName = ""
                                editMethod = ""
                                editAmount = ""
                                originalName = ""
                                originalMethod = ""
                                originalAmount = ""
                            },
                            onDone = {
                                val updated = expense.copy(
                                    expenseName = editName.trim(),
                                    method = editMethod.trim(),
                                    amount = editAmount.toLongOrNull()?.toDouble() ?: expense.amount
                                )
                                viewModel.updateExpense(updated)
                                editingExpenseId = null
                            }
                        )
                    }

                    Divider(
                        color = DividerColor,
                        thickness = 1.dp,
                        modifier = Modifier.alpha(
                            if (isEditing && !isThisEditing) backgroundAlpha else 1f
                        )
                    )
                }
            }
        }
    }
}

// ─── Header ───────────────────────────────────────────────────────────────────

@Composable
private fun ExpenseTableHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_info_details),
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

        Row(
            modifier = Modifier.weight(1.4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_agenda),
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

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
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

        Spacer(modifier = Modifier.width(40.dp))
    }
}

// ─── Normal Data Row ──────────────────────────────────────────────────────────

@Composable
private fun ExpenseRow(
    expense: Expense,
    modifier: Modifier = Modifier,
    editEnabled: Boolean = true,
    onEditClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = expense.expenseName,
            modifier = Modifier.weight(1.4f),
            color = ExpenseNameColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = expense.method,
            modifier = Modifier.weight(1.4f),
            color = MethodTextColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = "$${expense.amount.toLong()}",
            modifier = Modifier.weight(1f),
            color = AmountTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        IconButton(
            onClick = onEditClick,
            enabled = editEnabled,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_edit),
                contentDescription = "Edit ${expense.expenseName}",
                tint = EditIconColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ─── Inline Edit Form ─────────────────────────────────────────────────────────

@Composable
private fun InlineEditForm(
    name: String,
    method: String,
    amount: String,
    isAmountValid: Boolean,
    originalName: String,
    originalMethod: String,
    originalAmount: String,
    onNameChange: (String) -> Unit,
    onMethodChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCancel: () -> Unit,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    
    // Check if any changes have been made
    val hasChanges = name.trim() != originalName || method.trim() != originalMethod || amount != originalAmount
    val isFormValid = isAmountValid && amount.isNotBlank() && name.trim().isNotBlank() && method.trim().isNotBlank()
    val canSave = hasChanges && isFormValid
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = FieldBorderColor,
        unfocusedBorderColor = FieldBorderColor,
        cursorColor = DoneButtonColor,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )
    
    val errorFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ErrorColor,
        unfocusedBorderColor = ErrorColor,
        cursorColor = DoneButtonColor,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )
    val fieldShape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // ── Expense Field ─────────────────────────────────────────
        EditFieldRow(
            iconRes = android.R.drawable.ic_menu_info_details,
            label = "Expense"
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { newValue ->
                    if (newValue.length <= 25) {
                        onNameChange(newValue)
                    } else {
                        Toast.makeText(context, "Maximum 25 characters allowed", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                shape = fieldShape,
                colors = fieldColors,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = ExpenseNameColor
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Method Field ──────────────────────────────────────────
        EditFieldRow(
            iconRes = android.R.drawable.ic_menu_agenda,
            label = "Method"
        ) {
            OutlinedTextField(
                value = method,
                onValueChange = { newValue ->
                    if (newValue.length <= 25) {
                        onMethodChange(newValue)
                    } else {
                        Toast.makeText(context, "Maximum 25 characters allowed", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                shape = fieldShape,
                colors = fieldColors,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = ExpenseNameColor
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Amount Field ──────────────────────────────────────────
        EditFieldRow(
            iconRes = android.R.drawable.ic_menu_sort_by_size,
            label = "Amount"
        ) {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        // Only allow digits, no decimal point
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            if (newValue.length <= 9) {
                                onAmountChange(newValue)
                            } else {
                                Toast.makeText(context, "Expense value cannot exceed 9 digits", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    singleLine = true,
                    shape = fieldShape,
                    colors = if (isAmountValid) fieldColors else errorFieldColors,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    prefix = {
                        Text(
                            text = "$ ",
                            color = DollarPrefixColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = ExpenseNameColor
                    ),
                    isError = !isAmountValid
                )
                
                if (!isAmountValid) {
                    Text(
                        text = "Please enter a valid amount",
                        color = ErrorColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Action Buttons ────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cancel — outlined
            OutlinedButton(
                onClick = onCancel,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ExpenseNameColor
                )
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = ExpenseNameColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Cancel",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Done — filled dark
            Button(
                onClick = onDone,
                enabled = canSave,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DoneButtonColor,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_save),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Done",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ─── Reusable label + field row ───────────────────────────────────────────────

@Composable
private fun EditFieldRow(
    iconRes: Int,
    label: String,
    field: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.width(110.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = HeaderIconColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = LabelColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            field()
        }
    }
}