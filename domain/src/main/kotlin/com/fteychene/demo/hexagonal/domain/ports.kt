package com.fteychene.demo.hexagonal.domain

import arrow.data.EitherT

/**
 * Ports
 */

interface Logger<F> {

    fun log(message: String): EitherT<F, Error, Unit>
}

interface DataFetcher<F> {

    fun loadValues(id: Int): EitherT<F, Error, Int>
}

interface Repository<F> {

    fun persist(value: Int): EitherT<F, Error, String>
}