## Cerebro

> “Welcome, Professor.” ―Cerebro

A mutation testing library for JavaScript.


### Configuration

```bash
$ lein deps && npm install
$ cd test/example-project/
$ npm install
```


### Build

```
$ lein cljsbuild once $env
```

`$env` could be either `prod` or `dev`. To avoid having to start the JVM each time 

```
$ lein cljsbuild auto $env
```


### Running the example

```bash
# compile Cerebro to JavaScript
$ lein cljsbuild once prod

$ cp cerebro.js test/example-project/
$ cd test/example-project/

# run it
$ node cerebro.js -s lib/source.js -t ./test
```


### Developing

`SPC m s i` connects to the CIDER REPL
