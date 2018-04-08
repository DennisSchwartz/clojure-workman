(defproject workman "0.1.0-SNAPSHOT"
  :description "BioJS workman"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.7.0"]
                 [http-kit "2.2.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.1"]]
  :main ^:skip-aot workman.core
  :plugins [[lein-cljfmt "0.5.7"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
