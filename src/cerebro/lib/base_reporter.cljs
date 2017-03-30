(ns cerebro.lib.base-reporter
  (:require [cljs.nodejs :as node]
            [cerebro.lib.utils :as utils]))

(def colors (node/require "colors"))
(def diff (node/require "diff"))

(defn report
  "prints to console the affected code, highlighting the
  alive mutant position"
  [original mutated]
  (doseq [part (.diffWords diff original mutated)]
    (cond
      (true? part.added) (.write js/process.stderr (colors.green part.value))
      (true? part.removed) (.write js/process.stderr (colors.red part.value))
      :else (.write js/process.stderr(colors.grey part.value)))))

