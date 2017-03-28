(ns cerebro.lib.source-reader
  (:require [cljs.nodejs :as node]
            [clojure.string :as str]
            [cerebro.lib.utils :as utils]))

(def fs (node/require "fs"))


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
   #(hash-map :path % :code (.readFileSync fs % "utf8"))
   (walk path)))
