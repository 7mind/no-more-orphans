# No More Orphans

This is a sample project that defines a couple optional typeclass instances using [No More Orphans](https://blog.7mind.io/no-more-orphans.html) trick.
More details are in the accompanying blog post.

Use SBT to run the test suite:

```
git clone https://github.com/7mind/no-more-orphans.git
cd no-more-orphans
sbt test
```

Tested on Scala 2.12.9, 2.13.0, 2.11.12 and [dotty-0.16.0-RC3](https://github.com/7mind/no-more-orphans/tree/dotty)

Dotty treats intersection types differently, so one of the problems with Scala 2 encoding is manifest for ALL implicits, not just optional:

- https://github.com/lampepfl/dotty/issues/6384
