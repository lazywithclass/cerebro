(ns cerebro.lib.utils
  (:require [clojure.string :as str]))

(defn stringify
  "stringifies using JSON.stringify"
  [object]
  (.stringify js/JSON object))

(defn log-stringified
  "prints with proper stringification"
  [object]
  (.log js/console (.stringify js/JSON object nil "  ")))
