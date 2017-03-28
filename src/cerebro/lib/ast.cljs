(ns cerebro.lib.ast
  (:require [cljs.nodejs :as node] ;; qualify import
            [cerebro.lib.utils :as utils]
            [cerebro.lib.source-reader :as reader]
            [clojure.string :as str]))

(def esrecurse (node/require "esrecurse"))
(def acorn (node/require "acorn"))
(def escodegen (node/require "escodegen"))


(defn string-to-ast
  "given a coll of candidates path - file as string, returns a coll
  of candidates path - AST"
  [candidates]
  (map (fn [candidate]
         (hash-map
          :path (candidate :path)
          :code (.parse acorn (candidate :code))))
       candidates))

(defn ast-to-string
  "takes an ast and transforms it into a string"
  [ast]
  (.generate escodegen ast))

(defn surround-with-iife
  "surrounds the given code as string with an iife"
  [code]
  (str "(function() {" code "})()"))

(defn set-paths-relative-to-project-root
  "given a code as string changes all .. to ."
  [code]
  (str/replace
   (str/replace code "require('.." "require('.")
   "require(\".." "require(\". "))
