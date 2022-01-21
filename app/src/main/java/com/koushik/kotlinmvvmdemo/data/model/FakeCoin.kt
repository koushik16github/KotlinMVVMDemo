package com.koushik.kotlinmvvmdemo.data.model

object FakeCoin {

    val coins = arrayOf(
        Coin(
            "btc-bitcoin",
            true,
            "Bitcoin",
            1,
            "BTC"
        ),
        Coin(
            "eth-ethereum",
            true,
            "Ethereum",
            2,
            "ETH"
        ),
        Coin(
            "usdt-tether",
            true,
            "Tether",
            3,
            "USDT"
        ),
        Coin(
            "bnb-binance-coin",
            true,
            "Binance Coin",
            4,
            "BNB"
        ),
        Coin(
            "hex-hex",
            true,
            "HEX",
            5,
            "HEX"
        )
    )
}