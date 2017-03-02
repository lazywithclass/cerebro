## Cerebro

> “Welcome, Professor.” ―Cerebro

A mutation testing library for JavaScript.

To move from code representation to AST representation and vice versa Cerebro will use:

 * [escodegen](https://www.npmjs.com/package/escodegen)
 * [acorn](https://www.npmjs.com/package/acorn)
 * [esrecurse](https://www.npmjs.com/package/esrecurse)

### Wanted features

#### Speed

Cerebro will have to run several times for all the required mutations of the source code. It has to be fast.
An architectural decision is to change the files in memory, rather than writing to disk.

#### Parallel mutations

Mutations on different modules should happen in parallel.

#### Mutation types

All mutations should mutate the code in a reductive way, such as

```JavaScript
if (a > b) {} // --> if (a >= b) {}
```

while the following mutation is ok

```JavaScript
if (a >= b) {} // --> if (a > b) {}
```

this is done to avoid equivalent mutants. Which seems a Bad Thing.

#### Applicable to different frameworks

Without the need to have configuration, Cerebro should expect

 * tests to be in a test folder
 * `npm test` to run the unit tests
 * tests to run fast enough to allow for all mutations to take place in a reasonable time

and run with all major frameworks.

### Configuration

```
$ lein deps && npm install -g mocha
```

### Build

```
$ lein cljsbuild once
```

To avoid having to start the JVM each time 

```
$ lein cljsbuild auto
```

### Developing

`SPC m s i` connects to the CIDER REPL

### Test

```
$ mocha test/test.js
```
