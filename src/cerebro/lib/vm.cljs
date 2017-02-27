(ns cerebro.lib.vm
  (:require [cljs.nodejs :as node]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.source-reader :as reader]
            [cerebro.mutations.true-to-false :as true-to-false]))

(def escodegen (node/require "escodegen"))

(defn ast-to-string
  "takes an ast and transforms it into a string"
  [ast]
  (.generate escodegen ast))

(ast-to-string
 (true-to-false/loop-nodes
  (first (reader/to-ast
   (reader/read "./test/example-project/lib")))))
