(ns cerebro.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(println "Hello, world!")

(set! (.-exports js/module) Cerebro)
