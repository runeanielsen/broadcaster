(ns broadcaster.extensions)

(defmacro catch-thrown-info [f]
  `(try
     ~f
     (catch
      clojure.lang.ExceptionInfo e#
       {:msg (ex-message e#) :data (ex-data e#)})))
