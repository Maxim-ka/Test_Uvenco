package reschikov.test.test_uvenco.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.sp
import reschikov.test.test_uvenco.R

val styleHeaderH3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontSize = 17.sp,
    fontWeight = W600,
    lineHeight = 24.sp
)

val styleHeaderH4 = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontSize = 16.sp,
    fontWeight = W600,
    lineHeight = 22.sp
)

val styleBody = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontSize = 16.sp,
    fontWeight = W500,
    lineHeight = 22.sp
)

val styleControl = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontSize = 20.sp,
    fontWeight = W600,
    lineHeight = 20.sp
)

val styleDescription = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontSize = 16.sp,
    fontWeight = W500,
    lineHeight = 24.sp
)

val styleValue = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
    fontSize = 18.sp,
    fontWeight = W700,
    lineHeight = 27.sp
)

val styleFootnote = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontSize = 14.sp,
    fontWeight = W500,
    lineHeight = 22.sp
)