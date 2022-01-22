(ns broadcaster.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello world"})

(comment
  (def server
    (run-jetty (wrap-reload #'handler) {:port 3000 :join? false}))
  (.start server)
  (.stop server))
