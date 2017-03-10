(ns cerebro.lib.ast
  (:require [cljs.nodejs :as node] ;; qualify import
            [cerebro.lib.utils :as utils]
            [cerebro.lib.source-reader :as reader]))

(def esrecurse (node/require "esrecurse"))
(def acorn (node/require "acorn"))
(def escodegen (node/require "escodegen"))

(defrecord Candidate [path code])

(defn string-to-ast
  "given a coll of candidates path - file as string, returns a coll
  of candidates path - AST"
  [candidates]
  (map (fn [candidate]
         (Candidate. candidate.path (.parse acorn candidate.code)))
       candidates))

(defn ast-to-string
  "takes an ast and transforms it into a string"
  [ast]
  (utils/stringify ast)
  (.generate escodegen ast))

(defn surround-with-iife
  "surrounds the given code as string with an iife"
  [code]
  (str "(function() {" code "})()"))
