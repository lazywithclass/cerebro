(ns cerebro.mutations.utils
  (:require [cljs.nodejs :as node]))

(def estools (node/require "estools"))
(def esrecurse (node/require "esrecurse"))

(defn copy-obj
  "creates a copy of a JS object"
  [o]
  (.parse js/JSON (.stringify js/JSON o)))

(defn mutate-nodes
  "applies a mutation to the ASTs,returns the mutated, or non mutated, ASTs"
  [candidate mutate-expr]
  (let [original-code (copy-obj (candidate :code))]
    (.visit esrecurse (candidate :code) (clj->js mutate-expr))
    (hash-map :original
              (hash-map :path (candidate :path) :code original-code)
              :mutated
              (hash-map :path (candidate :path) :code (candidate :code)))))
