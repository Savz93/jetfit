package com.example.jetfit.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetfit.MainViewModel
import com.example.jetfit.data.favoritemeal.FavoriteMeal
import com.example.jetfit.data.favoritemeal.FavoriteMealViewModel
import com.example.jetfit.data.model.MealByCategory
import com.example.jetfit.data.userexercise.UserExercise
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext

@Composable
fun NutritionScreen(
    mainViewModel: MainViewModel,
    favoriteMealViewModel: FavoriteMealViewModel = viewModel(),
    navController: NavController,
) {
    val mealByCategoryState by mainViewModel.mealByCategoryState.collectAsState()
    var meals by remember { mutableStateOf(emptyList<MealByCategory>()) }
    var favoriteMeals by remember { mutableStateOf(emptyList<FavoriteMeal>()) }
    var search by remember { mutableStateOf("") }
    var allCardIsActive by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mealByCategoryState.isNotEmpty()) {
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    value = search,
                    onValueChange = { search = it },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    label = { Text(text = "search") },
                    placeholder = { Text(text = "search") }
                )
            }

            item {
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.Main) {
                        favoriteMealViewModel.getAllFavoriteMeal.observeForever { favoriteMeal ->
                            favoriteMeals = favoriteMeal
                        }

                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Card(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight()
                            .clickable {
                                allCardIsActive = true
                            },
                        colors =
                        if (allCardIsActive)
                            CardDefaults.cardColors(containerColor = Color.DarkGray, contentColor = Color.White)
                        else
                            CardDefaults.cardColors(containerColor = Color.LightGray, contentColor = Color.Black),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "All",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight()
                            .clickable { allCardIsActive = false },
                        colors =
                        if (allCardIsActive)
                            CardDefaults.cardColors(containerColor = Color.LightGray, contentColor = Color.Black)
                        else
                            CardDefaults.cardColors(containerColor = Color.DarkGray, contentColor = Color.White),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Favorites",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            meals = if (search.isNotEmpty()) {
                mainViewModel.searchForMeal(search, mealByCategoryState)
            } else {
                mealByCategoryState
            }

            if (allCardIsActive) {
                items(meals) { meal ->
                    NutritionCard(
                        imageUrl = meal.strMealThumb,
                        meal = meal.strMeal,
                        mealId = meal.idMeal,
                        navController = navController,
                        favoriteMealViewModel = favoriteMealViewModel
                    )
                }
            } else {
                items(favoriteMeals) {favoriteMeal ->
                    NutritionCard(
                        imageUrl = favoriteMeal.strMealThumb,
                        meal = favoriteMeal.strMeal,
                        mealId = favoriteMeal.idMeal,
                        navController = navController,
                        favorite = favoriteMeal.favorite,
                        favoriteMealViewModel = favoriteMealViewModel
                    )
                }
            }
        } else {
            item {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NutritionCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    meal: String,
    mealId: String = "",
    favorite: Boolean = false,
    navController: NavController,
    favoriteMealViewModel: FavoriteMealViewModel
) {
    val favoriteClicked = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        favoriteMealViewModel.getAllFavoriteMeal.observeForever {
            for (favoriteMeal in it) {
                if (favoriteMeal.idMeal == mealId) {
                    favoriteClicked.value = true
                }
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(16.dp)
            .clickable {
                navController.navigate("${Screen.NutritionDetailScreen.route}/${mealId}")
            },
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
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {

                GlideImage(
                    model = imageUrl,
                    contentDescription = "Meal Image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.LightGray),
                    contentScale = ContentScale.Fit
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    modifier = Modifier
                        .width(180.dp)
                        .height(140.dp),
                    text = meal,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =  MaterialTheme.colorScheme.primary
                )
            }

            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .clickable {
                        if (!favoriteClicked.value) {
                            favoriteMealViewModel.addFavoriteMeal(
                                FavoriteMeal(
                                    strMeal = meal,
                                    idMeal = mealId,
                                    strMealThumb = imageUrl,
                                    favorite = true
                                )
                            )
                        } else {
                            favoriteMealViewModel.deleteFavoriteMeal(
                                FavoriteMeal(
                                    strMeal = meal,
                                    idMeal = mealId,
                                    strMealThumb = imageUrl,
                                    favorite = true
                                )
                            )
                        }

                        favoriteClicked.value = !favoriteClicked.value
                    },
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = if (favorite || favoriteClicked.value) Color.Yellow else Color.Gray
            )
        }
    }
}
