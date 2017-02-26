(ns cerebro.lib.utils
  (:require [clojure.string :as str]))

(defn stringify
  "stringifies using JSON.stringify"
  [object]
  (.stringify js/JSON (clj->js object)))
