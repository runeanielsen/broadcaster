(ns broadcaster.circleci-test
  (:require [broadcaster.circleci :as sut]
            [broadcaster.test-data :as test-data]
            [broadcaster.extensions :refer [catch-thrown-info]]
            [clojure.test :as t :refer [deftest is]]))

(deftest invalid-signature-request
  (let [request test-data/invalid-signature-request
        expected {:msg "The signature does not match"
                  :data {:type :broadcaster.circleci/invalid-signature}}]
    (is (= (catch-thrown-info (sut/circleci-request request test-data/secret))
           expected))))

(deftest circleci-request
  (let [request test-data/job-completed-request
        result (sut/circleci-request request test-data/secret)]
    (is (true? result))))
