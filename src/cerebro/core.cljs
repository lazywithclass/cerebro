(ns cerebro.core
  (:require [cljs.nodejs :as node]
            [cerebro.mutations-reader :as reader]))



(node/enable-util-print!)

(println (reader/answer))

;; (println reader)

;; (set! (.-exports js/module) Cerebro)
