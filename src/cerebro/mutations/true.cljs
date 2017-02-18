(ns cerebro.mutations.t)

(defn mutations
   ""
   []
   ('undefined 'null false))

(set! (.-exports js/module) mutations)

