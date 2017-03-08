(ns cerebro.lib.source-reader
  (:require [cljs.nodejs :as node]
            [clojure.string :as str]
            [cerebro.lib.utils :as utils]))

(def fs (node/require "fs"))
(def acorn (node/require "acorn"))

(defrecord Candidate [path code])

(defn walk
  "walks recursively down a folder structure, returning file names"
  [path]
  (map
   (fn [filename]
     (let [filepath (str path "/" filename)]
       (if (.isDirectory (.statSync fs filepath))
         (walk filepath)
         filepath)))
   (.readdirSync fs path)))

(defn read
  "reads in memory all file paths that are passed as params"
  [path]
  (map
   #(Candidate. % (.readFileSync fs % "utf8"))
   (walk path)))

(defn to-ast
  "given a coll of candidates path - file as string, returns a coll
  of candidates path - AST"
  [candidates]
  (map (fn [candidate]
         (Candidate. candidate.path (.parse acorn candidate.code)))
       candidates))

;; (to-ast (read "./test/example-project/lib"))
