package com.kriskda.dogscats.domain

import java.nio.ByteBuffer

interface DogsCatsDetector {
    suspend fun runDetection(byteBuffer: ByteBuffer): FloatArray
}