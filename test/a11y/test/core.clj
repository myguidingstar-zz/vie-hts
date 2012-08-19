(ns a11y.test.core
  (:use [a11y.core])
  (:use [clojure.test]))

(deftest unicode-support
  (is (= (str (nth major-vowels 1) (nth diacritics-6 3)) "ẳ")
      "Clojure works with unicode!!!"))

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
  (is (= (count-rhymes) 854))) ;joking, I used its result

(deftest add-diacritics-to-rhyme-set-test
  (is (= (add-diacritics-to-rhyme-set '["iê" ng])
         '["iêng" "iềng" "iếng" "iểng" "iễng" "iệng"])))

(deftest make-rhyme-sets-test
  (is (= (make-rhyme-sets "oa")
         '[["oa" nil] ["oa" c] ["oa" ch] ["oa" i] ["oa" m]
           ["oa" n] ["oa" ng] ["oa" nh] ["oa" o] ["oa" p] ["oa" t] ["oa" y]])))

(deftest suffix-to-diacritics-test
  (is (= (suffix-to-diacritics 'nh) '[nil ̀ ́ ̉ ̃ ̣]))
  (is (= (suffix-to-diacritics 'c) '[́ ̣])))

;the following tests are made just to preview the results
(deftest make-rhymes-by-single-major-vowel-test
  ;;(is (= (apply concat (map make-rhyme-sets (get-major-vowel-set 'a))) nil))
  ;;(is (= (make-rhymes-by-single-major-vowel 'a) nil))
  )
(deftest make-all-rhymes-test
  (is (= (seq? (make-all-rhymes)) true)))
