(ns broadcaster.signature
  (:import (javax.crypto Mac)
           (javax.crypto.spec SecretKeySpec)))

(defn- create-secret-key [key mac encoding]
  (SecretKeySpec. (.getBytes key encoding) (.getAlgorithm mac)))

(defn- to-hex-string [bytes]
  (apply str (map #(format "%02x" %) bytes)))

(defn- sign [mac text secret encoding]
  (let [secret-key (create-secret-key secret mac encoding)]
    (-> (doto mac
          (.init secret-key)
          (.update (.getBytes text encoding)))
        .doFinal)))

(defn- valid-hex-signature? [algorithm signature text secret encoding]
  (let [mac (Mac/getInstance algorithm)
        calculated-signature (sign mac text secret encoding)]
    (= signature (to-hex-string calculated-signature))))

(defn valid-hmac-sha256?
  "Checks the validity of a HMACSHA256 hashed string encoded in UTF-8."
  [signature text secret]
  (valid-hex-signature? "HMACSHA256" signature text secret "UTF-8"))
