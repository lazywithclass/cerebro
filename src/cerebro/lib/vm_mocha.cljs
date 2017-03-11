(ns cerebro.lib.vm-mocha
  (:require [cljs.nodejs :as node]
            [cerebro.lib.utils :as utils]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than
             :as less-than-equal-to-less-than]))

(def vm (node/require "vm"))
(def util (node/require "util"))

(def Mocha (node/require "mocha"))

(defn create-context
  "creates a context in which all required functions and
  variables are defined, so mocha can run fine.
  it does this by passing a mocha-context as payload to
  pre-required event, mocha-context is then enriched with
  all required functions, like it before after describe etc"
  []
  (let [mocha (Mocha. {:reporter "reporter"})
        mocha-context #js {:console js/console
                           :module js/module
                           :require node/require
                           :mocha mocha
                           :vm vm}]
    (.emit (.-suite mocha) "pre-require" mocha-context nil mocha)
    (.createContext vm mocha-context)))

;; TODO this could go in a generic vm ns
(defn run-in-context
  "runs the passed code in a custom context
  to emulate the run of the suite"
  [code context]
  (let [Script (.-Script vm)
        script (Script. code)]
    (.runInContext script context)))

;; TODO this could go in a generic vm ns
(defn mutant-killed?
  "returns true if the mutant was killed (tests red)
  returns false if the mutant is still alive (tests green)"
  [mocha-results]
  (not (= (.-failures mocha-results) 0)))
