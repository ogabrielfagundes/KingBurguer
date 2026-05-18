package com.example.kingburguer.compose.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.compose.component.KingAlert
import com.example.kingburguer.compose.component.KingButton
import com.example.kingburguer.data.CategoryDetailResponse
import com.example.kingburguer.data.ProductDetailResponse
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.ProductViewModel
import java.util.Date

@Composable
fun ProductScreen(
    modifier: Modifier,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModel.factory),
    onCouponGenerated: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    ProductScreen(
        modifier = modifier,
        state = state,
        couponClicked = { viewModel.createCoupon() },
        onCouponGenerated = {
            viewModel.reset()
            onCouponGenerated()
        }
    )
}

@Composable
fun ProductScreen(
    modifier: Modifier,
    state: ProductUiState,
    couponClicked: () -> Unit,
    onCouponGenerated: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(state.error, color = MaterialTheme.colorScheme.primary)
            }

            else -> {
                state.productDetail?.let {
                    Product2Screen(modifier = modifier, product = state.productDetail, couponClicked)
                }
                state.coupon?.let  {
                    KingAlert(
                        onDismissRequest = {},
                        onConfirmation = onCouponGenerated,
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = stringResource(id = R.string.coupon_generated, state.coupon.coupon),
                        icon = Icons.Filled.Info
                    )
                }
            }
        }
    }
}


@Composable
fun Product2Screen(
    modifier: Modifier = Modifier,
    product: ProductDetailResponse,
    couponClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    model = product.pictureUrl,
                    placeholder = painterResource(R.drawable.example),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp),
                        text = product.price.currency(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )

                }

                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    text = product.description
                )
            }

            KingButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.get_coupon),
                onClick = couponClicked
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightProductScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        Product2Screen(
            product = ProductDetailResponse(
                id = 1,
                name = "Product A",
                description = "Teste de titulo de produto que seja realmente grande",
                pictureUrl = "",
                price = 21.99,
                createdDate = Date(),
                categoryResponse = CategoryDetailResponse(
                    id = 1,
                    name = "Cat test"
                )
            )

        ) {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkProductScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        Product2Screen(
            product = ProductDetailResponse(
                id = 1,
                name = "Product A",
                description = "Teste de titulo de produto que seja realmente grande",
                pictureUrl = "",
                price = 21.99,
                createdDate = Date(),
                categoryResponse = CategoryDetailResponse(
                    id = 1,
                    name = "Cat test"
                )
            )
        ) {}
    }
}