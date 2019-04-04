package com.fteychene.demo.hexagonal.domain

import arrow.core.Either
import arrow.data.EitherT
import arrow.data.ReaderT
import arrow.data.extensions.eithert.monad.monad
import arrow.data.fix

sealed class Error


fun <F> domain(initialValue: Int) = ReaderT<F, Context<F>, Either<Error, String>> {
    it.run {
        EitherT.monad<F, Error>(monadDefer).run {
            binding {
                logger.log("Load value").bind()
                val result = dataFetcher.loadValues(initialValue)
                        .map { it * 2 }.bind()
                logger.log("Insert in database").bind()
                repository.persist(result).bind()
            }.fix().value()
        }
    }
}


