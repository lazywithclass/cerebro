(ns cerebro.mutations.double-equal-to-true
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.utils :as utils]))

(defn mutation
  "mutate a == b to true == true"
  [node]
  (if (= (aget node "operator") "==")
    (do
      (aset node "left" (js-obj "type" "Literal" "value" true "raw" "true"))
      (aset node "right" (js-obj "type" "Literal" "value" true "raw" "true")))))

(defn mutate
  [candidate]
  (utils/mutate-nodes candidate {:BinaryExpression mutation}))
