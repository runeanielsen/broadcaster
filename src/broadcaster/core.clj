(ns broadcaster.core
  (:require [org.httpkit.server :as http-kit]
            [ring.middleware.reload :refer [wrap-reload]]
            [broadcaster.circleci :as circleci]
            [broadcaster.websocket :as ws]))

(def circle-ci-secret "my-secret123")
(def ok {:status 200})
(def not-found {:status 404})

(defn handler [request]
  (case (:uri request)
    "/ws"  (ws/handler request)
    "/circleci" (let [message (circleci/circleci-request request circle-ci-secret)]
                  (ws/send-message! message)
                  ok)
    not-found))

(comment
  (def server
    (http-kit/run-server (wrap-reload #'handler) {:port 3000 :join? false}))
  (.start server)
  (.stop server))
