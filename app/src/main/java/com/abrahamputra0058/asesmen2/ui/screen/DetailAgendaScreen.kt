package com.abrahamputra0058.asesmen2.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abrahamputra0058.asesmen2.R
import com.abrahamputra0058.asesmen2.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val KEY_ID_AGENDA = "idAgenda"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAgendaScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    //  Pembuatan Dropdown
    val agendaTypeOptions = listOf(
        stringResource(R.string.course),
        stringResource(R.string.task),
        stringResource(R.string.other)
    )

    var judul by rememberSaveable { mutableStateOf("") }
    var deskripsi by rememberSaveable { mutableStateOf("") }
    var selectedDate by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var selectedTypeOptionText by rememberSaveable { mutableStateOf(agendaTypeOptions.first()) }
    var selectedTime by rememberSaveable { mutableStateOf("00:00") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getAgenda(id) ?: return@LaunchedEffect
        judul = data.judul
        deskripsi = data.deskripsi
        selectedDate = data.tanggal
        selectedTypeOptionText = data.tipe
        selectedTime = data.waktu
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
//                    Tombol Back
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id != null)
                        Text(text = stringResource(id = R.string.edit_note))
                    else
                        Text(text = stringResource(id = R.string.add_agenda))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),

//                Tombol simpan
                actions = {
                    IconButton(onClick = {
//                        Sanity Check
                        if (judul.isBlank() || deskripsi.isBlank()) {
                            Toast.makeText(context, R.string.input_invalid, Toast.LENGTH_LONG)
                                .show()
                            return@IconButton
                        }

//Kondisi cek id null atau tidak. Jika id null maka insert, jika ada maka update
                        if (id == null) {
                            viewModel.insert(
                                judul,
                                selectedTypeOptionText,
                                selectedDate,
                                selectedTime,
                                deskripsi
                            )
                        } else {
                            viewModel.update(
                                id,
                                judul,
                                selectedTypeOptionText,
                                selectedDate,
                                selectedTime,
                                deskripsi
                            )
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.save),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
//                    DeleteAction in actions
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
//                                Hanya soft-delete
                                viewModel.delete(id)
                                navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        FormDetailAgenda(
            Modifier.padding(innerPadding),
            title = judul,
            onTitleChange = { judul = it },
            description = deskripsi,
            onDescChange = { deskripsi = it },
            agendaTypeOptions = agendaTypeOptions, //Menyimpan opsi yang sumbernya di DetailAgendaScreen
            selectedTypeOptionText = selectedTypeOptionText,
            onSelectedTypeChange = { selectedTypeOptionText = it },
            selectedDate = selectedDate,
//            selectedTime = selectedTime,
            onTimeChange = { selectedTime = it },

        )
    }
}

@Composable
fun FormDetailAgenda(
    modifier: Modifier = Modifier,
    title: String, onTitleChange: (String) -> Unit = {},
    description: String, onDescChange: (String) -> Unit = {},
    agendaTypeOptions: List<String> = listOf(),
    selectedTypeOptionText: String,
    onSelectedTypeChange: (String) -> Unit = {}, //Supaya bisa berubah nilai dropdown
    selectedDate: Long? = null,
//    selectedTime: String = "00:00",
    onTimeChange: (String) -> Unit = {}
) {
    var judulError by rememberSaveable { mutableStateOf(false) }
    var deskripsiError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.intro_add),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

//        Dropdown selectedTypeOptionText agenda
        DropdownTypeAgenda(
            options = agendaTypeOptions,
            selectedType = selectedTypeOptionText,
            onOptionSelected = { onSelectedTypeChange(it) }
        )

//        Judul agenda
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title_agenda)) },
            isError = judulError,
            trailingIcon = { IconPicker(judulError) },
            supportingText = { ErrorHint(judulError) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

//        Pilih selectedDate
        DatePickerFieldToModal(
            selectedDate = selectedDate,
            onDateChange = {
                dateError = false
            },
            isError = dateError
        )

//      Time picker
        SimpleTimeInput(
            onConfirm = onTimeChange,
            onDismiss = {},
        )

//      Deskripsi
        OutlinedTextField(
            value = description,
            onValueChange = { onDescChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            placeholder = { Text(text = stringResource(R.string.ph_description)) },
            isError = deskripsiError,
            trailingIcon = { IconPicker(deskripsiError) },
            supportingText = { ErrorHint(deskripsiError) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(), maxLines = 5
        )
        }
    }
//Date picker
@Composable
fun DatePickerFieldToModal(
    modifier: Modifier = Modifier,
    selectedDate: Long?,
    onDateChange: (Long?) -> Unit = {},
    isError: Boolean,

) {

    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text(text = stringResource(R.string.date_agenda)) },
        isError = isError,
        readOnly = true,
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.select_date))
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showModal = true }
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                onDateChange(it)
                showModal = false
            },
            selectedDate = selectedDate ?: System.currentTimeMillis(),
            onDismiss = { showModal = false }
        )
    }
}


//Modal pilih tanggal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    selectedDate: Long,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis ?: System.currentTimeMillis())
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long?): String {
    if (millis == null) return ""
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


//Time picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTimeInput(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column(modifier = Modifier.padding(16.dp)) {
        TimeInput(
            state = timePickerState,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onDismiss, modifier = Modifier.padding(end = 8.dp)) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    val formattedTime = "%02d:%02d".format(timePickerState.hour, timePickerState.minute)
                    onConfirm(formattedTime)
                    onDismiss()
                          },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("OK")
            }
        }
    }
}

@Composable
fun IconPicker(isError: Boolean) {
    if (isError)
        Icon(
            imageVector = Icons.Filled.Warning, contentDescription = null
        )
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError)
        Text(text = stringResource(R.string.input_invalid))
}


//Hapus lewat "TextButton"
@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.other_menu),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.delete)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

