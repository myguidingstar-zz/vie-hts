Giới thiệu
==========
Chương trình này được viết bằng clojure, mục đích chủ yếu để tạo danh sách các âm cần ghi.
Đồng thời, chương trình cũng đưa ra một số kết quả tính toán về mặt số lượng.

Mục tiêu của dự án là có được một chương trình đọc văn bản tiếng Việt (Vietnamese Text-to-Speech) với chất lượng giọng nói đầu ra đủ tốt;
 đồng thời cung cấp chỉ dẫn (và hỗ trợ) cho cộng đồng có thể tự xây dựng các giọng đọc bổ sung). Ngoài ra, phần ghi âm của dự án cũng là nguồn
 dữ liệu đầu vào tương đối đầy đủ để phân tích trên chương trình Praat cho những ai muốn tiến hành theo cách espeak đang làm (xem dưới đây).

Giải pháp sẵn có hiện nay là **espeak**, chương trình nhỏ gọn đang được cài sẵn trong nhiều distro.
Espeak cũng được Google sử dụng để phát âm tiếng Việt.

Để máy tính đọc được tiếng Việt, có một số hướng đi:

* Ghi âm từng từ có nghĩa một (kiểu wyabdc-real-people-tts)
	* Ưu: chất lượng đầu ra kiểm soát được 100%
	* Nhược:
		* không/khó mở rộng số từ: khi phát sinh từ mới (đặc điểm cố hữu của ngôn ngữ) phải ghi âm thêm; người dùng phải cập nhật phiên bản)
		* số lượng từ khổng lồ
* Tổng hợp từ các đơn vị âm cơ bản (cách của espeak), dùng thuật toán để sinh ra âm
	* Ưu:
		* đọc được tất cả các từ mà không cần cho sẵn
		* có thể điều chỉnh giọng đọc lên giọng, xuống giọng v.v... bằng tham truyền đến thuật toán.
	* Nhược: vì quá trình phân tích xử lí phức tạp nên đóng góp của cộng đồng sẽ chủ yếu là đánh giá kết quả, còn lại cá nhân sẽ nghiên cứu điều chỉnh.
* Cách thực hiện của dự án:
	* Ghi âm các **phần phụ âm đầu** và **vần** rồi tổng hợp (đơn giản là nối). Các khái niệm vui lòng xem bên dưới.
	* Kết hợp cân bằng (nhưng không thể tối đa) ưu, nhược của hai phương pháp trên.
	* Tạo một voice tên là vie cho espeak (phải viết lại *vie_rules*, *vie_list* và *ph_vie*)
	* Cộng đồng có thể ghi âm để tạo ra giọng đọc mới
		* thậm chí trong mỗi giọng đọc đó có thể tạo (một số) **giọng đọc phụ** được đọc với cung bậc cảm xúc đặc biệt, kèm với giọng đọc thường
		* lưu ý:
			* mỗi giọng đọc là một người đọc (hoặc những người có giọng giống nhau)
			* mỗi giọng đọc phụ này cũng có khối lượng bằng một giọng đoc thường

Các khái niệm
=============
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

* Tên tiếng Anh dùng trong chương trình:
	* Vần: rhyme
	* Phụ âm đầu: consonant
	* Âm chính: major-vowel
	* Đầu vần: vowel-prefix
	* Đuôi vần: vowel-suffix
Cách thống kê
-------------

1. Các phụ âm: Các âm in đậm là các âm cần xử lí. Sắp theo abc để tránh bỏ sót.
	* __b__
	* __c, ch__
	* __d__
	* __đ__
	* __f__
	* __g__, gi (biểu diễn qua j)
		* gia = j~ ~a
		* gì = j~ ~ì
	* __h__
	* __j__
	* __k__ (trong "Bắc Kạn"), __kh__
	* __l__
	* __m__
	* __n, ng, nh__
	* __p__,ph (biểu diễn qua f)
	* qu (không ghi, biểu diễn qua c)
		* qu~ á = c~ ~oá
		* qu~ yết = c~ ~uyết
	* __r__
	* __s__
	* __t, th, tr__
	* __v__
	* __w__
	* __x__
	* __z__
* Các vần
	* tính số vần qua mỗi âm chính, trong mỗi âm chính:
		* bản chất:
			* âm chính có thể kết hợp với các trường hợp âm đuôi nào?
			* các trường hợp âm đuôi đó có thể kết hợp với âm đầu nào?
		* cách tính (xét một cách thủ công các trường hợp kết hợp có thể được của tiếng Việt)
			* kết hợp âm chính và âm đuôi để thu được các **bộ âm đuôi**
			* kết hợp âm đầu với các bộ âm đuôi
* Minh hoạ vần /a:
	1. _- /a (các bộ âm đuôi)
		* /a -_ {6}
		* /a -c {2}
		* /a -ch {2}
		* /a -i {6}
		* /a -m {6}
		* /a -n {6}
		* /a -ng {6}
		* /a -nh {6}
		* /a -o {6}
		* /a -p {2}
		* /a -t {2}
		* /a -u {6}
		* /a -y {6}
	* Kết hợp âm đầu với các bộ âm đuôi
		* i- /a ~ (dùng lại kết quả bộ âm đuôi, có xét lại. Chỉ kết quả in đậm là có thể kết hợp)
			* **/a -_ {6}**
			* /a -c {2}
			* /a -ch {2}
			* /a -i {6}
			* /a -m {6}
			* /a -n {6}
			* /a -ng {6}
			* /a -nh {6}
			* /a -o {6}
			* /a -p {2}
			* /a -t {2}
			* /a -u {6}
			* /a -y {6}
		* o- /a (làm như i-)
			* ...
		* u- /a (làm như i-)
			* ...
