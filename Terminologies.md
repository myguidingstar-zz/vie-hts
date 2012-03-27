Các khái niệm
=============

Các khái niệm dưới đây không phải là các khái niệm của ngữ pháp, ngôn ngữ học v.v... Chúng được đặt ra trong thực tiễn phân tích, triển khai vie-hts. Ngữ cảnh của chúng cũng chỉ nên áp dụng trong chương trình, do cách tiếp cận như đã trình bày ở trên.

Từ
---

1. **Từ đầy đủ** là từ có thể đọc lên được theo cách đọc tiếng Việt.
    * ví dụ:    "từ", "đầy", "đủ", "chương", "trình" là các từ đầy đủ.
    * "tiế" không phải là từ đầy đủ.
* **Vần** (trong "đánh vần", "vần thơ") là phần còn lại của một từ đầy đủ sau khi đã bỏ phần phụ âm đầu (nếu có).
* **Phần phụ âm đầu** này có thể là một phụ âm đơn hoặc nhóm phụ âm.
    * ví dụ:
        * "ừ", "ầy", "ủ", "ương", "ình" v.v... là các vần trong ví dụ trên.
        * "t", "đ", "đ", "ch", "tr"
    * để trực quan, từ nay sẽ kí hiệu thành:
        * ~ừ ~ầy ~ủ
        * t~ ch~ tr~

Âm
---

1. Mỗi vần đều có một nguyên âm làm **âm chính**
    * ví dụ:
        * "từ" -> ~ừ -> "ư"
        * "đầy" -> ~ầy -> "â"
        * "chương" -> ~ương -> "ơ"
    * kí hiệu:
        * /ừ /ầ /ơ
* Ngoài **âm chính**, vần còn có thể có **dấu**, **đầu vần** và **đuôi vần**
    * ví dụ:
        * "đầy" -> ~ầy -> **dấu** *huyền*, không có **đầu vần** và **đuôi vần** là *y*
        * "chương" -> ~ương -> **dấu** *thanh* (còn gọi là không dấu), **đầu vần** *ư* và **đuôi vần** là *ng*
    * kí hiệu:
        * đầy = đ~ _ /â {f} -y
        * đủ = đ~ _ /u {r} _
        * chương = ch~ ư- /ơ {_} -ng
        * đủ = đ~ _ /u {r} _
    * có thể thấy:
        * dấu _ đại diện cho *không có gì*
        * kí hiệu của dấu {_}, {f}, {s}, {r}, {x}, {j} mượn từ cách gõ telex; 
* Vì mục đích chủ yếu để thống kê, mà mỗi vần chỉ có thể có 2 hoặc 6 cách kết hợp dấu nên có thể dùng cách kí hiệu sau:
    * số cách kết hợp dấu phụ thuộc vào **đuôi vần**
        * {2} với -c -ch -p -t
        * {6} với -_ -m -n -ng -nh -i -o -u -y
    * ví dụ:
        * _ /a {2} **-ch** = ~ách, ~ạch
        * ư- /ơ {6} **-ng** = ~ương ~ường ~ướng ~ưởng ~ưỡng ~ượng
* Lưu ý:
    * xoong = x~ o- **/o** {_} -ng
    * hươu = h~ ư- **/ơ** {_} -u
    * phân biệt **i** của vần (trong *kia*, vần ~ia) và **i** của phần phụ âm gi~ (trong *gia*, vần ~a)
		* "gì" thực chất là /gi~ ~ì/ là /j~ ~ì/
	* tương tự, phân biệt **u** của vần với u của qu~
    * chỉ xét **y** trong trường hợp đọc lên khác với khi bị thay bằng i, ví dụ:
		* cai, ca**y**: có xét
		* củi, cu__ỷ__ ("quỷ" được viết lại) có xét
		* cí, c__ý__ ("kí", "ký" được viết lại) không xét
	* một số lưu ý khác cũng được ghi chú trong mã nguồn chương trình.

* Tên tiếng Anh dùng trong chương trình:
	* Vần: rhyme
	* Phụ âm đầu: consonant
	* Âm chính: major-vowel
	* Đầu vần: vowel-prefix
	* Đuôi vần: vowel-suffix
	* Bộ âm chính: major-vowel-set

