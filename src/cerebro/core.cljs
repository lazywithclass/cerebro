(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than :as
             less-than-equal-to-less-than]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.vm-mocha :as vm-mocha]
            [cerebro.lib.source-reader :as reader]))

;; TODO dont mutate code that is not covered!

(defn run
  "runs the example"
  []
  (let [;; read source
        source (first (reader/to-ast (reader/read "./test/example-project/lib")))
        ;; read test
        test (first (vals (first (reader/to-ast (reader/read "./test/example-project/test")))))
        ;; apply the mutation
        mutated (less-than-equal-to-less-than/loop-nodes source)
        code (str
              (vm-mocha/ast-to-string mutated)
              (vm-mocha/ast-to-string test)
              "mocha.run()")]
    ;; (prn code)
    (vm-mocha/mutant-killed? (vm-mocha/run-in-context code (vm-mocha/create-context)))))

;; run tests
;; TODO fails because `check` has already been declared
(run)

