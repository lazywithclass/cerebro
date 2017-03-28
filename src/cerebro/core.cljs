(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.true-to-false :as true-to-false]
            [cerebro.mutations.less-than-equal-to-less-than :as
             less-than-equal-to-less-than]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.vm-mocha :as vm-mocha]
            [cerebro.lib.vm :as vm]
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.ast :as ast]
            [cerebro.lib.base-reporter :as reporter]
            [cerebro.lib.cli-arguments :as cli-arguments]))

(node/enable-util-print!)


(defn run
  "runs the example"
  [sourcePath testPath]
  (let [test (first (ast/string-to-ast (reader/read testPath)))
        source (first (ast/string-to-ast (reader/read sourcePath)))
        mutated (less-than-equal-to-less-than/loop-nodes source)
        code (str
              (ast/ast-to-string mutated.code)
              (ast/surround-with-iife (ast/ast-to-string test.code))
              (vm-mocha/produce-muted-reporter)
              (vm-mocha/produce-mocha-run))]

    ;; TODO find a better way to get the non mutated code
    ;; which should be passing a clone to the mutation, not
    ;; actual thing
    (let [original (ast/ast-to-string
                    (.-code (first (ast/string-to-ast (reader/read "./lib")))))
          mutated (ast/ast-to-string mutated.code)
          killed? (vm-mocha/mutant-killed?
                   (vm/run-in-context (ast/set-paths-relative-to-project-root code)
                                      (vm-mocha/create-context)))]
      (if (not killed?)
        (reporter/report original mutated)))))


(let [arguments (cli-arguments/handle (cli-arguments/setup))]
  (run (:source arguments) (:test arguments)))
