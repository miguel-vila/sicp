(defproject sicp "0.1.0-SNAPSHOT"
  :description "Ejercicios de sicp resueltos usando clojure"

  :url "http://github.com/miguel-vila/sicp"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/core.typed "0.3.9"]]

  :plugins [[cider/cider-nrepl "0.10.0"]]

  :profiles {:dev {:dependencies [[midje "1.6.3" :exclusions [org.clojure/clojure]]]
                  :plugins [[lein-midje "3.1.3"]
                            [lein-typed "0.3.4"]]}}

  :core.typed {:check [sicp.chapter2.ex29
                       sicp.chapter2.ex30
                       sicp.chapter2.symbolic_diff]})
