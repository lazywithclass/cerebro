(ns cerebro.mutations-reader
  (:require [cljs.nodejs :as node]))
(def fs (node/require "fs"))

;; (.readdirSync fs "./src/cerebro/mutations")
;; why is * required?
(.readdirSync fs (js* "__dirname"))


(defn answer
  ""
  []
  42)

(answer)

(set! (.-exports js/module) answer)
