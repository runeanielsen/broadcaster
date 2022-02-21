(ns broadcaster.circleci-test
  (:require [broadcaster.circleci :as sut]
            [broadcaster.test-data.circleci :as test-data]
            [clojure.test :as t :refer [deftest is]]))

(deftest handler-invalid-signature
  (let [request test-data/invalid-signature-request
        expected {:status 500
                  :body "The signature does not match."}
        result (sut/handler request test-data/secret)]
    (is (= expected result))))

(deftest handler
  (let [request test-data/job-completed-request
        result (sut/handler request test-data/secret)]
    (is (= {:status 200} result))))
