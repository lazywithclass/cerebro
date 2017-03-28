(ns cerebro.mutations.less-than-equal-to-less-than
  (:require [cljs.nodejs :as node] ;; TODO qualify import
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.utils :as utils]))

(def esrecurse (node/require "esrecurse"))

(defn mutate
  "if we're dealing with <= then mutate to <"
  [node]
  (aset node "operator" "<"))

(defn loop-nodes
  "applies a mutation to the ASTs,
  returns the mutated, or non mutated, ASTs"
  [candidate]
  (.visit esrecurse (candidate :code) #js {:BinaryExpression mutate})
  candidate)

;; (loop-nodes
;;  (first (reader/to-ast
;;          (reader/read "./test/example-project/lib"))))
