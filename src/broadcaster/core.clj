(ns broadcaster.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [broadcaster.circleci :as circleci]))

(def secret "my-secret123")

(defmulti route-request
  (fn [x] ((:headers x) "user-agent")))

(defmethod route-request "CircleCI/Webhook/1.0" [request]
  (circleci/circleci-request request secret))

(defmethod route-request :default [x]
  (throw (IllegalArgumentException.
          (str "I don't know the " ((:headers x) "user-agent") " user-agent."))))

(defn handler [request]
  (route-request request))

(comment
  (def server
    (run-jetty (wrap-reload #'handler) {:port 3000 :join? false}))
  (.start server)
  (.stop server))
