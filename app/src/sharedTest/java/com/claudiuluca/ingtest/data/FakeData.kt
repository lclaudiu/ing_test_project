package com.claudiuluca.ingtest.data

import com.claudiuluca.ingtest.data.api.Campaign
import com.claudiuluca.ingtest.data.api.Channel
import com.claudiuluca.ingtest.data.api.SelectedCampaign
import com.claudiuluca.ingtest.data.api.Target

val fakeTargetsList = listOf(
    Target(
        title = "Location",
        channels = listOf("Facebook", "Instagram")
    ),
    Target(
        title = "Events",
        channels = listOf("Facebook", "SEO")
    )
)

val fakeChannelsList = listOf(
    Channel(
        channelName = "Facebook",
        campaigns = listOf(
            Campaign(
                price = "80 EUR",
                details = listOf("3-8 listings", "12 optimizations")
            ),
            Campaign(
                price = "180 EUR",
                details = listOf("8-15 listings", "20 optimizations")
            )
        )
    ),
    Channel(
        channelName = "Instagram",
        campaigns = listOf(
            Campaign(
                price = "90 EUR",
                details = listOf("1-2 listings", "5 optimizations")
            ),
            Campaign(
                price = "180 EUR",
                details = listOf("8-10 listings", "10 optimizations")
            )
        )
    ),
    Channel(
        channelName = "SEO",
        campaigns = listOf(
            Campaign(
                price = "60 EUR",
                details = listOf("1-5 listings", "3 optimizations")
            ),
            Campaign(
                price = "100 EUR",
                details = listOf("7-10 listings", "7 optimizations")
            )
        )
    )
)

val campaign = Campaign(
    price = "80 EUR",
    details = listOf("3-8 listings", "12 optimizations")
)

val selectedCampaign = SelectedCampaign(
    targetName = "Location",
    channelName = "Facebook",
    campaign = campaign
)