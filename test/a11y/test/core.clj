(ns a11y.test.core
  (:use [a11y.core])
  (:use [clojure.test]))

(deftest unicode-support ;; FIXME: write
  (is (== (str (nth am-chinh 1) (nth dau-6 3)) "") "No tests have been written."))
