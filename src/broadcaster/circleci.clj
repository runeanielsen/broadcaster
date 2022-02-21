(ns broadcaster.circleci
  (:require [broadcaster.signature :as signature]
            [broadcaster.websocket :as ws]))

(defn- valid-signature? [request secret]
  (let [{:keys [headers body]} request
        signature (get headers "circleci-signature")]
    (signature/valid-hmac-sha256? signature body secret)))

(defn handler [request secret]
  (let [valid-request (valid-signature? request secret)]
    (if (true? valid-request)
      (do
        (ws/send-message! request)
        {:status 200})
      {:status 500 :body "The signature does not match."})))
