package com.gildedrose

sealed class QualityItem(val item: Item) {

    open val sellIn = item.sellIn
    open val quality = item.quality

    fun increaseQuality(currentQuality: Int): Int {
        val quality = currentQuality + 1
        if (quality > 50) return 50
        return quality
    }

    fun decreaseQuality(currentQuality: Int): Int {
        val quality = if (sellIn > 0) {
            currentQuality - 1
        } else {
            currentQuality - 2
        }
        if (quality < 0) return 0
        return quality
    }

    open fun update(): QualityItem {
        val sellIn = item.sellIn - 1
        val quality = decreaseQuality(item.quality)
        return PlainItem(item.name, sellIn, quality)
    }

    class PlainItem(val name: String, override val sellIn: Int, override val quality: Int): QualityItem(Item(name, sellIn, quality))

    class AgedBrie(override val sellIn: Int, override val quality: Int): QualityItem(Item(agedBrie, sellIn, quality)) {
        override fun update(): QualityItem {
            val sellIn = item.sellIn - 1
            val quality = if (sellIn > 0) {
                increaseQuality(item.quality)
            } else {
                increaseQuality(increaseQuality(item.quality))
            }
            return AgedBrie(sellIn, quality)
        }
    }

    class ConcertBackstagePass(override val sellIn: Int, override val quality: Int): QualityItem(Item(concertBackstagePass, sellIn, quality)) {
        override fun update(): QualityItem {
            val sellIn = item.sellIn - 1
            if (sellIn <= 0) {
                return ConcertBackstagePass(sellIn, 0)
            }
            var quality = increaseQuality(item.quality)
            if (sellIn < 11) {
                quality = increaseQuality(quality)
            }
            if (sellIn < 6) {
                quality = increaseQuality(quality)
            }
            return ConcertBackstagePass(sellIn, quality)
        }
    }
    class Sulfuras(override val sellIn: Int): QualityItem(Item(sulfuras, sellIn, sulfurasQuality)) {

        override fun update() = Sulfuras(sellIn)
    }

    companion object {
        const val agedBrie = "Aged Brie"
        const val concertBackstagePass = "Backstage passes to a TAFKAL80ETC concert"
        // FOR THE HORDE!!
        const val sulfuras = "Sulfuras, Hand of Ragnaros"
        const val sulfurasQuality = 80

        fun create(item: Item): QualityItem = when(item.name) {
            agedBrie -> AgedBrie(item.sellIn, item.quality)
            concertBackstagePass -> ConcertBackstagePass(item.sellIn, item.quality)
            sulfuras -> Sulfuras(item.sellIn)
            else -> PlainItem(item.name, item.sellIn, item.quality)
        }
    }
}

class GildedRose(var items: Array<Item>) {

    var qualityItems = items.map(QualityItem::create)

    fun updateQuality() {
        qualityItems = qualityItems.map { it.update() }
    }

}

