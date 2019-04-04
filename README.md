# Hexagonal Architecture gone functional

Hexagonal architecture as polymorphic monad stack in Kotlin with Arrow

This project is a Work In Progress


## Async polymorphism

The domain does not integrate any dependency to the asynchronous model by using the `MonadDefer` abstraction from Arrow.  
You can create an infra using the RxJava implementation or Reactor or Arrow's IO, it does not change the model.


## Polymorphic usage:

```kotlin
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
```

This model provide a compilation check.


Async model consistency
```
domain<ForFluxK>(60)
    .run(monoDependencyContext)
```

```
Type mismatch.
Required: InfraContext<ForFluxK>
Found: InfraContext<ForMonoK>
```


InfraContext consistency
```
InfraContext(SingleK.monadDefer(), repository = SpeficicRepository())
```
```
Type inference failed: Cannot infer type parameter F in constructor InfraContext<F> ...
```

