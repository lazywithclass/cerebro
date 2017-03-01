(ns cerebro.lib.vm-mocha
  (:require [cljs.nodejs :as node]
            [cerebro.lib.utils :as utils]
            [cerebro.lib.source-reader :as reader]
            [cerebro.mutations.true-to-false :as true-to-false]))

(def escodegen (node/require "escodegen"))
(def vm (node/require "vm"))
(def util (node/require "util"))

;; TODO this only works for mocha
;; TODO it should use mocha from the target project being
;;      tested, not from this project
(def Mocha (node/require "mocha"))

(defn ast-to-string
  "takes an ast and transforms it into a string"
  [ast]
  (.generate escodegen ast))

(defn create-context
  "creates a context in which all required functions and
  variables are defined, so mocha can run fine.
  it does this by passing a mocha-context as payload to
  pre-required event, mocha-context is then enriched with
  all required functions, like it before after describe etc"
  []
  (def Mocha (node/require "mocha"))
  (let [mocha (Mocha. {:reporter "reporter"})
        mocha-context #js {:console js/console
                           :require node/require
                           :mocha mocha}]
    (.emit (.-suite mocha) "pre-require" mocha-context nil mocha)
    (.createContext vm mocha-context)))

(defn run-in-context
  "runs the passed code in a custom context
  to emulate the run of the suite"
  [code context]
  ;; omg my eyes
  (let [Script (.-Script js/vm)
        script (Script. code)]
    (.runInContext script context)))

(defn mutant-killed?
  "returns true if the mutant was killed (tests red)
  returns false if the mutant is still alive (tests green)"
  [mocha-results]
  (not (= (.-failures mocha-results) 0)))

(mutant-killed? (run-in-context "
let assert = require('assert')
let sum = (a, b) => a + b
it('sums two numbers', () => {
  assert.equal(sum(1, 2), 3)
})
mocha.run()
" (create-context)))


;; TODO
;; mockRequire('./source', eval(mutant))
;; global.module = module;
;; global.require = require;
;; global.path = path;
;; global.__dirname = __dirname;

;; (ast-to-string
;;  (true-to-false/loop-nodes
;;   (first (reader/to-ast
;;    (reader/read "./test/example-project/lib")))))
