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
