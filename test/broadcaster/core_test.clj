(ns broadcaster.core-test
  (:require [broadcaster.core :as sut]
            [broadcaster.test-data :as test-data]
            [clojure.test :refer [deftest is]]))

(deftest handler
  (let [request test-data/job-completed-request
        result (sut/handler request)]
    (is (= request result))))
