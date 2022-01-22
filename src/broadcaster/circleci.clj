(ns broadcaster.circleci
  (:require [broadcaster.signature :as signature]))

(defn- valid-circleci-signature? [request secret]
  (let [{:keys [headers body]} request
        header-signature (get headers "circleci-signature")]
    (signature/valid-hex-signature? "HMACSHA256" header-signature body secret)))

(defn circleci-request [request secret]
  (let [valid-request? (valid-circleci-signature? request secret)]
    valid-request?))
