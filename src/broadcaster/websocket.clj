(ns broadcaster.websocket
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :as http-kit]))

(def ^:private clients (atom #{}))

(defn- connect! [channel]
  (log/info "Channel opened")
  (swap! clients conj channel))

(defn- disconnect! [channel status]
  (log/info "Channel closed: " status)
  (swap! clients disj channel))

(defn send-message! [message]
  (doseq [ch @clients]
    (http-kit/send! ch message)))

(defn handler [ring-req]
  (http-kit/as-channel ring-req
                       {:on-open (fn [ch] (connect! ch))
                        :on-close (fn [ch status] (disconnect! ch status))}))
