; * Tên tiếng Anh dùng trong chương trình:
	; * Vần: rhyme
	; * Phụ âm đầu: consonant
	; * Âm chính: major-vowel
	; * Đầu vần: vowel-prefix
	; * Đuôi vần: vowel-suffix
(ns a11y.core)
(def consonants '[b c ch d đ f g h j k kh l m n ng nh p r s t th tr v w x z])
(def major-vowels '[a ă â o ơ ô e u ư i y])
(def vowel-suffixes '[nil c ch i m n ng nh o p t u y])
(def diacritics-available '{nil 6 c 2 ch 2 i 6 m 6 n 6 ng 6 nh 6 o 6 p 2 t 2 u 6 y 6})
(def vowel-prefixes '[nil i o u])
(def diacritics-6 '[nil ̀ ́ ̉ ̃ ̣])
(def diacritics-2 '[́ ̣])
;(binding [*out* (java.io.FileWriter. "my.log")]
;  (print (str (nth am-chinh 2) (nth dau-6 0)))
;  (print (get so-dau-duoi (nth dau-6 0)))
;(flush))
