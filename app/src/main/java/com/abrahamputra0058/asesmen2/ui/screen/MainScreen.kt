package com.abrahamputra0058.asesmen2.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abrahamputra0058.asesmen2.R
import com.abrahamputra0058.asesmen2.navigation.Screen
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import com.abrahamputra0058.asesmen2.ui.model.MainViewModel
import com.abrahamputra0058.asesmen2.ui.theme.Asesmen2Theme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(navController: NavHostController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(Screen.About.route)}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription =  stringResource(R.string.about_app),
                            tint = MaterialTheme.colorScheme.primary

                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Screen.FormAdd.route)}
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_agenda),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    ) { innerPadding -> ScreenContent(Modifier.padding(innerPadding)) }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){
    val viewModel: MainViewModel = viewModel()
    val data = viewModel.data

    if (data.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.empty_list))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(data) {
                ListItem(agenda = it)
                HorizontalDivider()
            }
        }

//        Text(
//            text = stringResource(R.string.intro_app),
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.padding(16.dp)
//        )
    }
}

@Composable
fun ListItem(agenda: Agenda) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        Image(
//            painter = painterResource(agenda.imageResId),
//            contentDescription = stringResource(R.string.image_name, agenda.tipe),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.size(24.dp)
//
//        )
        Text(
            text = agenda.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(text = agenda.tipe)
        Text(text = agenda.tanggal)
        Text(text = agenda.waktu)
        Text(
            text = agenda.deskripsi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis

        )

    }
}



//private fun shareAgenda(context: Context, message: String){
//    val shareIntent = Intent(Intent.ACTION_SEND).apply {
//        type = "text/plain"
//        putExtra(Intent.EXTRA_TEXT, message)
//    }
//    if (shareIntent.resolveActivity(context.packageManager) != null) {
//        context.startActivity(shareIntent)
//    }
//}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Asesmen2Theme {
        MainScreen(rememberNavController())
    }
}