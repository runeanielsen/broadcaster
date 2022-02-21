(ns broadcaster.core
  (:require [org.httpkit.server :as http-kit]
            [compojure.route :refer [not-found]]
            [compojure.core :refer [defroutes GET]]
            [ring.middleware.reload :refer [wrap-reload]]
            [broadcaster.circleci :as circleci]
            [broadcaster.websocket :as ws])
  (:gen-class))

(defonce circleci-secret (or (System/getenv "BROADCASTER_CIRCLECI_SECRET")
                             "my-secret123"))

(defonce server (atom nil))

(defroutes all-routes
  (GET "/ws" [] ws/handler)
  (GET "/circleci" [request] (circleci/handler request circleci-secret))
  (not-found "<p>Page not found.</p>"))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server [port]
  (reset! server (http-kit/run-server (wrap-reload #'all-routes) {:port port})))

(defn -main [& _]
  (start-server 3000))
