(ns broadcaster.circleci-test
  (:require [broadcaster.circleci :as sut]
            [clojure.test :refer [deftest is testing]]))

(deftest invalid-signature-request
  (let [request {:headers {"content-type" "application/json"
                           "user-agent" "CircleCI-Webhook/1.0"
                           "circleci-event-type" "workflow-completed"
                           "circleci-signature" "not-a-valid-signature"}
                 :body "foo"}
        result (sut/circleci-request request "secret")]
    (is (false? result))))

(deftest circleci-request
  (let [request {:headers {"content-type" "application/json"
                           "user-agent" "CircleCI-Webhook/1.0"
                           "circleci-event-type" "workflow-completed"
                           "circleci-signature" "773ba44693c7553d6ee20f61ea5d2757a9a4f4a44d2841ae4e95b52e4cd62db4"}
                 :body "foo"}
        result (sut/circleci-request request "secret")]
    (is (true? result))))
