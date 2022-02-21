(ns broadcaster.signature-test
  (:require [broadcaster.signature :as sut]
            [clojure.test :refer [deftest testing is]]))

(deftest valid-hmac-sha256?
  (testing "valid hex signature hmacsha256"
    (let [text "hello world"
          secret "secret"
          signature "734cc62f32841568f45715aeb9f4d7891324e6d948e4c6c60c0621cdac48623a"
          valid? (sut/valid-hmac-sha256? signature text secret)]
      (is (true? valid?))))

  (testing "invalid hex signature hmacsha256"
    (let [text "hello world"
          secret "secret"
          signature "invalid-signature"
          valid? (sut/valid-hmac-sha256? signature text secret)]
      (is (false? valid?)))))
