package com.example.testutils.assertions

import org.assertj.core.api.Assertions
import org.assertj.core.util.DoubleComparator
import org.junit.Assert.assertTrue
import java.time.Instant
import java.time.temporal.ChronoUnit

infix fun Any?.shouldEqualRecursively(other: Any?) = Assertions
        .assertThat(this)
        .usingComparatorForType(DoubleComparator(0.01), Double::class.java)
        .usingComparatorForType({ o1, o2 ->
            o1.truncatedTo(ChronoUnit.MILLIS).compareTo(o2.truncatedTo(ChronoUnit.MILLIS))
        }, Instant::class.java)
        .isEqualToComparingFieldByFieldRecursively(other)

inline fun <reified T : Any> T?.shouldNotNull(): T {
    assertTrue("Expected ${T::class.java.canonicalName} to be not null", this != null)
    return this!!
}

