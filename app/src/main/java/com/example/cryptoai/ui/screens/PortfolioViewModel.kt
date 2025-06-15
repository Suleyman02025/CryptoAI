package com.example.cryptoai.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.cryptoai.ui.screens.PortfolioHolding
import com.example.cryptoai.ui.screens.getMockPortfolioHoldings
import com.example.cryptoai.ui.screens.TrendingCoin
import com.example.cryptoai.ui.screens.getTrendingCoins
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Transaction/order data class
data class PortfolioOrder(
    val type: String, // "Buy" or "Sell"
    val coin: String,
    val symbol: String,
    val amount: Double, // USD value
    val quantity: Double, // coin amount
    val date: String,
    val paymentMethod: String
)

class PortfolioViewModel : ViewModel() {
    // Portfolio holdings (mutable list)
    val portfolio: SnapshotStateList<PortfolioHolding> = mutableStateListOf<PortfolioHolding>().apply {
        addAll(getMockPortfolioHoldings())
    }
    // Orders/transactions
    val orders: SnapshotStateList<PortfolioOrder> = mutableStateListOf()

    // Helper to get trending coin data
    private fun getCoinData(symbol: String): TrendingCoin? = getTrendingCoins().find { it.symbol.equals(symbol, ignoreCase = true) }

    // Buy token
    fun buyToken(symbol: String, usdAmount: Double, paymentMethod: String) {
        val coinData = getCoinData(symbol) ?: return
        val price = coinData.price
        val quantity = usdAmount / price
        val existing = portfolio.find { it.symbol.equals(symbol, ignoreCase = true) }
        if (existing != null) {
            // Update existing holding
            val newAmount = existing.amount + quantity
            val idx = portfolio.indexOf(existing)
            portfolio[idx] = existing.copy(amount = newAmount)
        } else {
            // Add new holding
            portfolio.add(
                PortfolioHolding(
                    iconRes = coinData.iconRes,
                    iconBg = coinData.iconBg,
                    name = coinData.name,
                    symbol = coinData.symbol,
                    amount = quantity,
                    price = coinData.price,
                    changePercent = coinData.changePercent
                )
            )
        }
        // Add order
        orders.add(
            PortfolioOrder(
                type = "Buy",
                coin = coinData.name,
                symbol = coinData.symbol,
                amount = usdAmount,
                quantity = quantity,
                date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
                paymentMethod = paymentMethod
            )
        )
    }

    // Sell token
    fun sellToken(symbol: String, usdAmount: Double, paymentMethod: String): Boolean {
        val coinData = getCoinData(symbol) ?: return false
        val price = coinData.price
        val quantity = usdAmount / price
        val existing = portfolio.find { it.symbol.equals(symbol, ignoreCase = true) }
        if (existing == null || existing.amount < quantity) {
            // Not enough asset to sell
            return false
        }
        val newAmount = existing.amount - quantity
        val idx = portfolio.indexOf(existing)
        if (newAmount > 0) {
            portfolio[idx] = existing.copy(amount = newAmount)
        } else {
            portfolio.removeAt(idx)
        }
        // Add order
        orders.add(
            PortfolioOrder(
                type = "Sell",
                coin = coinData.name,
                symbol = coinData.symbol,
                amount = usdAmount,
                quantity = quantity,
                date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
                paymentMethod = paymentMethod
            )
        )
        return true
    }
} 