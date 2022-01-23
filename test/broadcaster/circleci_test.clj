(ns broadcaster.circleci-test
  (:require [broadcaster.circleci :as sut]
            [broadcaster.test-data :as test-data]
            [clojure.test :refer [deftest is]]))

(deftest invalid-signature-request
  (let [request test-data/invalid-signature-request
        result (sut/circleci-request request test-data/secret)]
    (is (false? result))))

(deftest circleci-request
  (let [request test-data/job-completed-request
        result (sut/circleci-request request test-data/secret)]
    (is (true? result))))
