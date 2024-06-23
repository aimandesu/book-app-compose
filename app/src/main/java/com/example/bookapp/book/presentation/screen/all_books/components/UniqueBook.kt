package com.example.bookapp.book.presentation.screen.all_books.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookapp.book.domain.model.BookModel

@Composable
fun UniqueBook(
    book: BookModel,
    screenWidth: Dp,
    onClick: () -> Unit,
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(15.dp)
            )
    ) {
        Row(
            Modifier
                .height(200.dp)
                .width(screenWidth),
        ) {

            AsyncImage(
                model = book.image,
                contentDescription = null,
                Modifier
                    .fillMaxHeight()
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 5.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Column(
                        Modifier
                            .width(screenWidth * 0.49f)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = book.name,
                            style = TextStyle(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = book.author,
                            style = TextStyle(
                                fontSize = 19.sp,
                                textAlign = TextAlign.Justify,
                                fontStyle = FontStyle.Italic
                            ),
                        )
                    }
                    Text(
                        text = book.year.toString(),
                        style = TextStyle(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Button(
                    modifier =
                    Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .height(50.dp)
                        .fillMaxWidth(0.8f),
//                        .clip(CircleShape)
//                        .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                    onClick = onClick,
                ) {
                    Text(text = "View Book")
                }
            }
        }
    }

}