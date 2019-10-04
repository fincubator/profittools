package aam.dex.web

import java.util.concurrent.atomic.AtomicInteger

class CyclicCounter(private val range: IntRange) {
    private val diff = range.last - range.first
    private val ai = AtomicInteger(range.first)

    fun cyclicallyIncrementAndGet(): Int {
        var curVal: Int
        var newVal: Int
        do {
            curVal = ai.get()
            newVal = if (curVal >= range.last) curVal - diff else curVal
        } while (!ai.compareAndSet(curVal, ++newVal))
        return newVal + range.first
    }

}