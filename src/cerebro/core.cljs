(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.less-than-equal-to-less-than :as
             less-than-equal-to-less-than]
            [cerebro.mutations.double-equal-to-true :as
             double-equal-to-true]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.vm-mocha :as vm-mocha]
            [cerebro.lib.vm :as vm]
            [cerebro.lib.source-reader :as reader]
            [cerebro.lib.ast :as ast]
            [cerebro.lib.base-reporter :as reporter]
            [cerebro.lib.cli-arguments :as cli-arguments]))

(node/enable-util-print!)

;; https://github.com/istanbuljs/istanbuljs/blob/master/packages/istanbul-lib-instrument/api.md

;; TODO run a mutation only on covered paths, so
;; coverage and mutation traversing should go together

;; TODO run all mutations
(defn run
  "runs the example"
  [sourcePath testPath]
  (let [test (first (ast/string-to-ast (reader/read testPath)))
        source (first (ast/string-to-ast (reader/read sourcePath)))
        ;; result (less-than-equal-to-less-than/mutate source)
        result (double-equal-to-true/mutate source)
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
      (reporter/report original mutated))
    ))

(let [arguments (cli-arguments/handle (cli-arguments/setup))]
  (run (:source arguments) (:test arguments)))

