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
        result (less-than-equal-to-less-than/loop-nodes source)
        mutated (ast/ast-to-string ((result :mutated) :code))
        original (ast/ast-to-string ((result :original) :code))
        code (str
              mutated
              (ast/surround-with-iife (ast/ast-to-string (test :code)))
              (vm-mocha/produce-muted-reporter)
              (vm-mocha/produce-mocha-run))
        mocha-run-result (vm/run-in-context (ast/set-paths-relative-to-project-root code)
                                            (vm-mocha/create-context))
        mutant-alive? (vm-mocha/mutant-alive? mocha-run-result)]

    ;; TODO this also has to check whether or not at least one mutation was applied
    (if mutant-alive?
      (reporter/report original mutated))))

(let [arguments (cli-arguments/handle (cli-arguments/setup))]
  (run (:source arguments) (:test arguments)))
