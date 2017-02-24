const esrecurse = require('esrecurse')

const mutate = (pathAst) => {
  let path = Object.keys(pathAst)[0], ast = pathAst[path]
  esrecurse.visit(ast, {
    Literal: (node) => {
      node.value = false
      node.raw = 'false'
    }
  })
  return ast
}

module.exports = mutate

// (ns cerebro.mutations.true-to-false
//   (:require [cljs.nodejs :as node]
//             [cerebro.lib.source-reader :as reader]))
// (def esrecurse (node/require "esrecurse"))

// (defn mutate?
//   "applies a mutation to the ASTs,
//   returns the mutated, or non mutated, ASTs"
//   [asts mutation]

//   ;; (map
//   ;;  #(.visit esrecurse (first (vals %)) (:Literal (fn [node] node)))
//   ;;  ;; #(prn (first (vals %)))
//   ;;  asts))

// (mutate? (reader/to-ast (reader/read "./test/example-project/lib")) nil)
