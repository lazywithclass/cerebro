(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than :as
             less-than-equal-to-less-than]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.vm-mocha :as vm-mocha]
            [cerebro.lib.vm :as vm]
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.ast :as ast]))

(node/enable-util-print!)

(defn run
  "runs the example"
  []
  (let [test (first (ast/string-to-ast
                     (reader/read "./test")))
        mutated (less-than-equal-to-less-than/loop-nodes
                 (first
                  (ast/string-to-ast
                   (reader/read "./lib"))))
        code (str
              (ast/ast-to-string mutated.code)
              (ast/surround-with-iife (ast/ast-to-string test.code))
              ;; TODO this should be somehow added in vm-mocha
              "; mocha.run()")]
    (vm/mutant-killed?
     (vm/run-in-context code
                        (vm-mocha/create-context))
     ))) 

(run)


;; (set! (.-exports js/module) "oh hai"))
