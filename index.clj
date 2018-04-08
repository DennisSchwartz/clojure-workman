(ns npm-search.core
  (:require [clj-http.client :as client]))

(client/get "https://registry.npmjs.org/")
