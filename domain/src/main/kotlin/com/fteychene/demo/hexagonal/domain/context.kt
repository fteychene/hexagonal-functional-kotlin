package com.fteychene.demo.hexagonal.domain

import arrow.effects.typeclasses.MonadDefer

open class Context<F>(val monadDefer: MonadDefer<F>,
                      val dataFetcher: DataFetcher<F>,
                      val repository: Repository<F>,
                      val logger: Logger<F>) : DataFetcher<F> by dataFetcher, Repository<F> by repository, Logger<F> by logger