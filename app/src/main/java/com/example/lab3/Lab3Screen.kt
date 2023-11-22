package com.example.lab3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trp.ui.theme.TRPTheme
import com.example.trp.ui.theme.TRPThemeDefaultSettings

class Lab3Screen : ComponentActivity() {
    @Suppress("UNCHECKED_CAST")
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkMode = isSystemInDarkTheme()
            val trpThemeSettings by remember {
                mutableStateOf(
                    TRPThemeDefaultSettings.copy(
                        isDarkMode = isDarkMode
                    )
                )
            }
            TRPTheme(TRPThemeSettings = trpThemeSettings) {
                val viewModel = viewModel<Lab3ViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return Lab3ViewModel() as T
                        }
                    }
                )
                Scaffold(
                    containerColor = TRPTheme.colors.primaryBackground,
                ) {
                    Column {
                        ListArea(viewModel)
                        VectorArea(viewModel)
                        DataTypeRadioGroup(viewModel)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RandomAddButton(viewModel)
                                SortButton(viewModel)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IndexAddButton(viewModel)
                                PushBackButton(viewModel)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                MultiplyButton(viewModel)
                                LoadFromFileButton(viewModel)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SaveToFileButton(viewModel)
                                RemoveByIndexButton(viewModel)
                            }
                        }
                    }
                }
                if (viewModel.showPushBackDialog) {
                    PushBackDialog(viewModel)
                }
                if (viewModel.showIndexAddDialog) {
                    IndexAddDialog(viewModel)
                }
                if (viewModel.showMultiplyAllDialog) {
                    MultiplyAllDialog(viewModel)
                }
                if (viewModel.showRemoveByIndexDialog) {
                    RemoveDialog(viewModel)
                }
                if (viewModel.showIntegerSaveFileToast) {
                    Toast.makeText(
                        LocalContext.current,
                        "List save to file IntegerList.json",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showIntegerSaveFileToast = false
                }

                if (viewModel.showPoint2DSaveFileToast) {
                    Toast.makeText(
                        LocalContext.current,
                        "List save to file Point2DList.json",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showPoint2DSaveFileToast = false
                }
            }
        }
    }
}

