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
  (let [mocha (Mocha. {:reporter "reporter"})
        mocha-context #js {:console js/console
                           :module js/module
                           :require node/require
                           :mocha mocha}]
    (.emit (.-suite mocha) "pre-require" mocha-context nil mocha)
    (.createContext vm mocha-context)))

