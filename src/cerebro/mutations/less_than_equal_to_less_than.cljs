(ns cerebro.mutations.less-than-equal-to-less-than
  (:require [cljs.nodejs :as node] ;; TODO qualify import
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.utils :as utils]))

(def esrecurse (node/require "esrecurse"))

(defn copy-obj
  "creates a copy of a JS object"
  [o]
  (.parse js/JSON (.stringify js/JSON o)))

(defn mutate
  "mutate <= to <"
  [node]
  (aset node "operator" "<"))

(defn loop-nodes
  "applies a mutation to the ASTs,
  returns the mutated, or non mutated, ASTs"
  [candidate]
  (let [original-code (copy-obj (candidate :code))]
    (.visit esrecurse (candidate :code) (clj->js {:BinaryExpression mutate}))
    (hash-map :original
              (hash-map :path (candidate :path) :code original-code)
              :mutated
              (hash-map :path (candidate :path) :code (candidate :code)))))

;; (loop-nodes
;;  (first (reader/to-ast
;;          (reader/read "./test/example-project/lib"))))
