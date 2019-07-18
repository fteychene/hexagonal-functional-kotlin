package com.fteychene.demo.hexagonal.domain

import arrow.core.Either
import arrow.data.EitherT
import arrow.data.ReaderT
import arrow.data.extensions.eithert.monad.monad
import arrow.data.fix

sealed class Error

fun <F> domain(initialValue: Int) = ReaderT<F, Context<F>, Either<Error, String>> { ctx ->
    EitherT.monad<F, Error>(ctx).run {
        binding {
            ctx.log("Load value").bind()
            val result = ctx.loadValues(initialValue)
                    .map { it * 2 }.bind()
            ctx.log("Insert in database").bind()
            ctx.persist(result).bind()
        }.fix().value()
    }
}