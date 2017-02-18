(defproject cerebro "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.456"]
                 [mocha-latte "0.1.2"]
                 [chai-latte "0.2.1"]]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-npm "0.6.2"]]

  :node-dependencies [[mocha "3.2.0"]
                      [chai "3.5.0"]]

  :source-paths ["src" "target/classes"]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.10"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :cljsbuild {
              :builds [{
                        :source-paths ["src"]
                        :compiler {
                                   :output-to "target/cerebro.js"
                                   :pretty-print true
                                   :optimizations :simple
                                   }
                        }]
              })


