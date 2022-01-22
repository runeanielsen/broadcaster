(ns broadcaster.core-test
  (:require [broadcaster.core :as sut]
            [clojure.test :refer [deftest is]]))

(deftest handler
  (let [expected {:status 200
                  :headers {"Content-Type" "text/html"}
                  :body "Hello world"}
        result (sut/handler nil)]
    (is (= expected result))))
