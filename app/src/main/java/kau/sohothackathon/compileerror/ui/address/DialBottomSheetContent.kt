package kau.sohothackathon.compileerror.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Gray


@Composable
fun DialBottomSheetContent(
    phoneSearch: String,
    updatePhoneSearch: (Char) -> Unit,
    deletePhoneSearch: () -> Unit,
) {

    val regex = "(\\d{2,3})(\\d{3,4})(\\d{4})".toRegex()  // 정규식을 이용하여 숫자를 그룹화합니다.

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray)
            .padding(top = 10.dp)
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
                .size(80.dp, 5.dp)
                .clip(RoundedCornerShape(50))
                .background(Black)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = regex.replace(phoneSearch, "$1-$2-$3"),
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Black
            )
            IconButton(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp), onClick = deletePhoneSearch
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_keyboard_back_24),
                    contentDescription = "IC_KEYBOARD_BACK",
                    modifier = Modifier
                        .size(42.dp),
                    tint = Black,
                )
            }

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('1')
                    },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "1",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('2')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "2",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('3')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "3",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('4')
                    },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "4",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('5')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "5",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('6')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "6",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('7')
                    },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "7",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('8')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "8",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('9')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "9",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('*')
                    },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "*",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('0')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "0",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape), onClick = {
                        updatePhoneSearch('#')
                    }, colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "#",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape), onClick = {
                    updatePhoneSearch('9')
                }, colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                IconButton(onClick = {
                    // TODO 전화걸기
                }) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "IC_PHONE",
                        modifier = Modifier
                            .size(36.dp),
                        tint = Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
