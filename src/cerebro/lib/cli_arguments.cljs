(ns cerebro.lib.cli-arguments
  (:require [cljs.nodejs :as node]))

(def program (node/require "commander"))


(defn setup
  "prepares the command line interface for cerebro"
  []
  (.option program "-s, --source <source>" "specify the source path")
  (.option program "-t, --test <test>" "specify the test path")
  program)

(defn handle
  "either prints the help or passed the parameters
  to cerebro as inputs"
  [program]
  (.parse program process.argv)
  (cond
    (or (nil? (aget program "args"))
        (nil? (aget program.args "length"))) (.help program)
    true {:source program.source
          :test program.test}))
