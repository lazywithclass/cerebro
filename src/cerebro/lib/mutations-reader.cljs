(ns cerebro.mutations-reader
  (:require [cljs.nodejs :as node]))


(def fs (js/require "fs"))
(.readdirSync fs "/home/lazywithclass")
;; (.readdirSync fs node/__dirname)




(defn answer
  ""
  []
  42)

(answer)

(set! (.-exports js/module) answer)
