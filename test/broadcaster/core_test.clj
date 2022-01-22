(ns broadcaster.core-test
  (:require [broadcaster.core :as sut]
            [clojure.test :refer [deftest is]]))

(deftest entry-test
  (is (= "hello broadcaster" (sut/entry))))
