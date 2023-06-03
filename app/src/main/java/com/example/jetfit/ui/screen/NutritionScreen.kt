package com.example.jetfit.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetfit.MainViewModel
import com.example.jetfit.R
import com.example.jetfit.data.model.MealByCat
import com.example.jetfit.data.model.MealByCategory
import com.example.jetfit.data.model.MealName

@Composable
fun NutritionScreen(mainViewModel: MainViewModel) {
    val categoryState by mainViewModel.mealCategoryState.collectAsState()
    val mealByCategoryState by mainViewModel.mealByCategoryState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            if (mealByCategoryState.isNullOrEmpty())
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
        }

        items(mealByCategoryState) { meal ->
            NutritionCard(meal = meal)
        }

//        items(items = categoryState.meals) { mealName: MealName ->
//            NutritionCard(category = mealName.strCategory)
//        }

    }
}

@Composable
fun NutritionCard(
    modifier: Modifier = Modifier,
    meal: String
) {
    val favoriteIconClicked = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.jetfit_app_icon),
                contentDescription = "Meal Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.LightGray),
                contentScale = ContentScale.Fit
            )

            Text(
                text = meal,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color =  MaterialTheme.colorScheme.primary
            )

            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .clickable { favoriteIconClicked.value = !favoriteIconClicked.value },
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = if (favoriteIconClicked.value) Color.Yellow else Color.Gray
            )
        }
    }
}

@Composable
fun NutritionCardContent() {

}