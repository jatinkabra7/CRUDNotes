package com.jatinkabra.crudnotes.screens

import android.health.connect.datatypes.AppInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jatinkabra.crudnotes.R

@Composable
fun AppInfo(modifier: Modifier = Modifier) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(it)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    text = "CRUD Notes",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Version 3",
                    color = Color.White.copy(0.7f),
                    fontSize = 20.sp
                )

//                Spacer(modifier = Modifier.padding(30.dp))
//
//                Image(
//                    painter = painterResource(id = R.drawable.crud),
//                    contentDescription = null,
//                    modifier = Modifier.size(200.dp)
//                )

                Spacer(modifier = Modifier.padding(30.dp))

                Text(
                    text = "Made by Jatin Kabra",
                    color = Color.White.copy(0.7f),
                    fontSize = 20.sp
                )
            }
        }
    }
}