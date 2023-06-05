package kau.sohothackathon.compileerror.util

import android.support.annotation.DrawableRes
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE
import kau.sohothackathon.compileerror.util.Constants.FILE_ROUTE

enum class Screen(
    val route: String,
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    ADDRESS(
        ADDRESS_ROUTE,
        "주소록",
        R.drawable.ic_baseline_person_24,
        R.drawable.ic_outline_person_24
    ),
    FILE(
        FILE_ROUTE,
        "파일",
        R.drawable.ic_baseline_insert_drive_file_24,
        R.drawable.ic_outline_insert_drive_file_24
    );

}