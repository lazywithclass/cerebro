(ns cerebro.lib.mutation-applier
  (:require [cerebro.lib.source-reader :as reader]
            [cljs.nodejs :as node]))

(def true-to-false (node/require "./src/cerebro/mutations/true-to-false"))


(defn apply-mutation
  "tries to apply the given mutation to the given AST,
  returns a list of all mutated ASTs or
  an empty list if none were mutated"
  [path-ast mutation]
  ;; (prn (first (vals (first path-ast))))
  (map #(mutation (clj->js %)) path-ast))
;; (js->clj (map #(mutation %) (clj->js (rest path-ast)))))


(clj->js
 (apply-mutation
  (reader/to-ast (reader/read "./test/example-project/lib"))
  true-to-false))




;; var lib = {},
;; esrecurse = require('esrecurse');

;; // TODO probably I have to abstract away
;; // esrecurse in a utility module?

;; lib.mutants = (ast) => {
;;   var mutants = [];
;;   esrecurse.visit(ast, {
;;     Literal: function(node) {
;;       if (node.value === true) {
;;         lib.mutate(node);
;;         mutants.push(ast);
;;       }
;;    }
;;  });
;;  return mutants;
;;};

;; lib.mutate = (astNode) => {
;;   astNode.value = false;
;;   astNode.raw = 'false';
;; };
