(ns broadcaster.circleci
  (:require [broadcaster.signature :as signature]))

(defn- valid-circleci-signature? [request secret]
  (let [{:keys [headers body]} request
        signature (get headers "circleci-signature")]
    (signature/valid-hex-signature? "HMACSHA256" signature body secret "UTF-8")))

(defn circleci-request [request secret]
  (let [valid-request (valid-circleci-signature? request secret)]
    (if (true? valid-request)
      true
      (throw
       (ex-info "The signature does not match"
                {:type ::invalid-signature})))))
