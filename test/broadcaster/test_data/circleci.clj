(ns broadcaster.test-data.circleci)

(def ^:private test-data-path "test/broadcaster/test_data/")
(def ^:private workflow-completed-body-json (slurp (str test-data-path "workflow-completed.json")))
(def ^:private job-completed-body-json (slurp (str test-data-path "job-completed.json")))

(def secret "my-secret123")

(def job-completed-request
  {:uri "/circleci"
   :headers {"content-type" "application/json"
             "user-agent" "CircleCI/Webhook/1.0"
             "circleci-event-type" "job-completed"
             "circleci-signature" "5a187556ae2d6d0be9212883287f59cb3160deff934bd94a75ebe64a1c7a56c4"}
   :body job-completed-body-json})

(def workflow-completed-request
  {:uri "/circleci"
   :headers {"content-type" "application/json"
             "user-agent" "CircleCI/Webhook/1.0"
             "circleci-event-type" "workflow-completed"
             "circleci-signature" "70dc0980c4f5e7d903072701576ad1af508701a092757e2a8869650ba23ee834"}
   :body workflow-completed-body-json})

(def invalid-signature-request
  {:uri "/circleci"
   :headers {"content-type" "application/json"
             "user-agent" "CircleCI/Webhook/1.0"
             "circleci-event-type" "workflow-completed"
             "circleci-signature" "this-signature-is-invalid"}
   :body workflow-completed-body-json})
