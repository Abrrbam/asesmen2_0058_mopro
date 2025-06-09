package com.abrahamputra0058.asesmen2.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var selectedDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var selectedTypeOptionText by rememberSaveable { mutableStateOf(agendaTypeOptions.first()) }
    var selectedTime by rememberSaveable { mutableStateOf("") }

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
                        if (judul.isBlank() || deskripsi.isBlank() || selectedDate == null || selectedTime == "") {
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
//                          Hanya soft-delete
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
            onDateChange = { selectedDate = it },
            selectedTime = selectedTime,
            onTimeChange = { selectedTime = it },

            )
    }
}

@Composable
fun FormDetailAgenda(
    modifier: Modifier = Modifier,
    title: String, onTitleChange: (String) -> Unit,
    description: String, onDescChange: (String) -> Unit,
    agendaTypeOptions: List<String> = listOf(),
    selectedTypeOptionText: String,
    onSelectedTypeChange: (String) -> Unit, //Supaya bisa berubah nilai dropdown
    selectedDate: Long? = null,
    onDateChange: (Long?) -> Unit,
    selectedTime: String,
    onTimeChange: (String) -> Unit
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

//      Tanggal agenda
        DatePickerField(
            selectedDate = selectedDate,
            onDateChange = { millis ->
                onDateChange(millis)
                dateError = false
            },
            isError = dateError
        )

//      Time picker
        SimpleTimeInput(
            initialTime = selectedTime,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), maxLines = 5
        )
    }
}

@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    selectedDate: Long?,
    onDateChange: (Long?) -> Unit,
    isError: Boolean
) {
    var showModal by remember { mutableStateOf(false) }
    var textValue by rememberSaveable(selectedDate) {
        mutableStateOf(selectedDate?.let {
            convertMillisToDate(
                it
            )
        } ?: "")
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            val parsed =
                parseDateToMillis(it) //Ini berguna ketika pengguna mengetik tanggal(Date) -> diubah ke Long
            onDateChange(parsed)
        },
        label = { Text(stringResource(R.string.date_agenda)) },
        isError = isError,
        placeholder = { Text("dd/MM/YYYY") },
        trailingIcon = {
            IconButton(onClick = { showModal = true }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_date)
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )

    if (showModal) {
        DatePickerModal(
            selectedDate = selectedDate ?: System.currentTimeMillis(),
            onDateSelected = {
                onDateChange(it)
                textValue = it?.let { convertMillisToDate(it) } ?: ""
                showModal = false
            },
            onDismiss = { showModal = false }
        )
    }
}


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
                onDateSelected(datePickerState.selectedDateMillis)
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

fun parseDateToMillis(dateStr: String): Long? {
    return try {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
        formatter.parse(dateStr)?.time
    } catch (e: Exception) {
        null
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
    return formatter.format(Date(millis))
}


//Time picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTimeInput(
    initialTime: String?,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val initialHourMinute = remember(initialTime) {
        initialTime?.split(":")?.mapNotNull { it.toIntOrNull() }?.takeIf { it.size == 2 }
            ?: listOf(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
            )
    }

//    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = initialHourMinute[0],
        initialMinute = initialHourMinute[1],
        is24Hour = true,
    )

    val formattedTime = "%02d:%02d".format(timePickerState.hour, timePickerState.minute)
//    var selectedTime by remember { mutableStateOf(initialTime) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeInput(state = timePickerState)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onDismiss,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    onConfirm(formattedTime)
                    onDismiss()
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("OK")
            }
        }
        Column {
            Text(
                text = stringResource(R.string.time_agenda, formattedTime ?: ""),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
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

