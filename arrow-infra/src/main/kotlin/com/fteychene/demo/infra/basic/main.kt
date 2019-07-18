package com.fteychene.demo.infra.basic

import arrow.core.Either
import arrow.core.right
import arrow.data.EitherT
import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.extensions.io.monadDefer.monadDefer
import arrow.effects.fix
import arrow.effects.reactor.*
import arrow.effects.reactor.extensions.fluxk.monadDefer.monadDefer
import arrow.effects.reactor.extensions.monok.monadDefer.monadDefer
import arrow.effects.rx2.*
import arrow.effects.rx2.extensions.observablek.monadDefer.monadDefer
import arrow.effects.rx2.extensions.singlek.monadDefer.monadDefer
import arrow.effects.typeclasses.MonadDefer
import com.fteychene.demo.hexagonal.domain.*
import reactor.core.publisher.Mono


class InfraContext<F>(monadDefer: MonadDefer<F>,
                      dataFetcher: DataFetcher<F> = GenericDataFetcher(monadDefer),
                      repository: Repository<F> = GenericRepository(monadDefer),
                      logger: Logger<F> = ConsoleLogger(monadDefer)) : Context<F>(monadDefer, dataFetcher, repository, logger)

/*
 * Adapters
 */

class ConsoleLogger<F>(MDF: MonadDefer<F>) : Logger<F>, MonadDefer<F> by MDF {

    override fun log(message: String): EitherT<F, Error, Unit> =
            EitherT.liftF(this, delay { println(message) })
}

class GenericDataFetcher<F>(MDF: MonadDefer<F>) : DataFetcher<F>, MonadDefer<F> by MDF {

    override fun loadValues(id: Int): EitherT<F, Error, Int> =
            EitherT.just(this, id)

}

class GenericRepository<F>(MDF: MonadDefer<F>) : Repository<F>, MonadDefer<F> by MDF {

    override fun persist(value: Int): EitherT<F, Error, String> =
            EitherT.just(this, value.toString())

}

class SpeficicRepository : Repository<ForMonoK> {

    override fun persist(value: Int): EitherT<ForMonoK, Error, String> =
            EitherT(Mono.just(value.right() as Either<Error, String>).k())

}

fun main() {
    println("Run with Arrow IO")
    val ioDependencyContext = InfraContext(IO.monadDefer())
    val io = domain<ForIO>(60).run(ioDependencyContext).fix()
    io.attempt().unsafeRunAsync { println(it) }
    println()

    println("Run with RxJava Single")
    val singleDependencyContext = InfraContext(SingleK.monadDefer())
    val single = domain<ForSingleK>(60).run(singleDependencyContext).fix()
    single.single.subscribe(::println, ::println)
    println()

    println("Run with RxJava Observable")
    val observableDependencyContext = InfraContext(ObservableK.monadDefer())
    val observable = domain<ForObservableK>(60).run(observableDependencyContext).fix()
    observable.observable.subscribe(::println, ::println)
    println()

    println("Run with Reactor Mono")
    val monoDependencyContext = InfraContext(MonoK.monadDefer(), repository = SpeficicRepository())
    val mono = domain<ForMonoK>(60).run(monoDependencyContext).fix()
    mono.mono.subscribe(::println, ::println)
    println()

    println("Run with Reactor Flux")
    val fluxDependencyContext = InfraContext(FluxK.monadDefer())
    val flux = domain<ForFluxK>(60).run(fluxDependencyContext).fix()
    flux.flux.subscribe(::println, ::println)
    println()
}