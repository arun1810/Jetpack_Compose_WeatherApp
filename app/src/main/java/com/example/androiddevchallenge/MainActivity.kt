/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.CoolColorPalette
import com.example.androiddevchallenge.ui.theme.CoolDarkColorPalette
import com.example.androiddevchallenge.ui.theme.HotColorPalette
import com.example.androiddevchallenge.ui.theme.HotDarkColorPalette
import com.example.androiddevchallenge.ui.theme.NewTheme

class MainActivity : ComponentActivity() {
    private  lateinit var days:List<String>
    lateinit   var  temp:List<String>
    lateinit   var  cloudy:List<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{ days  = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
            temp = listOf("22","19","30","26","24","27","21")
            cloudy=listOf(0,1,0,1,0,0,1)

            WeatherText(days,temp,cloudy,)

        }
    }
}
@Composable
fun ImageComponent(){
    Image(painter = painterResource(id = R.drawable.ic_snow),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .paddingFromBaseline(10.dp)
            .clip(shape = RoundedCornerShape(5.dp)),

        )
}

@Composable
fun WeatherText(days:List<String>,temp:List<String>,cloudy:List<Int>) {

    var index by remember { mutableStateOf(0) }
    var colors by remember { mutableStateOf(CoolColorPalette) }


    colors = if(isSystemInDarkTheme()){if(temp[index].toInt()>25){
        HotDarkColorPalette}else{
        CoolDarkColorPalette}
    }
    else{if(temp[index].toInt()>25){
        HotColorPalette}else{
        CoolColorPalette}}


    val surfaceclr by animateColorAsState(colors.surface)
    val primaryclr = colors.primary
     NewTheme(colors) {
        Surface(color = surfaceclr, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            Column(
                modifier = Modifier
                    .paddingFromBaseline(10.dp)
                    .fillMaxHeight(),
                //verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                ImageComponent()

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight(0.5f)

                ) {
                    Text(
                        text = days[index],
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = primaryclr
                    )
                    Text(
                        text = temp[index] + "\u2103",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color =primaryclr
                    )

                    WindTextnImage(primaryclr = primaryclr)
                }

                val state = rememberScrollState()

                Row(
                    Modifier
                        .horizontalScroll(state)
                        .fillMaxWidth()
                        .fillMaxHeight(),


                    verticalAlignment = Alignment.Bottom
                ) {
                    for (i in 1..6) {
                        Button(
                            onClick = { index = i }, modifier = Modifier
                                .padding(end = 5.dp, bottom = 5.dp)
                                .fillMaxHeight(0.55f)
                                //.paddingFromBaseline(10.dp)
                                .width(100.dp)
                                .clip(shape = RoundedCornerShape(15.dp))
                        ) {
                            BtnLayout(day = days[i], temp = temp[i] + "\u2103",cloudy[i])

                        }
                    }
                }
            }
        }
    }

}

@Composable
fun WindTextnImage(primaryclr: Color){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.ic_windy),
            contentDescription = null,
            modifier = Modifier

                .clip(shape = RoundedCornerShape(5.dp))
                .padding(1.dp),

            )

        Text(
            text = "8km/h",
            style = MaterialTheme.typography.subtitle1,
            color = primaryclr
        )
    }
}

@Composable
fun BtnLayout(day:String,temp:String,i:Int){
    Column(verticalArrangement = Arrangement.Top,horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {

        Text(text = day,style = MaterialTheme.typography.subtitle1,modifier = Modifier.align( Alignment.CenterHorizontally))
        Image(painter = if(i==0){
            painterResource(id = R.drawable.ic_sun_1)
        }else{
            painterResource(id = R.drawable.ic_cloudy_sun_1)
        },contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .fillMaxHeight(0.80f),
        )

        Text(text = temp,style = MaterialTheme.typography.h6,modifier = Modifier.align( Alignment.CenterHorizontally))

    }
}

@Preview
@Composable
fun Test(){
    MaterialTheme() {
        val days  = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
        val  temp = listOf("22","19","30","26","24","27","21")
        val cloudy=listOf(0,1,0,1,0,0,1)
        WeatherText(days = days, temp = temp, cloudy =cloudy )
    }
}
