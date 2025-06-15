package com.example.cryptoai.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.screens.*
import com.example.cryptoai.ui.screens.PortfolioScreen
import com.example.cryptoai.ui.screens.BuySellScreen
import androidx.lifecycle.viewmodel.compose.viewModel

import kotlinx.coroutines.delay


sealed class Screen {
    object Splash : Screen()
    data class Intro(val page: Int) : Screen()
    object Login : Screen()
    object Main : Screen()
    object Portfolio : Screen()
    object Trending : Screen()
    data class CoinDetail(val coin: String) : Screen()
    data class BuySell(val type: String) : Screen()
    object PaymentMethod : Screen()
    object OrderSummary : Screen()
    object MyCards : Screen()
    object Orders : Screen()
}

@Composable
fun NavigationScreen() {
    val context = LocalContext.current
    var ordersList by remember { mutableStateOf(listOf<OrderSummary>()) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var selectedCoin by rememberSaveable { mutableStateOf("BTC") }
    var buySellType by rememberSaveable { mutableStateOf("Buy") }
    var selectedPaymentCardIndex by rememberSaveable { mutableStateOf(0) }
    var onboardingPage by rememberSaveable { mutableStateOf(0) }
    var buySellAmount by rememberSaveable { mutableStateOf(0.0) }

    val navStack = rememberSaveable(
        saver = listSaver<SnapshotStateList<Screen>, String>(
            save = { it.map { screen -> screen.toString() } },
            restore = { list ->
                mutableStateListOf<Screen>().apply {
                    list.forEach {
                        when {
                            it == Screen.Splash.toString() -> add(Screen.Splash)
                            it.startsWith("Intro") -> add(Screen.Intro(it.removePrefix("Intro(").removeSuffix(")").toInt()))
                            it == Screen.Login.toString() -> add(Screen.Login)
                            it == Screen.Main.toString() -> add(Screen.Main)
                            it == Screen.Portfolio.toString() -> add(Screen.Portfolio)
                            it == Screen.Trending.toString() -> add(Screen.Trending)
                            it.startsWith("CoinDetail") -> add(Screen.CoinDetail(it.removePrefix("CoinDetail(").removeSuffix(")")))
                            it.startsWith("BuySell") -> add(Screen.BuySell(it.removePrefix("BuySell(").removeSuffix(")")))
                            it == Screen.PaymentMethod.toString() -> add(Screen.PaymentMethod)
                            it == Screen.OrderSummary.toString() -> add(Screen.OrderSummary)
                            it == Screen.MyCards.toString() -> add(Screen.MyCards)
                            it == Screen.Orders.toString() -> add(Screen.Orders)
                        }
                    }
                }
            }
        )
    ) { mutableStateListOf<Screen>(Screen.Splash) }

    val currentScreen = navStack.lastOrNull() ?: Screen.Splash

    LaunchedEffect(Unit) {
        if (currentScreen is Screen.Splash) {
        delay(1500)
            navStack.removeLast()
            navStack.add(Screen.Intro(0))
        }
    }

    BackHandler(enabled = navStack.size > 1) {
        navStack.removeLast()
    }

    when (val screen = currentScreen) {
        is Screen.Splash -> SplashScreen()
        is Screen.Intro -> IntroScreen(
            currentPage = screen.page,
            onContinue = {
                if (screen.page == 0) {
                    navStack.add(Screen.Intro(1))
            } else {
                    navStack.add(Screen.Login)
            }
            }
        )
        is Screen.Login -> LoginScreen(onLogin = {
            navStack.add(Screen.Main)
        })
        is Screen.Main -> Column(modifier = Modifier.fillMaxSize()) {
            val portfolioViewModel: PortfolioViewModel = viewModel()
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> HomeScreen(
                        viewModel = portfolioViewModel,
                        onPortfolioClick = { navStack.add(Screen.Portfolio) },
                        onSeeAllTrending = { navStack.add(Screen.Trending) }
                    )
                    1 -> MarketScreen(onCoinClick = { coin ->
                        selectedCoin = coin
                        navStack.add(Screen.CoinDetail(coin))
                    })
                    2 -> ChatScreen()
                    3 -> ProfileScreen(
                        onLogout = {
                            // Clear stack and go to login
                            navStack.clear()
                            navStack.add(Screen.Login)
                        },
                        onMyCardsClick = { navStack.add(Screen.MyCards) },
                        onMyOrdersClick = { navStack.add(Screen.Orders) }
                    )
                }
            }
            BottomBar(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        }
        is Screen.Portfolio -> {
            val portfolioViewModel: PortfolioViewModel = viewModel()
            PortfolioScreen(viewModel = portfolioViewModel, onBack = { navStack.removeLast() })
        }
        is Screen.Trending -> TrendingScreen(onBack = { navStack.removeLast() })
        is Screen.CoinDetail -> CoinDetailScreen(
            coin = screen.coin,
            onBack = { navStack.removeLast() },
            onBuyClick = {
                buySellType = "Buy"
                navStack.add(Screen.BuySell("Buy"))
            },
            onSellClick = {
                buySellType = "Sell"
                navStack.add(Screen.BuySell("Sell"))
            }
        )
        is Screen.BuySell -> BuySellScreen(
            type = screen.type,
            symbol = selectedCoin,
            onBack = { navStack.removeLast() },
            onContinue = { amount ->
                buySellAmount = amount
                navStack.add(Screen.PaymentMethod)
            }
        )
        is Screen.PaymentMethod -> PaymentMethodScreen(
            onBack = { navStack.removeLast() },
            onContinue = {
                navStack.add(Screen.OrderSummary)
            }
        )
        is Screen.OrderSummary -> OrderSummaryScreen(
            type = buySellType,
            coinSymbol = selectedCoin,
            amount = buySellAmount,
            paymentMethod = when (selectedPaymentCardIndex) {
                0 -> "VISA"
                1 -> "MASTERCARD"
                2 -> "PAYPAL"
                else -> "VISA"
            },
            onBack = { navStack.removeLast() },
            onOrderConfirmed = {
                navStack.removeLast()
                navStack.add(Screen.Orders)
            }
        )
        is Screen.MyCards -> MyCardsScreen(onBack = { navStack.removeLast() })
        is Screen.Orders -> OrdersScreen(onBack = { navStack.removeLast() })
    }
}

@Composable
fun BottomBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val items = listOf(
        Triple("Home", R.drawable.ic_home, 0),
        Triple("Market", R.drawable.ic_market, 1),
        Triple("Chat", R.drawable.ic_chat, 2),
        Triple("Profile", R.drawable.ic_profile, 3)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18181C))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEach { (label, icon, index) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onTabSelected(index) }
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = if (selectedTab == index) Color(0xFF40BF6A) else Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    color = if (selectedTab == index) Color(0xFF40BF6A) else Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
} 



