(ns cerebro.mutations.less-than-equal-to-less-than
  (:require [cljs.nodejs :as node]
            [cerebro.mutations.utils :as utils]))

(defn mutation
  "mutate <= to <"
  [node]
  (if (= (aget node "operator") "<=")
    (aset node "operator" "<")))

(defn mutate
  [candidate]
  (utils/mutate-nodes candidate {:BinaryExpression mutation}))
