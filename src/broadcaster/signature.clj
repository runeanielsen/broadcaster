(ns broadcaster.signature
  (:import (javax.crypto Mac)
           (javax.crypto.spec SecretKeySpec)))

(defn- create-secret-key [key mac encoding]
  (SecretKeySpec. (.getBytes key encoding) (.getAlgorithm mac)))

(defn- to-hex-string [bytes]
  (apply str (map #(format "%02x" %) bytes)))

(defn- sign [mac string secret encoding]
  (let [secret-key (create-secret-key secret mac encoding)]
    (-> (doto mac
          (.init secret-key)
          (.update (.getBytes string encoding)))
        .doFinal)))

(defn valid-hex-signature? [algorithm signature string secret encoding]
  (let [mac (Mac/getInstance algorithm)
        calculated-signature (sign mac string secret encoding)]
    (= signature (to-hex-string calculated-signature))))
