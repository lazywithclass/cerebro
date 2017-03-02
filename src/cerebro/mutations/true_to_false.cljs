(ns cerebro.mutations.true-to-false
  (:require [cljs.nodejs :as node] ;; qualify import?
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.utils :as utils]))

(def esrecurse (node/require "esrecurse"))

;; TODO it's important that this does not mutate in place
;;      so I could continue mutating in parallel

(defn mutate
  "if we're dealing with true then mutate"
  [node]
  (if (= (.-value node) true)
    (do
      (aset node "value" false)
      (aset node "raw" "false")
      node)
    node))

;; TODO losing path here
(defn loop-nodes
  "applies a mutation to the ASTs,
  returns the mutated, or non mutated, ASTs"
  [path-ast]
  (let [ast (first (vals path-ast))]
    (.visit esrecurse ast #js { :Literal mutate })
    ast))

;; (utils/stringify (loop-nodes
;;                   (first (reader/to-ast
;;                           (reader/read "./test/example-project/lib")))))
