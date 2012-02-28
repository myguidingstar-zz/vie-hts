; * Tên tiếng Anh dùng trong chương trình:
	; * Vần: rhyme
	; * Phụ âm đầu: consonant
	; * Âm chính: major-vowel
	; * Đầu vần: vowel-prefix
	; * Đuôi vần: vowel-suffix
(ns a11y.core)
(def consonants '[b c ch d đ f g h j k kh l m n ng nh p r s t th tr v w x z])
(def major-vowels '[a ă â e ê i o ơ ô u ư y])
(def vowel-suffixes '[nil c ch i m n ng nh o p t u y])
(def diacritics-available '{nil 6 c 2 ch 2 i 6 m 6 n 6 ng 6 nh 6 o 6 p 2 t 2 u 6 y 6})
(def vowel-prefixes '[nil i o u ư])
(def diacritics-6 '[nil ̀ ́ ̉ ̃ ̣])
(def diacritics-2 '[́ ̣])
(def allowed-suffixes
'{	 "a" [nil c ch i m n ng nh o p t u y]
	"ia" [nil                           ]
	"oa" [nil c ch i m n ng nh o p t   y]
	"ua" [nil                           ]
	"ưa" [nil                           ]

	 "ă" [nil c      m n ng      p t    ]
	"iă" []
	"oă" [    c      m n ng      p t    ]
	"uă" [] ;biểu diễn qua oă

	 "â" [nil c      m n ng      p t u y]
	"iâ" [] ;dùng y như một phụ âm
	"oâ" []
	"uâ" [    c      m n ng      p t    ]
	"ưâ" []

	 "e" [nil c    i m n ng nh   p t    ]
	"ie" []
	"oe" [nil c      m n ng    o p t    ]
	"ue" [] ;biểu diễn qua oe
	"ưe" []

	 "ê" [nil   ch   m n ng nh   p t    ]
	"iê" [    c      m n ng      p t u  ]
	"oê" [] ;biểu diễn qua uê
	"uê" [nil   ch   m n ng nh   p t u y]
	"ưê" []

	 "i" [nil   ch i m n ng nh   p t u  ]
	"ii" []
	"oi" [] ;đây là o- /i, đừng nhầm với /o -i
	"ui" [] ;đây là u- /i, đừng nhầm với /u -i ; biểu diễn qua uy
	"ưi" []

	 "o" [nil c    i m n ng      p t    ]
	"io" []
	"oo" [    c          ng             ]
	"uo" []
	"ưo" []

	 "ô" [nil c    i m n ng nh   p t    ]
	"iô" []
	"oô" []
	"uô" [    c    i m n ng      p t    ]
	"ưô" []

	 "ơ" [nil c    i m n ng nh   p t u  ]
	"iơ" []
	"oơ" []
	"uơ" [nil      i                 u  ]
	"ươ" [    c    i m n ng      p t u  ]

	 "u" [nil c    i m n ng nh   p t   y]
	"iu" [] ;đây là i- /u, đừng nhầm với /i -u
	"ou" []
	"uu" []
	"ưu" [] ;đây là ư- /u, đừng nhầm với /ư -u

	 "ư" [nil c    i m n ng nh   p t u  ]
	"iư" []
	"oư" []
	"uư" []
	"ưư" []

	 "y" [nil   ch i m n ng nh   p t    ]
	"iy" []
	"oy" []
	"uy" [nil c ch   m n ng nh   p t u  ]
	"ưy" []})
;(def allowed-suffixes-strict) -->binding
(defn count-allowed-suffixes [major-vowel-set]
	(reduce + 
		(map #(get diacritics-available %1)
			(get allowed-suffixes major-vowel-set))))
(defn get-major-vowel-set [major-vowel]
	(map #(str %1 major-vowel)
		vowel-prefixes))
(defn count-rhymes-by-single-major-vowel [major-vowel]
	(reduce + 
		(map count-allowed-suffixes
			(get-major-vowel-set major-vowel))))
(defn count-rhymes []
	(reduce +
		(map count-rhymes-by-single-major-vowel
			major-vowels)))
(defn suffix-to-diacritics [vowel-suffix]
	(cond
		(= (get diacritics-available vowel-suffix) 6) diacritics-6
		(= (get diacritics-available vowel-suffix) 2) diacritics-2
		))
(defn add-diacritics [major-vowel-set vowel-suffix]
	(reduce conj []
		(map #(str major-vowel-set %1 vowel-suffix)
			(suffix-to-diacritics vowel-suffix))))
(defn make-rhyme-sets [major-vowel-set]
	(reduce conj []
		(map #(conj [major-vowel-set] %1)
			(get allowed-suffixes major-vowel-set))))


