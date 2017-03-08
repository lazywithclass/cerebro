(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than :as
             less-than-equal-to-less-than]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.vm-mocha :as vm-mocha]
            [cerebro.lib.source-reader :as reader]))

(defn run
  "runs the example"
  []
  (let [test (first (reader/to-ast (reader/read "./test/example-project/test")))
        mutated (less-than-equal-to-less-than/loop-nodes
                 (first
                  (reader/to-ast
                   (reader/read "./test/example-project/lib"))))
        code (str
              (vm-mocha/ast-to-string mutated.code)
              (vm-mocha/ast-to-string test.code)
              "mocha.run()")
        ]


    (vm-mocha/mutant-killed? (vm-mocha/run-in-context code (vm-mocha/create-context))))) 

;; run tests
;; TODO fails because `check` has already been declared
(run)

