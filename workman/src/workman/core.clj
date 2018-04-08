(ns workman.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [ring.middleware.defaults :as ring])
  (:use [compojure.route :only [not-found]]
        [compojure.core :only [defroutes GET]]
        org.httpkit.server))

(defn extract-body
  "Get the body of a HTTP response if status is OK"
  [response]
  (if (= 200 (get response :status))
    [(get response :body) nil]
    [nil "The GET request failed!"]))

(defn get-packages []
  (let [keys '("biojs", "bionode")]
    (extract-body
     (http/get
      (str
       "https://registry.npmjs.org/-/v1/search?text=keywords:"
       (clojure.string/join "," keys))))))

(defn create-response []
  (let [packages (get-packages)]
    (if (nil? (first packages))
      (rest packages)
      (first packages))))

(defn async-handler [ring-request]
  ;; unified API for WebSocket and HTTP long polling/streaming
  (with-channel ring-request channel    ; get the channel
    (if (websocket? channel)            ; if you want to distinguish them
      (on-receive channel (fn [data]     ; two way communication
                            (send! channel data)))
      (send! channel {:status 200
                      :headers {"Content-Type" "text/plain"}
                      :body    (create-response)}))))

(defroutes all-routes
  (GET "/" [] async-handler)
  (not-found "<p>Page not found.</p>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-server all-routes {:port 8080}))
