(ns cerebro.lib.source-reader
  (:require [cljs.nodejs :as node]
            [clojure.string :as str]
            [cerebro.lib.utils :as utils]))
(def fs (node/require "fs"))
(def acorn (node/require "acorn"))


(defn walk-names
  "walks recursively down a folder structure, returning file names"
  [path]
  (map
   (fn [filename]
     (let [filepath (str path "/" filename)]
     (if (.isDirectory (.statSync fs filepath))
       (walk-names filepath)
       filepath)))
   (.readdirSync fs path)))

(defn read-in-memory
  "reads in memory all file paths that are passed as params"
  [path]
  (map
   #(hash-map % (.readFileSync fs % "utf8"))
   (flatten (walk-names path))))

(defn create-ast
  "given a map with path and string representation
  returns a map between path and AST representation"
  [path-string]

  (map
   #(hash-map (first (keys %)) (.parse acorn (first(vals %))))
   path-string))

;; sample usage
;; (utils/stringify (create-ast (read-in-memory "./test/example-project/lib")))


(set! (.-exports js/module) (hash-map
                             :create-ast create-ast
                             :read read-in-memory))
