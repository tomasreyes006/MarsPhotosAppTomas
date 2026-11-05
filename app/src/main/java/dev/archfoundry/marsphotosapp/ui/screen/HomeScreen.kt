package dev.archfoundry.marsphotosapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.archfoundry.marsphotosapp.R
import dev.archfoundry.marsphotosapp.network.MarsPhoto

@Composable
fun HomeScreen(marsUiState: MarsUiState, retryEntry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (marsUiState) {
            is MarsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is MarsUiState.Success -> PhotosGridScreen(
                marsUiState.photosList,
                modifier = Modifier.fillMaxSize(),
            )

            is MarsUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize(), retryEntry = retryEntry)
        }
    }
}

@Composable
fun PhotosGridScreen(photosList: List<MarsPhoto>, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(items = photosList, key = { photo -> photo.id }) { photo ->
            MarsPhotoCard(
                photo = photo,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
            )
        }
    }
}

@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        AsyncImage(
            model = photo.imgSrc,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier, retryEntry: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null,
        )
        Text("Error...", modifier = Modifier.padding(all = 16.dp))
        Button(onClick = retryEntry) {
            Text("Reintentar")
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
        Text(text = "Cargando")
    }
}
