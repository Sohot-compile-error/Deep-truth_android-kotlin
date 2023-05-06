package kau.sohothackathon.compileerror.ui.address

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Gray
import kau.sohothackathon.compileerror.ui.theme.Main

@Composable
fun AddressItem(
    address: Address,
    searchString: String = "",
    navigateToVoiceCall: (String) -> Unit,
) {

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
                    buildAnnotatedString {
                        address.name.forEach { searchChar ->
                            if (searchString.contains(searchChar)) {
                                withStyle(
                                    style = SpanStyle(
                                        color = Main,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(searchChar)
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Black)) {
                                    append(searchChar)
                                }
                            }
                        }
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (isClicked) TextAlign.End else TextAlign.Start,

                    )
                Text(
                    buildAnnotatedString {
                        address.phone.forEach { searchChar ->
                            if (searchString.contains(searchChar)) {
                                withStyle(
                                    style = SpanStyle(
                                        color = Main,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(searchChar)
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Black)) {
                                    append(searchChar)
                                }
                            }
                        }
                    },
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
                        .align(Alignment.Center)
                        .clickable {
                            navigateToVoiceCall(address.phone)
                        },
                )
            }

        }
    }

}
