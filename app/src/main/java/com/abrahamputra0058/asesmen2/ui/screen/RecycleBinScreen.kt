package com.abrahamputra0058.asesmen2.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abrahamputra0058.asesmen2.R
import com.abrahamputra0058.asesmen2.navigation.Screen
import com.abrahamputra0058.asesmen2.ui.theme.Asesmen2Theme
import com.abrahamputra0058.asesmen2.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.recycle_bin))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.surfaceBright
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = stringResource(R.string.menu),
                            tint = MaterialTheme.colorScheme.surfaceBright
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        RecycleBinList(Modifier.padding(innerPadding))
    }
}

@Composable
fun RecycleBinList(modifier: Modifier) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val trashViewModel: TrashViewModel = viewModel(factory = factory)
    val deletedAgendas by trashViewModel.recycleBinData.collectAsState()

    if (deletedAgendas.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.empty_recycle_bin))
        }
    } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(deletedAgendas) {
                    ListItem(agenda = it) {
                    }
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    trashViewModel.restoreData(it.id)
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.restore), color = MaterialTheme.colorScheme.primary)
                        }
                        TextButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    trashViewModel.deletePermanentData(it.id)
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.delete_permanent),color = MaterialTheme.colorScheme.error)
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RecycleBinScreenPreview() {
    Asesmen2Theme {
        RecycleBinScreen(rememberNavController())
    }
}


