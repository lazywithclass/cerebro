(ns cerebro.lib.source-reader
  (:require [cljs.nodejs :as node]
            [clojure.string :as str]))
(def fs (node/require "fs"))

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
   #(.readFileSync fs % "utf8")
   (flatten (walk-names path)))
  )

(read-in-memory "./test/example-project")


(set! (.-exports js/module) read-in-memory)
