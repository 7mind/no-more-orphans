# No More Orphans

This is a sample project that defines a couple optional typeclass instances using [No More Orphans](https://blog.7mind.io/no-more-orphans.html) trick.
More details are in the accompanying blog post.

Use SBT to run the test suite:

```
git clone https://github.com/7mind/no-more-orphans.git
cd no-more-orphans
sbt test
```

Tested on Scala 2.12.12, 2.13.3, 2.11.12 (2.11 only works partially) and [dotty](https://github.com/7mind/no-more-orphans/tree/dotty)

Addendum: the pattern may not work for non-higher-kinded types on Scala 2 (i.e. plain types like `Int`, not type constructors like `List[_]` or typeclasses like `Monad[_[_]]`).
