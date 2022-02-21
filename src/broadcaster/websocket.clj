(ns broadcaster.websocket
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :as http-kit]))

(def ^:private clients (atom #{}))

(defn- connect! [channel]
  (log/info "Channel opened.")
  (swap! clients conj channel))

(defn- disconnect! [channel status]
  (log/info "Channel closed: " status)
  (swap! clients disj channel))

(defn send-message! [message]
  (log/info "Sending messages to connected sockets.")
  (doseq [ch @clients]
    (if-let [success-send? (http-kit/send! ch message)]
      success-send?
      (log/warn "Could not send message to socket."))))

(defn handler [request]
  (http-kit/as-channel request
                       {:on-open (fn [ch] (connect! ch))
                        :on-close (fn [ch status] (disconnect! ch status))}))
