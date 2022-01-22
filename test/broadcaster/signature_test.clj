(ns broadcaster.signature-test
  (:require [broadcaster.signature :as sut]
            [clojure.test :refer [deftest testing is]]))

(deftest valid-hex-signature?
  (testing "valid hex signature hmacsha256"
    (let [string "hello world"
          secret "secret"
          signature "734cc62f32841568f45715aeb9f4d7891324e6d948e4c6c60c0621cdac48623a"
          algorithm "HMACSHA256"
          valid? (sut/valid-hex-signature? algorithm signature string secret)]
      (is (true? valid?))))

  (testing "valid hex signature hmacsha256"
    (let [string "hello world"
          secret "secret"
          signature "invalid-signature"
          algorithm "HMACSHA256"
          valid? (sut/valid-hex-signature? algorithm signature string secret)]
      (is (false? valid?)))))
