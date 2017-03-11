(ns cerebro.lib.vm
  (:require [cljs.nodejs :as node]))

(def vm (node/require "vm"))

(defn run-in-context
  "runs the passed code in a custom context
  to emulate the run of the suite"
  [code context]
  (let [Script (.-Script vm)
        script (Script. code)]
    (.runInContext script context)))

(defn mutant-killed?
  "returns true if the mutant was killed (tests red)
  returns false if the mutant is still alive (tests green)"
  [mocha-results]
  (not (= (.-failures mocha-results) 0)))
