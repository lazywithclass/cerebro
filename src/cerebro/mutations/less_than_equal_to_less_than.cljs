(ns cerebro.mutations.less-than-equal-to-less-than
  (:require [cljs.nodejs :as node] ;; qualify import?
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.utils :as utils]))

(def esrecurse (node/require "esrecurse"))

;; TODO it's important that this does not mutate in place
;;      so I could continue mutating in parallel

(defn mutate
  "if we're dealing with <= then mutate to <"
  [node]
  (aset node "operator" "<")
  node)

;; TODO losing path here
(defn loop-nodes
  "applies a mutation to the ASTs,
  returns the mutated, or non mutated, ASTs"
  [path-ast]
  (let [ast (first (vals path-ast))]
    (.visit esrecurse ast #js {:BinaryExpression mutate})
    ast))

(utils/stringify (loop-nodes
                  (first (reader/to-ast
                          (reader/read "./test/example-project/lib")))))

