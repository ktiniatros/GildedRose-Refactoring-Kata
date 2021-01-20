package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun `given a Sulfuras when days pass then quality and sellIn do not change`() {
        val items = arrayOf(Item("Sulfuras, Hand of Ragnaros", 90, 80))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(90, app.qualityItems.first().sellIn)
        assertEquals(80, app.qualityItems.first().quality)
    }

    @Test
    fun `given a plain item when days pass then quality degrades`() {
        val items = arrayOf(Item("Plain item", 9, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(9, app.qualityItems.first().quality)
        assertEquals(8, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given an Aged Brie when days pass then quality improves`() {
        val items = arrayOf(Item("Aged Brie", 9, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(11, app.qualityItems.first().quality)
        assertEquals(8, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 80 quality when days pass and still not close to concert date then quality improves`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 81, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(11, app.qualityItems.first().quality)
        assertEquals(80, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 49 quality when days pass and still not close to concert date then quality improves`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 81, 49))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(80, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 49 quality when days pass and closer to concert date then quality improves`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 10, 49))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(9, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 49 quality when days pass and even closer to concert date then quality improves`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 5, 49))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(4, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 48 quality when days pass and closer to concert date then quality improves twice as fast`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 10, 48))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(9, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 48 quality when days pass and even closer to concert date then quality improves two times faster`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 5, 48))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(4, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a Backstage pass of 46 quality when days pass and even closer to concert date then quality improves three times faster`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 5, 46))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(49, app.qualityItems.first().quality)
        assertEquals(4, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given an Aged Brie when it expires then its quality still improves`() {
        val items = arrayOf(Item("Aged Brie", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(12, app.qualityItems.first().quality)
        assertEquals(-1, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a plain item when it expires then its quality degrades twice as fast`() {
        val items = arrayOf(Item("Plain item", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(8, app.qualityItems.first().quality)
        assertEquals(-1, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a concert when happened its quality becomes zero`() {
        val items = arrayOf(Item("Backstage passes to a TAFKAL80ETC concert", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(0, app.qualityItems.first().quality)
        assertEquals(-1, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a plain item of low quality when updates its quality is not negative`() {
        val items = arrayOf(Item("Plain item", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(0, app.qualityItems.first().quality)
        assertEquals(-1, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given an Aged Brie of high quality when updates its quality equal to 50`() {
        val items = arrayOf(Item("Aged Brie", 10, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.qualityItems.first().quality)
        assertEquals(9, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given a conjured item when days pass then quality degrades`() {
        val items = arrayOf(Item("Conjured", 1, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(8, app.qualityItems.first().quality)
        assertEquals(0, app.qualityItems.first().sellIn)
    }

    @Test
    fun `given an expired conjured item when days pass then quality degrades more`() {
        val items = arrayOf(Item("Conjured", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(7, app.qualityItems.first().quality)
        assertEquals(-1, app.qualityItems.first().sellIn)
    }

}


