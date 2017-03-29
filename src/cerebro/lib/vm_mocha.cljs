(ns cerebro.lib.vm-mocha
  (:require [cljs.nodejs :as node]
            [cerebro.lib.utils :as utils]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than
             :as less-than-equal-to-less-than]))

(def util (node/require "util"))
(def vm (node/require "vm"))
(def Mocha (node/require "mocha"))

(defn create-context
  "creates a context in which all required functions and
  variables are defined, so mocha can run fine.
  it does this by passing a mocha-context as payload to
  pre-required event, mocha-context is then enriched with
  all required functions, like it before after describe etc"
  []
  (let [mocha (Mocha.)
        mocha-context #js {:console js/console
                           :module js/module
                           :require node/require
                           :Mocha Mocha
                           :mocha mocha}]
    (.emit (.-suite mocha) "pre-require" mocha-context nil mocha)
    (.createContext vm mocha-context)))

(defn mutant-alive?
  "returns false if the mutant was killed, tests are red >0 failures
  returns true if the mutant was not killed so it's still alive, tests are green 0 failures"
  [mocha-results]
  (= (.-failures mocha-results) 0))

(defn produce-muted-reporter
  "returns a reporter that does not report anything"
  []
  ";mocha.reporter(function(runner) {
     Mocha.reporters.Base.call(this, runner);
     runner.on('pass', function() {});
     runner.on('fail', function() {});
     runner.on('end', function() {});
  });")

(defn produce-mocha-run
  "returns the instruction to run mocha"
  []
  ";mocha.run();")