@Composable
fun ListArea(viewModel: Lab3ViewModel) {
    Surface(
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = 5.dp,
                end = 5.dp
            )
            .fillMaxWidth()
            .wrapContentSize(),
        color = Color.Transparent,
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .verticalScroll(rememberScrollState())
                .background(
                    color = TRPTheme.colors.secondaryBackground,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = viewModel.listAreaText,
                color = TRPTheme.colors.primaryText,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun VectorArea(viewModel: Lab3ViewModel) {
    Surface(
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = 5.dp,
                end = 5.dp
            )
            .fillMaxWidth()
            .wrapContentSize(),
        color = Color.Transparent,
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .verticalScroll(rememberScrollState())
                .background(
                    color = TRPTheme.colors.secondaryBackground,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = viewModel.vectorAreaText,
                color = TRPTheme.colors.primaryText,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun DataTypeRadioGroup(viewModel: Lab3ViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = viewModel.selectedDataType == Lab3ViewModel.DataType.INTEGER,
                onClick = {
                    viewModel.selectedDataType = Lab3ViewModel.DataType.INTEGER
                    viewModel.integerListClean()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = TRPTheme.colors.myYellow
                )
            )
            Text(
                text = "Integer",
                color = TRPTheme.colors.primaryText,
                fontSize = 20.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = viewModel.selectedDataType == Lab3ViewModel.DataType.POINT2D,
                onClick = {
                    viewModel.selectedDataType = Lab3ViewModel.DataType.POINT2D
                    viewModel.point2DListClean()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = TRPTheme.colors.myYellow
                )
            )
            Text(
                text = "Point2D",
                color = TRPTheme.colors.primaryText,
                fontSize = 20.sp
            )
        }
        NTextField(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NTextField(viewModel: Lab3ViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "N = ",
            color = TRPTheme.colors.primaryText,
            fontSize = 20.sp
        )
        TextField(
            modifier = Modifier
                .height(60.dp)
                .width(90.dp)
                .padding(end = 15.dp),
            value = viewModel.nValue,
            onValueChange = { viewModel.onNValueChange(it) },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = TRPTheme.colors.secondaryBackground,
                textColor = TRPTheme.colors.primaryText,
                cursorColor = TRPTheme.colors.primaryText,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Composable
fun RandomAddButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onRandomAddButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Add 10 random", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun PushBackButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onPushBackButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Push back", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun IndexAddButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onIndexAddButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Add by index", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun MultiplyButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onMultiplyAllButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Multiply all", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun RemoveByIndexButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onRemoveByIndexButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Remove by Index", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun SortButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onSortButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Sort", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun SaveToFileButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onSaveToFileButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Save to file", color = TRPTheme.colors.secondaryText)
    }
}

@Composable
fun LoadFromFileButton(viewModel: Lab3ViewModel) {
    Button(
        onClick = { viewModel.onLoadFromFileButtonClick() },
        colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
    ) {
        Text(text = "Load from file", color = TRPTheme.colors.secondaryText)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PushBackDialog(viewModel: Lab3ViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Enter value", color = TRPTheme.colors.primaryText) },
        containerColor = TRPTheme.colors.primaryBackground,
        text = {
            when (viewModel.selectedDataType) {
                Lab3ViewModel.DataType.INTEGER -> {
                    TextField(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        value = viewModel.dialogValue1,
                        onValueChange = { viewModel.dialogValue1 = it },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = TRPTheme.colors.secondaryBackground,
                            textColor = TRPTheme.colors.primaryText,
                            cursorColor = TRPTheme.colors.primaryText,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text(
                                "Value",
                                color = TRPTheme.colors.primaryText,
                                modifier = Modifier
                                    .alpha(0.6f)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }

                Lab3ViewModel.DataType.POINT2D -> {
                    Column {
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue1,
                            onValueChange = { viewModel.dialogValue1 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "X",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue2,
                            onValueChange = { viewModel.dialogValue2 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "Y",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.onConfirmPushBackDialogButtonClick() },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
            ) {
                Text(text = "Confirm", color = TRPTheme.colors.secondaryText)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showPushBackDialog = false
                    viewModel.dialogValue1 = ""
                    viewModel.dialogValue2 = ""
                },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.errorColor)
            ) {
                Text(text = "Dismiss", color = TRPTheme.colors.secondaryText)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexAddDialog(viewModel: Lab3ViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Enter index and value", color = TRPTheme.colors.primaryText) },
        containerColor = TRPTheme.colors.primaryBackground,
        text = {
            when (viewModel.selectedDataType) {
                Lab3ViewModel.DataType.INTEGER -> {
                    Column {
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue1,
                            onValueChange = { viewModel.dialogValue1 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "Index",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue2,
                            onValueChange = { viewModel.dialogValue2 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "Value",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }

                Lab3ViewModel.DataType.POINT2D -> {
                    Column {
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue1,
                            onValueChange = { viewModel.dialogValue1 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "Index",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue2,
                            onValueChange = { viewModel.dialogValue2 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "X",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        TextField(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = viewModel.dialogValue3,
                            onValueChange = { viewModel.dialogValue3 = it },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = TRPTheme.colors.secondaryBackground,
                                textColor = TRPTheme.colors.primaryText,
                                cursorColor = TRPTheme.colors.primaryText,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(
                                    "Y",
                                    color = TRPTheme.colors.primaryText,
                                    modifier = Modifier
                                        .alpha(0.6f)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.onConfirmIndexAddDialogButtonClick() },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
            ) {
                Text(text = "Confirm", color = TRPTheme.colors.secondaryText)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showIndexAddDialog = false
                    viewModel.dialogValue1 = ""
                    viewModel.dialogValue2 = ""
                    viewModel.dialogValue3 = ""
                },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.errorColor)
            ) {
                Text(text = "Dismiss", color = TRPTheme.colors.secondaryText)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplyAllDialog(viewModel: Lab3ViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Enter value", color = TRPTheme.colors.primaryText) },
        containerColor = TRPTheme.colors.primaryBackground,
        text = {
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                value = viewModel.dialogValue1,
                onValueChange = { viewModel.dialogValue1 = it },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = TRPTheme.colors.secondaryBackground,
                    textColor = TRPTheme.colors.primaryText,
                    cursorColor = TRPTheme.colors.primaryText,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                textStyle = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text(
                        "Value",
                        color = TRPTheme.colors.primaryText,
                        modifier = Modifier
                            .alpha(0.6f)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = { viewModel.onConfirmMultiplyAllDialogButtonClick() },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
            ) {
                Text(text = "Confirm", color = TRPTheme.colors.secondaryText)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showMultiplyAllDialog = false
                    viewModel.dialogValue1 = ""
                },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.errorColor)
            ) {
                Text(text = "Dismiss", color = TRPTheme.colors.secondaryText)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveDialog(viewModel: Lab3ViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Enter index", color = TRPTheme.colors.primaryText) },
        containerColor = TRPTheme.colors.primaryBackground,
        text = {
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                value = viewModel.dialogValue1,
                onValueChange = { viewModel.dialogValue1 = it },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = TRPTheme.colors.secondaryBackground,
                    textColor = TRPTheme.colors.primaryText,
                    cursorColor = TRPTheme.colors.primaryText,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                textStyle = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text(
                        "Index",
                        color = TRPTheme.colors.primaryText,
                        modifier = Modifier
                            .alpha(0.6f)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = { viewModel.onConfirmRemoveDialogButtonClick() },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.myYellow)
            ) {
                Text(text = "Confirm", color = TRPTheme.colors.secondaryText)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showRemoveByIndexDialog = false
                    viewModel.dialogValue1 = ""
                },
                colors = ButtonDefaults.buttonColors(TRPTheme.colors.errorColor)
            ) {
                Text(text = "Dismiss", color = TRPTheme.colors.secondaryText)
            }
        }
    )
}