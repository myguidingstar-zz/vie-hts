(ns a11y.test.core
  (:use [a11y.core])
  (:use [clojure.test]))

(deftest unicode-support
  (is (= (str (nth major-vowels 1) (nth diacritics-6 3)) "ẳ") "Clojure works with unicode!!!"))

(deftest diacritics-available-test
	(is (= (get diacritics-available nil) 6)))

(deftest count-allowed-suffixes-test
	(is (= (count-allowed-suffixes "a") 62))
	(is (= (count-allowed-suffixes "iư") 0)))
(deftest get-major-vowel-set-test
	(is (= (get-major-vowel-set 'a) '["a" "ia" "oa" "ua" "ưa"])))
(deftest count-rhymes-by-single-major-vowel-test
	(is (= (count-rhymes-by-single-major-vowel 'a) 136))
	(is (= (count-rhymes-by-single-major-vowel 'ă) 54)))
(deftest count-rhymes-test
	(is (= (count-rhymes) 896)))
