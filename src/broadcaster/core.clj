(ns broadcaster.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.tools.logging :as log]
            [broadcaster.circleci :as circleci]
            [broadcaster.websocket :as ws]))

(def secret "my-secret123")

(defn status-code-200 [body]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str body)})

(defn route-request [request]
  (case (:uri request)
    "/ws"  (ws/handler request)
    "/circle-ci" (status-code-200 "")
    (status-code-200 "Not found.")))

(defn handler [request]
  (log/info (pr-str request))
  (ws/handler request)
  {:status 101
   :headers {:Upgrade "websocket"}})

(comment
  (def server
    (run-jetty (wrap-reload #'handler) {:port 3000 :join? false}))
  (.start server)
  (.stop server))
