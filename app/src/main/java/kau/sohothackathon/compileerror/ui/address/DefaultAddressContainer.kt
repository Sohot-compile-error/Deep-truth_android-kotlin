package kau.sohothackathon.compileerror.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.data.model.Address
import kau.sohothackathon.compileerror.ui.theme.SubGray

@Composable
fun DefaultAddressContainer(
    search: String,
    updateSearch: (String) -> Unit,
    addresses: List<Address>,
    filteredAddresses: List<Address>
) {
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
                onValueChange = updateSearch,
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
            text = if (search.isEmpty()) "모든 연락처" else "'${search}' 검색 결과",
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 30.dp, bottom = 5.dp)
        )

        /** 연락처 모음 */
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (search.isEmpty()) {
                items(addresses) { address ->
                    AddressItem(address, search)
                }
            } else {
                items(filteredAddresses) { address ->
                    AddressItem(address, search)
                }
            }
        }
    }
}
