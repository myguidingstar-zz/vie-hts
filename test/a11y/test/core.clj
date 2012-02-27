(ns a11y.test.core
  (:use [a11y.core])
  (:use [clojure.test]))

(deftest unicode-support
  (is (= (str (nth major-vowels 1) (nth diacritics-6 3)) "ẳ") "Clojure works with unicode!!!"))

