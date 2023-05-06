package kau.sohothackathon.compileerror.ui.address

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Gray
import kau.sohothackathon.compileerror.ui.theme.SubGray

@Composable
fun AddressScreen(appState: ApplicationState) {

    var search by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        /** 유저 텍스트 필드 */
        Row(
            modifier = Modifier.padding(30.dp),
        ) {
            BasicTextField(modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(80.dp))
                .background(SubGray),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = Color.Black,
                ),
                value = search,
                onValueChange = { search = it },
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(20.dp, 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "IC_SEARCH",
                            modifier = Modifier.size(24.dp),
                        )
                        Box(Modifier.weight(1f)) {
                            if (search.isEmpty()) {
                                Text(
                                    "연락처를 입력해주세요.",
                                    style = LocalTextStyle.current.copy(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        fontSize = 16.sp,
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    }
                })


        }

        Text(
            text = "모든 연락처", color = Color.Black, fontSize = 13.sp,
            modifier = Modifier.padding(start = 30.dp, bottom = 5.dp)
        )

        /** 연락처 모음 */
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(10) {
                AddressItem()
            }
        }
    }
}

@Composable
fun AddressItem() {

    var isClicked by remember {
        mutableStateOf(false)
    }
    val animatedXOffset = animateDpAsState(
        targetValue = if (isClicked) (-170).dp else 0.dp,
    )
    val animatedXCallOffset = animateDpAsState(
        targetValue = if (isClicked) 0.dp else (170).dp,
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = animatedXOffset.value)
                .padding(30.dp, 0.dp)
                .clip(RoundedCornerShape(80.dp))
                .border(1.dp, Black, RoundedCornerShape(80.dp))
                .clickable {
                    isClicked = !isClicked
                }
                .padding(15.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_50),
                contentDescription = "IC_LAUNCHER",
                modifier = Modifier.size(50.dp),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
            ) {
                Text(
                    text = "김수현",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (isClicked) TextAlign.End else TextAlign.Start,
                )
                Text(
                    text = "010-1234-5678",
                    color = Black,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (isClicked) TextAlign.End else TextAlign.Start,
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 30.dp)
                .offset(x = animatedXCallOffset.value),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Gray)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "IC_PHONE",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Gray)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "IC_PHONE",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                )
            }

        }
    }

}
