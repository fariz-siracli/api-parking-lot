package com.fs.api.parking.lot;

import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom

import java.nio.charset.Charset

class TestUtils {
    public static final EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .seed(123)
            .randomizationDepth(3)
            .charset(Charset.forName("UTF-8"))
            .stringLengthRange(5, 10)
            .collectionSizeRange(1, 2)
            .scanClasspathForConcreteTypes(true)
            .overrideDefaultInitialization(false)
            .build()
} 