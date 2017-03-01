(ns cerebro.lib.source-reader
  (:require [cljs.nodejs :as node]
            [clojure.string :as str]
            [cerebro.lib.utils :as utils]))

;; TODO USE A deftype here instead of maps or array of maps

(def fs (node/require "fs"))
(def acorn (node/require "acorn"))

;; TODO this should read just the first time from disk
;;      and transform into AST just once
;;      after that return the AST

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
   #(hash-map % (.readFileSync fs % "utf8"))
   (flatten (walk path))))

;; TODO what about return a Clojure map instead of an AST
;;      and then put the filename as a property?
;;      so I dont have to deal with a map path->AST
;;      but just with map
(defn to-ast
  "given a map with path and string representation
  returns a map between path and AST representation"
  [path-string]
  (map
   #(hash-map (first (keys %)) (.parse acorn (first(vals %))))
   path-string))

;; sample usage
;; (utils/stringify (to-ast (read "./test/example-project/lib")))
